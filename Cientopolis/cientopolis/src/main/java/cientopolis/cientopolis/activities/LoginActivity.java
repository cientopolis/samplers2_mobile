package cientopolis.cientopolis.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import cientopolis.cientopolis.R;
import cientopolis.cientopolis.RequestController;
import cientopolis.cientopolis.interfaces.RequestControllerListener;
import cientopolis.cientopolis.models.LoginResponse;
import cientopolis.cientopolis.models.ResponseDTO;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, RequestControllerListener<LoginResponse> {

    private static final int RC_SIGN_IN = 9001;
    private static final int USER_EXISTS_REQUEST = 1;
    private CallbackManager mFacebookCallbackManager;
    private LoginButton mFacebookSignInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private TextView errorTitle;
    private TextView errorDescription;
    private SignInButton signInButton;
    private String uid;
    private RequestController requestController;
    private TextView link;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mFacebookCallbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);

        errorDescription = (TextView) findViewById(R.id.error_description);
        errorTitle = (TextView) findViewById(R.id.error_title);
        link = (TextView) findViewById(R.id.link);
        link.setClickable(true);
        link.setMovementMethod(LinkMovementMethod.getInstance());

        mFacebookSignInButton = (LoginButton)findViewById(R.id.facebook_sign_in_button);
        requestController = new RequestController(getApplicationContext(), this);
        signInButton = (SignInButton)findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this);
        setGooglePlusButtonText(signInButton, "Inicia con Gmail");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mFacebookSignInButton.registerCallback(mFacebookCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        String id1 = loginResult.getAccessToken().getUserId();
                        GraphRequest request =  GraphRequest.newMeRequest(
                                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject me, GraphResponse response) {

                                        if (response.getError() != null) {
                                            String errpr = "";
                                        } else {
                                            errorTitle.setVisibility(View.GONE);
                                            errorDescription.setVisibility(View.GONE);
                                            link.setVisibility(View.GONE);
                                            Map<String, String> params = getParams();
                                            uid = response.getJSONObject().optString("token_for_business");
                                            requestController.get(new TypeToken<ResponseDTO<LoginResponse>>() {}.getType(), "login?uid="+uid+"&provider=facebook", USER_EXISTS_REQUEST, params);

                                        }
                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "last_name,first_name,email,token_for_business");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }
                    @Override
                    public void onCancel() {
                    }
                    @Override
                    public void onError(FacebookException error) {
                    }
                }
        );
    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }


    private Map<String, String> getParams(){
        Map<String, String> params = new HashMap<>();
        return params;
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                Log.v("LOGIN-RESULT",account.getDisplayName());
                Log.v("TOKEN-ID",account.getIdToken());
                Map<String, String> params = getParams();
                uid = account.getEmail();
                requestController.get(new TypeToken<ResponseDTO<LoginResponse>>() {}.getType(), "login?uid="+uid+"&provider=gmail", USER_EXISTS_REQUEST, params);
            }
        }

        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                errorTitle.setVisibility(View.GONE);
                errorDescription.setVisibility(View.GONE);
                link.setVisibility(View.GONE);
                signIn();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void goToMainActivity(Integer state) {
        Intent result = new Intent();
        setResult(state, result);
        finish();
    }

    @Override
    public void responseOk(Integer id, ResponseDTO<LoginResponse> response) {
        if (response.getData().getUserInformation() != null) {
            SharedPreferences sharedPref = getSharedPreferences("Profile", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("id", String.valueOf(response.getData().getUserInformation().getId()));
            editor.putString("username", response.getData().getUserInformation().getUsername());
            editor.putString("email", response.getData().getUserInformation().getEmail());
            editor.commit();
            goToMainActivity(1);
        } else {
            responseError(1,response);
        }
    }

    @Override
    public void responseError(Integer id, ResponseDTO<LoginResponse> response) {
        LoginManager.getInstance().logOut();
        String textError = !response.getData().getExists() ? "Primero registrate en :" : "Hubo un error inesperado";
        errorDescription.setText(textError);
        errorTitle.setVisibility(View.VISIBLE);
        link.setVisibility(View.VISIBLE);
        link.setText(Html.fromHtml(response.getData().getRedirectUrl()));
        errorDescription.setVisibility(View.VISIBLE);
    }
}

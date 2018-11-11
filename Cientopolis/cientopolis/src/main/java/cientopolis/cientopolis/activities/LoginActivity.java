package cientopolis.cientopolis.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
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

import java.util.HashMap;
import java.util.Map;
import cientopolis.cientopolis.R;
import cientopolis.cientopolis.RequestController;
import cientopolis.cientopolis.interfaces.RequestControllerListener;
import cientopolis.cientopolis.models.LoginResponse;
import cientopolis.cientopolis.models.ProfileModel;
import cientopolis.cientopolis.models.ResponseDTO;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, RequestControllerListener<LoginResponse> {

    private static final int RC_SIGN_IN = 9001;
    private static final int USER_EXISTS_REQUEST = 1;
    private CallbackManager mFacebookCallbackManager;
    private LoginButton mFacebookSignInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton signInButton;
    private String uid;
    private RequestController requestController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mFacebookCallbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);

        mFacebookSignInButton = (LoginButton)findViewById(R.id.facebook_sign_in_button);
        requestController = new RequestController(getApplicationContext(), this);
        signInButton = (SignInButton)findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mFacebookSignInButton.registerCallback(mFacebookCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        Map<String, String> params = getParams();
                        uid = loginResult.getAccessToken().getUserId();
                        requestController.get(new TypeToken<ResponseDTO<String>>() {}.getType(), "login?uid="+uid, USER_EXISTS_REQUEST, params);

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
                uid = account.getId();
                requestController.get(new TypeToken<ResponseDTO<String>>() {}.getType(), "login?uid="+uid, USER_EXISTS_REQUEST, params);
            }
        }

        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
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
        switch(id) {
            case USER_EXISTS_REQUEST:
                if (response.getData().getExists()) {
                    SharedPreferences sharedPref = getSharedPreferences("Profile", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("uid",String.valueOf(response.getData().getUserInformation().getId()) );
                    editor.commit();
                    goToMainActivity(1);
                } else {
                    responseError(1,response);
                }

                break;
        }

    }

    @Override
    public void responseError(Integer id, ResponseDTO<LoginResponse> response) {

        //cancelar la pegada a FB o G
        goToMainActivity(2);
    }
}

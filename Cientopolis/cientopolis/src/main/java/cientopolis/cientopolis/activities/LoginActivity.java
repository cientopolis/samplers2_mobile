package cientopolis.cientopolis.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

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
import java.util.HashMap;
import java.util.Map;
import cientopolis.cientopolis.R;
import cientopolis.cientopolis.RequestController;
import cientopolis.cientopolis.interfaces.RequestControllerListener;
import cientopolis.cientopolis.models.ResponseDTO;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, RequestControllerListener<String> {

    private static final int RC_SIGN_IN = 9001;
    private CallbackManager mFacebookCallbackManager;
    private LoginButton mFacebookSignInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton signInButton;
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
                        responseOk(1,null);
                        //requestController.get(new TypeToken<ResponseDTO<String>>() {}.getType(), "login/", 1, params);

                    }
                    @Override
                    public void onCancel() {
                        responseError(1,null);
                    }
                    @Override
                    public void onError(FacebookException error) {
                        responseError(1,null);
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
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                Log.v("LOGIN-RESULT",account.getDisplayName());
                Log.v("TOKEN-ID",account.getIdToken());
                Map<String, String> params = getParams();
                responseOk(2,null);
                //requestController.get(new TypeToken<ResponseDTO<String>>() {}.getType(), "login/", 2, params);
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

    @Override
    public void responseOk(Integer id, ResponseDTO<String> response) {
        //TODO: guardar datos en sharedPreferences
        Intent result = new Intent();
        setResult(1, result);
        finish();

    }

    @Override
    public void responseError(Integer id, ResponseDTO<String> response) {
        //TODO: error response
        Intent result = new Intent();
        setResult(2, result);
        finish();
    }
}

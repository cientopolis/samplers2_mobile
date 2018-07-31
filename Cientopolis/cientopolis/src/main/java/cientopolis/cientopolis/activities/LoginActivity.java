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
import cientopolis.cientopolis.models.ProfileModel;
import cientopolis.cientopolis.models.ResponseDTO;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, RequestControllerListener<ProfileModel> {

    private static final int RC_SIGN_IN = 9001;
    private static final int USER_EXISTS_REQUEST = 1;
    private static final int CREATE_USER_REQUEST = 2;
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
                        //requestController.get(new TypeToken<ResponseDTO<String>>() {}.getType(), "user-exist/", USER_EXISTS_REQUEST, params);

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
                responseOk(2,null);
                //requestController.get(new TypeToken<ResponseDTO<String>>() {}.getType(), "user-exist/", USER_EXISTS_REQUEST, params);
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
    public void responseOk(Integer id, ResponseDTO<ProfileModel> response) {
        switch(id) {
            case USER_EXISTS_REQUEST:
                //result.data.exist
                if(true){
                    // guardar en shared preferences
                    goToMainActivity(1);
                } else {
                    //requestController.get(new TypeToken<ResponseDTO<String>>() {}.getType(), "create-user/", CREATE_USER_REQUEST, params);
                }

                break;
            case CREATE_USER_REQUEST:
                // guardar en shared preferences
                goToMainActivity(1);
                break;
        }

    }

    @Override
    public void responseError(Integer id, ResponseDTO<ProfileModel> response) {
        goToMainActivity(2);
    }
}

package ba.work.chbla.ba_eresamont.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import ba.work.chbla.ba_eresamont.R;

public class GoogleSingActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private TextView mStatusTextView;
    private TextView mDetailTextView;

    SignInButton signInButton;
    Button signOutButton;
    TextView statusTextView;
    GoogleApiClient mGoogleApiClient;
    private static final String TAG="SignInActivity";
    private static final int RC_SIGN_IN=9001;
    private Intent intent;
    GoogleSignInAccount account;
    public GoogleSignInAccount getAccount() {
        return account;
    }
    public void setAccount(GoogleSignInAccount account) {
        this.account = account;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sing);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               .requestEmail()
                .build();

        mGoogleApiClient= new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        statusTextView= findViewById(R.id.status_textview);
        signInButton= findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);

        signOutButton= findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(this);
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // Switch between login or logout
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.signOutButton:
                signOut();
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
            }
        }
    // Handle the result of input parameter, if suceed start the app, else give out an error
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:"+result.isSuccess());
        if (result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
            setAccount(account);
            statusTextView.setText(("Guten Tag, "+account.getDisplayName()));
            intent = new Intent(getApplicationContext(), MainActivity.class);
            //intent = new Intent(getApplicationContext(), ChatActivity.class);
            intent.putExtra("User", account.getDisplayName());
            startActivity(intent);
        }else{
            statusTextView.setText(("Error, "+"User or Password wrong"));
        }
    }
    //sign out the user
    private void signOut() {
        Auth.GoogleSignInApi.signOut((mGoogleApiClient)).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                statusTextView.setText("Signed out");
            }
        });
    }
      // task if connection failed
    public void onConnectionFailed(ConnectionResult connectionResult){
        Log.d(TAG, "onConnectionFailed:"+connectionResult);
    }
    public void chat(){

    }
}

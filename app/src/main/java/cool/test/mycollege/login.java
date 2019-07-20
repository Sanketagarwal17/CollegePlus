package cool.test.mycollege;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
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
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.Arrays;

public class login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String EMAIL = "email";
    EditText email, password;
    private FirebaseAuth mAuth;
    Button login, signup;
    private LoginButton loginButton;
    TextView forgot;
    ImageView skip;
    SignInButton signInButton;
    private final int RC_SIGN_IN = 1;
    CallbackManager callbackManager;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth.AuthStateListener authStateListener;
    private static final String TAG = "loginUdit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        callbackManager = CallbackManager.Factory.create();




        loginButton = (LoginButton) findViewById(R.id.flogin);
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                // App code
                Log.d(TAG, "fbonCancel: ");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d(TAG, "fbonError: " + exception.getMessage() );
            }
        });
        mAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signInButton = findViewById(R.id.glogin);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        authStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null)
                {
                    startActivity(new Intent(login.this,HomePage.class));
                }
            }
        };
        progressDialog.setTitle("Logging In");
        progressDialog.setCancelable(false);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(login.this, "Error :" + connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        skip = findViewById(R.id.skip_image);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        forgot = findViewById(R.id.forgot);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(login.this);
                alertDialog.setTitle("Password Recovery");

                final EditText input = new EditText(login.this);
                input.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("Recover",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                resetpassword(input.getText().toString());

                            }
                        });

                alertDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }

        });


        //configureSignIn();
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (email.getText().toString().length() > 0 && password.getText().toString().length() > 0) {

                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (user.isEmailVerified()) {
                                            progressDialog.dismiss();
                                            finish();
                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(login.this, "      First Verify Your Email !!\n Verification sent to your mail        ", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(login.this, "Authentication Failed" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();

                                    }
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "Enter Email and password", Toast.LENGTH_SHORT).show();
                }


            }
        });


        signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, signup.class));
            }
        });


// Configure Google Sign In

    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:successudit");
                            FirebaseUser user = mAuth.getCurrentUser();
                            SharedPreferences sharedPreferences = getApplication().getSharedPreferences("details_of_login",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("name",user.getDisplayName());
                            editor.putString("email",user.getEmail());
                            editor.putString("image",user.getPhotoUrl().toString() + "?height=500");
                            editor.apply();
                            FirebaseDatabase.getInstance().getReference().child("Users")
                                    .child(user.getUid()).child("name").setValue(user.getDisplayName());
                            FirebaseDatabase.getInstance().getReference().child("Users")
                                    .child(user.getUid()).child("email").setValue(user.getEmail());
                            FirebaseDatabase.getInstance().getReference().child("Users")
                                    .child(user.getUid()).child("type").setValue("Facebook");
                            FirebaseDatabase.getInstance().getReference().child("Users")
                                    .child(user.getUid()).child("image").setValue(user.getPhotoUrl().toString() + "?height=500");
                            startActivity(new Intent(login.this,HomePage.class));
                            Log.d(TAG, "onCompletefbloginudit: " + user.getUid()+"  " + user.getPhotoUrl());
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                        // ...
                    }
                });
    }//FacebookLoginActivity.java

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        googleApiClient.clearDefaultAccountAndReconnect();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void resetpassword(String emaili) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(emaili)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(login.this, "Recovery sent to your mail address", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(login.this, task.getException().toString(), Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Toast.makeText(this, "Internet not availible", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (googleSignInResult.isSuccess()) {
                GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();
                fireBaseAuthWithGoogle(googleSignInAccount);
            } else {

            }
        }
    }

    private void fireBaseAuthWithGoogle(GoogleSignInAccount googleSignInAccount) {

        AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(login.this, "google login success", Toast.LENGTH_SHORT).show();
                            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(login.this);
                            if (acct != null) {
                                String personName = acct.getDisplayName();
                                String personGivenName = acct.getGivenName();
                                String personFamilyName = acct.getFamilyName();
                                String personEmail = acct.getEmail();
                                String personId = acct.getId();
                                Uri personPhoto = acct.getPhotoUrl();
                                SharedPreferences sharedPreferences = getSharedPreferences("details_of_login",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("name",personName);
                                editor.putString("email",personEmail);
                                editor.putString("image",personPhoto.toString());
                                editor.apply();
                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                FirebaseDatabase.getInstance().getReference().child("Users")
                                        .child(uid).child("name").setValue(personName);
                                FirebaseDatabase.getInstance().getReference().child("Users")
                                        .child(uid).child("email").setValue(personEmail);
                                FirebaseDatabase.getInstance().getReference().child("Users")
                                        .child(uid).child("type").setValue("Google");
                                FirebaseDatabase.getInstance().getReference().child("Users")
                                        .child(uid).child("image").setValue(personPhoto.toString());

                                Log.d(TAG, "onComplete: " + "personName" + personName+ "personGivenName"
                                        + personGivenName+"personEmail" + personEmail + "personPhoto" + personPhoto);
                               // Toast.makeText(login.this, "personName" + personName+ "personGivenName"
                                 //       + personGivenName+"personEmail" + personEmail + "personPhoto" + personPhoto, Toast.LENGTH_SHORT).show();
                            }
                            //updateUI(user);
                            startActivity(new Intent(login.this,HomePage.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(login.this, "Error" + task.getException().toString(), Toast.LENGTH_SHORT).show();

                        }



                        // ...
                    }
                });
        /*AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken == null)
                {

                }
                else
                {
                    //loadUserProfile(currentAccessToken);
                }
            }
        };*/

    }

    /*private void loadUserProfile(AccessToken newAccessToken)
    {
        GraphRequest graphRequest = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d(TAG, "onCompletedfblogin  ");
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }*/
}
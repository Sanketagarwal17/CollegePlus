package cool.test.mycollege;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

public class login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

     EditText email,password;
    private FirebaseAuth mAuth;
    Button login,signup;
    SignInButton glogin;
    private GoogleApiClient mGoogleApiClient;
    LoginButton flogin;
    CallbackManager mCallbackManager;
    TextView forgot;

    ImageView skip;
    GoogleSignInOptions options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.login_screen);
        mAuth = FirebaseAuth.getInstance();
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Logging In");
        progressDialog.setCancelable(false);


        skip=findViewById(R.id.skip_image);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getSharedPreferences("logindata", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("ID", null);
                editor.putString("UID", null);
                editor.putString("isnormallogin", "skip");
                editor.putInt("PUN", 0);
                editor.commit();
                finish();
            }
        });
        forgot=findViewById(R.id.forgot);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(login.this);
                alertDialog.setTitle("Password Recovery");

                final EditText input = new EditText(login.this);
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








        configureSignIn();
        login=(Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                 if (email.getText().toString().length()>0&&password.getText().toString().length()>0) {

                     progressDialog.show();
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (user.isEmailVerified()) {
                                            SharedPreferences prefs = getSharedPreferences("logindata", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = prefs.edit();
                                            editor.putString("ID", user.getEmail());
                                            editor.putString("UID", user.getUid());
                                            editor.putString("isnormallogin", "true");
                                            editor.putInt("PUN", 0);
                                            editor.putBoolean("islogin", true);
                                            editor.commit();
                                            progressDialog.dismiss();
                                            finish();
                                        } else {
                                            progressDialog.dismiss();

                                            Toast.makeText(login.this, "      First Verify Your Email !!\n Verification sent to your mail        ", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(login.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();

                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(getApplicationContext(),"Enter Email and password",Toast.LENGTH_SHORT).show();
                }


            }
        });

        mCallbackManager = CallbackManager.Factory.create();

        signup=(Button)findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this,signup.class));
            }
        });
        glogin = (SignInButton)findViewById(R.id.glogin);
        glogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("click","click");
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, 102);
            }
        });
        flogin=(LoginButton)findViewById(R.id.flogin);
        flogin.setText("Continue With Facebook");
        flogin.setReadPermissions("email", "public_profile");
        flogin.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
            }
        });

    }



    public void resetpassword(String emaili)
    {
        FirebaseAuth.getInstance().sendPasswordResetEmail(emaili)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(login.this,"Recovery sent to your mail address",Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(login.this,"Error completing your Request",Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    private void handleFacebookAccessToken(final AccessToken token) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Logging In");
        progressDialog.setCancelable(false);
        progressDialog.show();

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            SharedPreferences prefs = getSharedPreferences("logindata", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=prefs.edit();
                            editor.putString("ID",user.getEmail());
                            editor.putString("UID",user.getUid());
                            editor.putString("name",user.getDisplayName());
                            editor.putInt("PUN",0);
                            editor.putBoolean("islogin",true);
                            editor.putString("isnormallogin","true");
                            editor.commit();
                            progressDialog.dismiss();
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Authentication Failed",Toast.LENGTH_SHORT).show();
                            // If sign in fails, display a message to the user.
                        }

                        // ...
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


            if(requestCode==102)
                Log.e("result","102");
            Log.e("result",String.valueOf(resultCode));

        //doubt---------/



        if(resultCode==RESULT_OK){
            if (requestCode==102){


                GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if(result.isSuccess()){

                    GoogleSignInAccount account=result.getSignInAccount();
                    FirebaseAuthWithGoogle(account);

                }

            }
        }
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    private void FirebaseAuthWithGoogle(final GoogleSignInAccount account) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Logging In");
        progressDialog.setCancelable(false);
        progressDialog.show();
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            SharedPreferences prefs = getSharedPreferences("logindata", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=prefs.edit();
                            editor.putString("ID",account.getEmail());
                            editor.putString("UID",user.getUid());
                            editor.putString("name",account.getDisplayName());
                            editor.putInt("PUN",0);
                            editor.putString("isnormallogin","true");
                            //Boolean b=task.getResult().getAdditionalUserInfo().;



                            editor.putBoolean("islogin",true);
                            editor.commit();
                            finish();

                            //updateUI(user);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Authentication Failed",Toast.LENGTH_SHORT).show();

                            // If sign in fails, display a message to the user.
                            //updateUI(null);
                        }

                        // ...
                    }
                });

    }


    public void configureSignIn(){

// Configure sign-in to request the user’s basic profile like name and email
         options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                 .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient=new GoogleApiClient.Builder(this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,options)
                .build();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
package cool.test.mycollege;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    EditText email,password,name,phone;
    int a=0;
    private FirebaseAuth mAuth;
    Button go;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        email=(EditText)findViewById(R.id.semail);
        password=(EditText)findViewById(R.id.spassword);
        name=(EditText)findViewById(R.id.sname);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Signing Up");
        progressDialog.setCancelable(false);
        mAuth=FirebaseAuth.getInstance();

        go=(Button)findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(email.getText().toString().length()==0||password.getText().toString().length()==0||name.getText().toString().length()==0||password.getText().toString().length()<6)
            {
                if (password.getText().toString().length()<6)
                {
                    Toast.makeText(signup.this,"Password should be atleast 6 units",Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(signup.this,"Enter all details",Toast.LENGTH_SHORT).show();
                }

            }
            else {
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    progressDialog.dismiss();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(signup.this, "Verification email sent to your mail address", Toast.LENGTH_LONG).show();
                                    a = 1;
                                    verify();
                                    /*SharedPreferences prefs = getSharedPreferences("logindata", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString("name", name.getText().toString());
                                    editor.putString("issignup","true");
                                    editor.putString("UID",task.getResult().getUser().getUid());
                                    editor.commit();*/

                                } else {
                                    progressDialog.dismiss();

                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(signup.this, "Process failed. " + task.getException().toString(),
                                            Toast.LENGTH_SHORT).show();

                                }

                            }
                        });
            }
            }
        });
    }

    public void verify()
    {
        mAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    //finishh();
                    SharedPreferences sharedPreferences = getSharedPreferences("details_of_login",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name",name.getText().toString());
                    editor.putString("email",email.getText().toString());
                    editor.apply();
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users").child(uid).child("name").setValue(name.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(signup.this, "name added to firebase", Toast.LENGTH_SHORT).show();
                        }
                    });
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users").child(uid).child("email").setValue(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(signup.this, "email added to firebase", Toast.LENGTH_SHORT).show();
                        }
                    });
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users").child(uid).child("type").setValue("Email").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(signup.this, "type added to firebase", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
    public void finishh(){
        SharedPreferences prefs = getSharedPreferences("logindata", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("islogin", true).apply();
        finish();

    }
}

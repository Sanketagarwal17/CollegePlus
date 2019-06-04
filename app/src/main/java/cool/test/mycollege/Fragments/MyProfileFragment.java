package cool.test.mycollege.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cool.test.mycollege.R;
import cool.test.mycollege.login;

/**
 * Created by Vipin soni on 20-12-2017.
 */

public class MyProfileFragment extends Fragment {
    Button uploads,chanPass;
    TextView name;
    FirebaseUser firebaseUser;
    AdView mAdView;

    @Override
    public void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            chanPass.setVisibility(View.GONE);
            uploads.setText("Login");
        }
        else
        {
            chanPass.setVisibility(View.VISIBLE);
            uploads.setText("LogOut");
        }
        SharedPreferences prefs = getContext().getSharedPreferences("logindata", Context.MODE_PRIVATE);
        String ss=prefs.getString("name","-Login First-");
        name.setText(ss);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.user_profile,container,false);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        chanPass = v.findViewById(R.id.button2);

        chanPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                final EditText edittext = new EditText(getActivity());
                alert.setTitle("Enter Your New Password");

                alert.setView(edittext);

                alert.setPositiveButton("Yes Option", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = edittext.getText().toString();
                        FirebaseAuth.getInstance().getCurrentUser().updatePassword(value)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

                alert.setNegativeButton("No Option", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();
            }
        });
        //String namee=firebaseUser.getDisplayName();
        name=v.findViewById(R.id.profile_name);
        SharedPreferences prefs = v.getContext().getSharedPreferences("logindata", Context.MODE_PRIVATE);
        String ss=prefs.getString("name","Your Name");

        name.setText(ss);
        uploads =v.findViewById(R.id.myuploads);


        mAdView = v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        uploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                    Toast.makeText(getActivity(), "user not null", Toast.LENGTH_SHORT).show();
                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_empty, null);
                    dialogBuilder.setView(dialogView);
                    dialogBuilder.setTitle("Log Out?");
                    dialogBuilder.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences prefs = getContext().getSharedPreferences("logindata", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putBoolean("islogin", false);
                            editor.apply();
                            FirebaseAuth.getInstance().signOut();

                            Intent i = new Intent(getContext(), login.class);
                            startActivity(i);
                        }
                    }).setNegativeButton("cancel", null);
                    AlertDialog b = dialogBuilder.create();
                    b.show();
                }
                else
                {
                    Toast.makeText(getActivity(), "LoginFirst", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), login.class);
                    startActivity(i);
                }


            }
        });



        SharedPreferences sharedPreferences=v.getContext().getSharedPreferences("logindata",Context.MODE_PRIVATE);
       if (sharedPreferences.getString("isnormallogin","false").equals("skip")){
            uploads.setText("LogIN");

            uploads.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent k=new Intent(getContext(),login.class);
                    startActivityForResult(k,142);
                }
            });

        }


        return v;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        SharedPreferences sharedPreferences=getContext().getSharedPreferences("logindata",Context.MODE_PRIVATE);
        if (sharedPreferences.getString("isnormallogin","false").equals("true")){
            uploads.setText("Log Out");


            uploads.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                        LayoutInflater inflater = getActivity().getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.dialog_empty, null);
                        dialogBuilder.setView(dialogView);
                        dialogBuilder.setTitle("Log Out?");
                        dialogBuilder.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SharedPreferences prefs = getContext().getSharedPreferences("logindata", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putBoolean("islogin", false);
                                editor.commit();
                                FirebaseAuth.getInstance().signOut();

                                Intent i = new Intent(getContext(), login.class);
                                startActivity(i);


                            }
                        }).setNegativeButton("cancel", null);


                        AlertDialog b = dialogBuilder.create();
                        b.show();
                    }
                    else
                    {
                        Intent i = new Intent(getContext(), login.class);
                        startActivity(i);
                    }





                }
            });



        }
        else if (sharedPreferences.getString("isnormallogin","false").equals("skip")){

            uploads.setText("LogIN");

            uploads.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent k=new Intent(getContext(),login.class);
                    startActivity(k);
                }
            });


        }


        }
}

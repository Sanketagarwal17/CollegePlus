package cool.test.mycollege.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cool.test.mycollege.R;
import cool.test.mycollege.login;

/**
 * Created by Vipin soni on 20-12-2017.
 */

public class MyProfileFragment extends Fragment {
    Button uploads;
    TextView name;
    FirebaseUser firebaseUser;
    AdView mAdView;

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs = getContext().getSharedPreferences("logindata", Context.MODE_PRIVATE);
        String ss=prefs.getString("name","-Login First-");

        name.setText(ss);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.user_profile,container,false);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

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


                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_empty, null);
                dialogBuilder.setView(dialogView);
                dialogBuilder.setTitle("Log Out?");
                dialogBuilder.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences prefs = getContext().getSharedPreferences("logindata", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=prefs.edit();
                        editor.putBoolean("islogin",false);
                        editor.commit();
                        FirebaseAuth.getInstance().signOut();

                        Intent i=new Intent(getContext(),login.class);
                        startActivity(i);


                    }
                }).setNegativeButton("cancel",null);


                AlertDialog b = dialogBuilder.create();
                b.show();






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


                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_empty, null);
                    dialogBuilder.setView(dialogView);
                    dialogBuilder.setTitle("Log Out?");
                    dialogBuilder.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            SharedPreferences prefs = getContext().getSharedPreferences("logindata", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=prefs.edit();
                            editor.putBoolean("islogin",false);
                            editor.commit();
                            FirebaseAuth.getInstance().signOut();

                            Intent i=new Intent(getContext(),login.class);
                            startActivity(i);


                        }
                    }).setNegativeButton("cancel",null);


                    AlertDialog b = dialogBuilder.create();
                    b.show();






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

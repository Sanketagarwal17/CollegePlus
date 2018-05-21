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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cool.test.mycollege.HomePage;
import cool.test.mycollege.MainActivity;
import cool.test.mycollege.Myuploads;
import cool.test.mycollege.R;
import cool.test.mycollege.login;

/**
 * Created by Vipin soni on 20-12-2017.
 */

public class MyProfileFragment extends Fragment {
    Button uploads;
    TextView name;
    FirebaseUser firebaseUser;

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs = getContext().getSharedPreferences("logindata", Context.MODE_PRIVATE);
        String ss=prefs.getString("name","Your Name");

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


        return v;
    }
}

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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import cool.test.mycollege.R;
import cool.test.mycollege.login;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Vipin soni on 20-12-2017.
 */

public class MyProfileFragment extends Fragment {
    Button uploads,chanPass;
    TextView name;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    AdView mAdView;
    ImageButton imageButton;
    SharedPreferences sharedPreferences;
    @Override
    public void onResume() {
        super.onResume();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            chanPass.setVisibility(View.GONE);
            uploads.setText("Login");
            name.setText("Login First");
        } else {
            for(UserInfo user: FirebaseAuth.getInstance().getCurrentUser().getProviderData()){

                if(user.getProviderId().equals("facebook.com")){
                    //logged with Facebook

                }

               else if(user.getProviderId().equals("google.com")){
                    //logged with google

                }
               else
                {
                    chanPass.setVisibility(View.VISIBLE);
                }


            }

            uploads.setText("LogOut");
            String nameFromSP = sharedPreferences.getString("name","Dummy");
            name.setText(nameFromSP);
            String image = sharedPreferences.getString("image",null);
            if(image != "no_user") {
                Glide.with(getActivity())
                        .asBitmap().load(image)
                        .into(imageButton);
            }
            else
            {
                Glide.with(getActivity())
                        .load(getResources().getIdentifier("cyber","drawable",getActivity().getPackageName()))
                        .into(imageButton);
            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.user_profile,container,false);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        chanPass = v.findViewById(R.id.button2);
        name = v.findViewById(R.id.profile_name);
        firebaseAuth = FirebaseAuth.getInstance();
        imageButton = v.findViewById(R.id.user_profile_photo);
        sharedPreferences = getActivity().getSharedPreferences("details_of_login",getActivity().MODE_PRIVATE);
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

        uploads =v.findViewById(R.id.myuploads);


        mAdView = v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        uploads.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            startActivity(new Intent(getActivity(),login.class));
        }
        else
        {
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_empty, null);
            dialogBuilder.setView(dialogView);
            dialogBuilder.setTitle("Log Out?");
            dialogBuilder.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("details_of_login",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name","Dummy");
                    editor.putString("email","Dummy.com");
                    editor.putString("image","no_user");
                    editor.apply();
                    FirebaseAuth.getInstance().signOut();
                    LoginManager.getInstance().logOut();
                    Intent i = new Intent(getActivity(), login.class);
                    startActivity(i);


                }
            }).setNegativeButton("cancel", null);


            AlertDialog b = dialogBuilder.create();
            b.show();
        }

    }
});
        return v;
    }

}

package cool.test.mycollege.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import cool.test.mycollege.R;
import cool.test.mycollege.StudyMaterialOnline;

/**
 * Created by Vipin soni on 20-12-2017.
 */

public class StudyMaterial extends Fragment implements AdapterView.OnItemSelectedListener{
    Spinner studyspinner,branchspinner;
    Button fab;
    String[] objects={"1st YEAR","2nd YEAR (3rd Sem)","2nd YEAR (4th Sem)","3rd YEAR (5th Sem)","3rd YEAR (6th Sem)","4th YEAR (7th Sem)","4th YEAR (8th Sem)"};
    ArrayList<String> arrayList;
    Bundle bla=new Bundle();
    AdView mAdView;
    int year=0,branch=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.study_material_launch,container,false);

        studyspinner=v.findViewById(R.id.yearspinner);
        studyspinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,objects);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studyspinner.setAdapter(aa);


        fab=v.findViewById(R.id.goooo);
        branchspinner=v.findViewById(R.id.branchspinner);
        branchspinner.setOnItemSelectedListener(this);

        arrayList=new ArrayList<>();
        arrayList.add(0,"Hard Sem");
        arrayList.add(1,"Easy Sem");

/*        AdView adView = new AdView(v.getContext());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-9534694647722812/9002584171");*/

        mAdView = v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        studyspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                arrayList=new ArrayList<>();
                arrayList.add(0,"Hard Sem");
                arrayList.add(1,"Easy Sem");

                if(position==0)
                {
                   arrayList=new ArrayList<>();
                    arrayList.add(0,"Hard Sem");
                    arrayList.add(1,"Easy Sem");

                }
                    else {


                    arrayList=new ArrayList<>();
                    arrayList.add(0,"CSE");
                    arrayList.add(1,"Mathematics and Computing");
                    arrayList.add(2,"Electronics and communication");
                    arrayList.add(3,"Electronics and Instrumentation");
                    arrayList.add(4,"Electrical");
                    arrayList.add(5,"Mechanical");
                    arrayList.add(6,"Civil");
                    arrayList.add(7,"Mining");
                    arrayList.add(8,"Mining Machinery Engineering");
                    arrayList.add(9,"Petroleum Engineering");
                    arrayList.add(10,"Applied Geology");
                    arrayList.add(11,"Environmental Engineering");
                    arrayList.add(12,"Applied Geophysics");
                    arrayList.add(13,"Engineering Physics");
                    arrayList.add(14,"Chemical Engineering");
                    arrayList.add(15,"Mineral Engineering");



                }


                ArrayAdapter aaj = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,arrayList);
                aaj.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                branchspinner.setAdapter(aaj);
                year=position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        branchspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                branch=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
fab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        if (year>0)
        {
            Toast.makeText(getContext(),"Content To be Uploaded Soon",Toast.LENGTH_SHORT).show();
        }
        else {
            /*Intent i = new Intent(getContext(), StudyMaterialOnline.class);
            bla.putInt("year", year);
            bla.putInt("branch", branch);
            i.putExtras(bla);
            startActivity(i);*/
            Uri uri = Uri.parse("https://drive.google.com/drive/u/0/folders/0B_XYsz-weqCGWFg1VHlVTEhyM0k"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
});








        return v;
    }

















    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

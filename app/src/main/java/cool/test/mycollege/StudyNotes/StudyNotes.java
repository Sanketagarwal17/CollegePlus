package cool.test.mycollege.StudyNotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import cool.test.mycollege.R;

public class StudyNotes extends AppCompatActivity {


    ArrayList<DownLoadStudyNotesModel> arrayList;
    DownloadStudyNotesAdapter downloadStudyNotesAdapter;

    TextView year1,branch1;
    RecyclerView Allnotesrecycler;
    EditText search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_notes);

        year1 = findViewById(R.id.year);
        branch1 = findViewById(R.id.branch);
        Allnotesrecycler = findViewById(R.id.allnotesrecycler);
        final String year2 = getIntent().getStringExtra("Year");
        final String branch2 = getIntent().getStringExtra("Branch");
        if (year2.equals("0") && branch2.equals("0")) {

            year1.setText("1 st Year");
            branch1.setText("Hard Semester");
        } else if (year2.equals("0") && branch2.equals("1")) {
            year1.setText("1 st Year");
            branch1.setText(" Easy Semester");
        } else {
            if (branch2.equals("0"))
                branch1.setText("CSE");
            else if (branch2.equals("1"))
                branch1.setText("Mathematics and Computing");
            else if (branch2.equals("2"))
                branch1.setText("Electronics and communication");
            else if (branch2.equals("3"))
                branch1.setText("Electronics and Instrumentation");
            else if (branch2.equals("4"))
                branch1.setText("Electrical");
            else if (branch2.equals("5"))
                branch1.setText("Mechanical");
            else if (branch2.equals("6"))
                branch1.setText("Civil");
            else if (branch2.equals("7"))
                branch1.setText("Mining");
            else if (branch2.equals("8"))
                branch1.setText("Mining Machinery Engineering");
            else if (branch2.equals("9"))
                branch1.setText("Petroleum Engineering");
            else if (branch2.equals("10"))
                branch1.setText("Applied Geology");
            else if (branch2.equals("11"))
                branch1.setText("Environmental Engineering");
            else if (branch2.equals("12"))
                branch1.setText("Applied Geophysics");
            else if (branch2.equals("13"))
                branch1.setText("Engineering Physics");
            else if (branch2.equals("14"))
                branch1.setText("Chemical Engineering");
            else if (branch2.equals("15"))
                branch1.setText("Mineral Engineering");
        }


        if(year2.equals("1"))
            year1.setText("2 nd Year");
        else if(year2.equals("2"))
            year1.setText("3 rd Year");
        else if(year2.equals("3"))
            year1.setText("Final Year");
            Log.e("Running", year2 + branch2);

            arrayList = new ArrayList<>();
            Allnotesrecycler.setHasFixedSize(false);
            Allnotesrecycler.setLayoutManager(new LinearLayoutManager(this));
            downloadStudyNotesAdapter = new DownloadStudyNotesAdapter(this, arrayList);
            Allnotesrecycler.setAdapter(downloadStudyNotesAdapter);


            DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("StudyNotes");

            DatabaseReference databaseReference = mDatabaseReference.child(year2).child(branch2);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot db : dataSnapshot.getChildren()) {
                        Log.e("Running", "1");
                        Log.e("Running", "2");

                        String name = db.child("subjectname").getValue().toString();
                        String topic = db.child("subjecttopic").getValue().toString();
                        String Url = db.child("url").getValue().toString();
                        Log.e("Running", name + topic + Url);

                        DownLoadStudyNotesModel downLoadStudyNotesModel = new DownLoadStudyNotesModel(name, topic, Url, Integer.valueOf(year2), Integer.valueOf(branch2));
                        arrayList.add(downLoadStudyNotesModel);
                        downloadStudyNotesAdapter.notifyDataSetChanged();
                        Log.e("Running", "3");

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(StudyNotes.this, databaseError.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });


            search = findViewById(R.id.searchengine);

            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    filter(s.toString());
                }

                private void filter(String so) {
                    ArrayList<DownLoadStudyNotesModel> filterdNames = new ArrayList<>();

                    //looping through existing elements
                    for (DownLoadStudyNotesModel se : arrayList) {
                        //if the existing elements contains the search input
                        if (se.getName().toLowerCase().contains(so.toLowerCase())) {
                            //adding the element to filtered list
                            filterdNames.add(se);
                        } else if (se.getTopic().toLowerCase().contains(so.toLowerCase())) {
                            //adding the element to filtered list
                            filterdNames.add(se);
                        }
                    }

                    //calling a method of the adapter class and passing the filtered list
                    downloadStudyNotesAdapter.filterList(filterdNames);


                }
            });


        }
    }









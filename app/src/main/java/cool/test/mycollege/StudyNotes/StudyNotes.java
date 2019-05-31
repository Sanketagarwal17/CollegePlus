package cool.test.mycollege.StudyNotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_notes);

        year1=findViewById(R.id.year);
        branch1=findViewById(R.id.branch);
        Allnotesrecycler=findViewById(R.id.allnotesrecycler);
        final String year2=getIntent().getStringExtra("Year");
        final String branch2=getIntent().getStringExtra("Branch");

        if(year2=="0" && branch2=="0")
        {
            year1.setText("1 st Year");
            branch1.setText("Hard Semester");
        }
        else if(year2=="0" && branch2=="1")
        {
            year1.setText("1 st Year");
            branch1.setText(" Easy Semester");
        }
        else
        {
            year1.setText(year2);
            branch1.setText(branch2);
        }







        year1.setText(year2);
        branch1.setText(branch2);

        Log.e("Running",year2+branch2);

        arrayList=new ArrayList<>();
        Allnotesrecycler.setHasFixedSize(false);
        Allnotesrecycler.setLayoutManager(new LinearLayoutManager(this));
         downloadStudyNotesAdapter=new DownloadStudyNotesAdapter(this,arrayList);
        Allnotesrecycler.setAdapter(downloadStudyNotesAdapter);



        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("StudyNotes");

        DatabaseReference databaseReference=mDatabaseReference.child(year2).child(branch2);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot db : dataSnapshot.getChildren()) {
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
 Toast.makeText(StudyNotes.this,databaseError.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });







    }
}

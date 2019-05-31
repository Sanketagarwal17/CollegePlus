package cool.test.mycollege.StudyNotes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import cool.test.mycollege.R;

public class UploadStudyNotes extends AppCompatActivity {
EditText psubjectname,psubjecttopic;
Button upload;
    Spinner studyspinner,branchspinner;
    String[] objects={"1st YEAR","2nd YEAR (3rd Sem)","2nd YEAR (4th Sem)","3rd YEAR (5th Sem)","3rd YEAR (6th Sem)","4th YEAR (7th Sem)","4th YEAR (8th Sem)"};
    ArrayList<String> arrayList;
    Bundle bla=new Bundle();
    int uyear=0,ubranch=0;
    String usubjectname,usubjecttopic;
    final static int PICK_PDF_CODE = 2342;
StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_study_notes);

    psubjectname=findViewById(R.id.uploadsubjectname);
    psubjecttopic=findViewById(R.id.uploadtopic);



    upload=findViewById(R.id.uploadpdf);

        studyspinner=findViewById(R.id.uyearspinner);
        //studyspinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(UploadStudyNotes.this,android.R.layout.simple_spinner_item,objects);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studyspinner.setAdapter(aa);



        branchspinner=findViewById(R.id.ubranchspinner);
        //branchspinner.setOnItemSelectedListener(this);

        arrayList=new ArrayList<>();
        arrayList.add(0,"Hard Sem");
        arrayList.add(1,"Easy Sem");

/*        AdView adView = new AdView(v.getContext());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-9534694647722812/9002584171");*/



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


                ArrayAdapter aaj = new ArrayAdapter(UploadStudyNotes.this,android.R.layout.simple_spinner_item,arrayList);
                aaj.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                branchspinner.setAdapter(aaj);
                uyear=position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        branchspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ubranch=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);





upload.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        usubjectname=psubjectname.getText().toString();
        usubjecttopic=psubjecttopic.getText().toString();
        getPDF();

    }
});




    }



    private void getPDF() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            return;
        }

        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                uploadFile(data.getData());
            }else{
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadFile(Uri data) {
        //progressBar.setVisibility(View.VISIBLE);
        Toast.makeText(UploadStudyNotes.this,"Enter Successfully",Toast.LENGTH_LONG).show();

        StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".pdf");
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //progressBar.setVisibility(View.GONE);
                        //textViewStatus.setText("File Uploaded Successfully");
                        Toast.makeText(UploadStudyNotes.this,"Upload Successfully",Toast.LENGTH_LONG).show();

                        Log.e("Uploading",usubjectname+usubjecttopic);
                        UploadStudyNotesModel uploadobject = new UploadStudyNotesModel(taskSnapshot.getDownloadUrl().toString(),usubjectname,usubjecttopic,ubranch,uyear);
                        //mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(uploadobject);
                        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
                        String reqKey = databaseReference.push().getKey();

                        databaseReference.child("StudyNotes").child(Integer.toString(uyear)).child(Integer.toString(ubranch)).child(reqKey).setValue(uploadobject).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(UploadStudyNotes.this, "Upload Successfully  2", Toast.LENGTH_LONG).show();
                                }else
                                {
                                    Toast.makeText(UploadStudyNotes.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                }

                            }
                        });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        // textViewStatus.setText((int) progress + "% Uploading...");
                    }
                });

    }
}

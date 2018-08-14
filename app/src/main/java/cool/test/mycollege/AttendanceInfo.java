package cool.test.mycollege;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import cool.test.mycollege.Helpers.DBTools;
import cool.test.mycollege.Helpers.InfoAdapter;
import cool.test.mycollege.Helpers.SettingsAdapter;


public class AttendanceInfo extends AppCompatActivity {


    InfoAdapter adapter;
    DBTools ourDB = new DBTools(this);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_info);


        final DBTools mydb= new DBTools(this);
        RecyclerView infoRecycle = (RecyclerView) findViewById(R.id.info_recycle);

        adapter = new InfoAdapter(this, ourDB.getthedamnList());
        infoRecycle.setAdapter(adapter);
        infoRecycle.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_attendance_settings);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {



                final DBTools myownDB = new DBTools(getApplicationContext());

                LayoutInflater inflater = getLayoutInflater();
                final View rootView=inflater.inflate(R.layout.dialog_layout, null);




                AlertDialog.Builder builder1 = new AlertDialog.Builder(rootView.getContext());
                builder1.setView(rootView);
                builder1.setTitle("Enter the Subject Name");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "ADD",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                EditText subjectName =(EditText)rootView.findViewById(R.id.subject_name);

                                String subjectNameString = subjectName.getText().toString();
                                if(!Objects.equals(subjectNameString, "")) {
                                    myownDB.insertData(subjectNameString);
                                }
                                //onCreate(savedInstanceState);
                                ArrayList<String> subarray = new ArrayList<>();

                                subarray=myownDB.getthedamnList();
                                RecyclerView infoRecycle = (RecyclerView) findViewById(R.id.info_recycle);

                                adapter = new InfoAdapter(view.getContext(), mydb.getthedamnList());
                                infoRecycle.setAdapter(adapter);
                                infoRecycle.setLayoutManager(new LinearLayoutManager(view.getContext()));



                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getApplicationContext(),"Cancelled", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();



            }
        });
    }
}

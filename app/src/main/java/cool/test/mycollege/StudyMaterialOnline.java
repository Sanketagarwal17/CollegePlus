package cool.test.mycollege;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cool.test.mycollege.Helpers.AdapterStudyMaterial;
import cool.test.mycollege.MyDataTypes.StudyMaterialBookData;

public class StudyMaterialOnline extends AppCompatActivity {
    RecyclerView recyclerView;
    int year, branch;
    FirebaseDatabase database;
    ProgressBar progressBar;


    @Override
    public void onStart() {
        super.onStart();

        database = FirebaseDatabase.getInstance();
        DatabaseReference hell = database.getReference("StudyMaterial").child("1").child("hardsem");

        FirebaseRecyclerAdapter<subjectdata, viewHolder> firebaseRecyclerAdapter = new
                FirebaseRecyclerAdapter<subjectdata, viewHolder>(subjectdata.class, R.layout.card_view_subjects, viewHolder.class, hell) {

                    @Override
                    protected void populateViewHolder(viewHolder viewholder, subjectdata model, int position) {

                        progressBar.setVisibility(View.INVISIBLE);

                        viewholder.givemodel(model);
                        viewholder.setsubname(model.getSubjectname());
                        viewholder.setlistener1(model.getUrl());
                        viewholder.setlistener2();


                    }


                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent iii = getIntent();
        Bundle b = iii.getExtras();
        year = b.getInt("year");
        branch = b.getInt("branch");
        Toast.makeText(this, String.valueOf(year) + String.valueOf(branch), Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_study_material_online);
        recyclerView = (RecyclerView) findViewById(R.id.studyrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        progressBar = findViewById(R.id.viuyt);


    }


    public static class viewHolder extends RecyclerView.ViewHolder {
        View productview;
        Activity a;
        subjectdata data;


        public void geta(Activity b) {
            a = b;
        }

        public void givemodel(subjectdata d) {
            data = d;
        }

        public viewHolder(final View itemView) {

            super(itemView);
            productview = itemView;


        }


        public void setsubname(String stitle) {
            TextView a = productview.findViewById(R.id.producttittle);
            a.setText(stitle);


        }


        public void setlistener1(final String a) {
            FloatingActionButton b = productview.findViewById(R.id.linkdownload);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //download();

                    new DownloadFileFromURL().execute(a);

                }
            });
        }

        public void setlistener2() {
            FloatingActionButton b = productview.findViewById(R.id.linkshare);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //share();

                }
            });
        }


    }


    public static class subjectdata {
        String name, url;

        public subjectdata() {

        }

        public subjectdata(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public String getSubjectname() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }

}

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();

                // this will be useful so that you can show a tipical 0-100%
                // progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);

                // Output stream
                OutputStream output = new FileOutputStream(Environment
                        .getExternalStorageDirectory().toString()
                        + "/"+"blabla");

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
        //    pDialog.setProgress(Integer.parseInt(progress[0]));
        }


        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
          //  dismissDialog(progress_bar_type);

        }



}

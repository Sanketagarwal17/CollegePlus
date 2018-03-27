package cool.test.mycollege;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import cool.test.mycollege.Helpers.DBTools;
import cool.test.mycollege.Helpers.InfoAdapter;


public class AttendanceInfo extends AppCompatActivity {


    InfoAdapter adapter;
    DBTools ourDB = new DBTools(this);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_info);


        RecyclerView infoRecycle = (RecyclerView) findViewById(R.id.info_recycle);

        adapter = new InfoAdapter(this, ourDB.getthedamnList());
        infoRecycle.setAdapter(adapter);
        infoRecycle.setLayoutManager(new LinearLayoutManager(this));
    }
}

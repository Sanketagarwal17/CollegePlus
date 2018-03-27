package cool.test.mycollege;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cool.test.mycollege.Helpers.AugustineAdapter;
import cool.test.mycollege.Helpers.MyReceiver;
import cool.test.mycollege.Helpers.WeekSubjectDB;
import cool.test.mycollege.MyDataTypes.SubjectInfo;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    WeekSubjectDB today = new WeekSubjectDB(this);
    private AugustineAdapter adapter;

    public static String getCustomDateString(Date date) {
        SimpleDateFormat tmp = new SimpleDateFormat("MMMM d");

        String str = tmp.format(date);
        str = str.substring(0, 1).toUpperCase() + str.substring(1);

        if (date.getDate() > 10 && date.getDate() < 14)
            str = str + "th, ";
        else {
            if (str.endsWith("1")) str = str + "st, ";
            else if (str.endsWith("2")) str = str + "nd, ";
            else if (str.endsWith("3")) str = str + "rd, ";
            else str = str + "th, ";
        }

        tmp = new SimpleDateFormat("yyyy");
        str = str + tmp.format(date);

        return str;
    }


    public ArrayList<SubjectInfo> getDatat() {
        ArrayList<SubjectInfo> theTempList = new ArrayList<SubjectInfo>();
        ArrayList<String> current = new ArrayList<>();
        ArrayList<String> elsey = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();


        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                current = today.getthedamnList(0);
                break;
            case Calendar.TUESDAY:
                current = today.getthedamnList(1);
                break;
            case Calendar.WEDNESDAY:
                current = today.getthedamnList(2);
                break;
            case Calendar.THURSDAY:
                current = today.getthedamnList(3);
                break;
            case Calendar.FRIDAY:
                current = today.getthedamnList(4);
                break;
            default:
                current = elsey;
                break;
        }


        for (int i = 0; i < current.size(); i++) {
            SubjectInfo _current = new SubjectInfo();

            _current._id = i;
            _current._subjectname = current.get(i);
            theTempList.add(_current);

        }
        return theTempList;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //CardView myCard=(CardView)findViewById(R.id.homeSubjectCard);
        RecyclerView myRecycler = (RecyclerView) findViewById(R.id.homeRecycle);

        adapter = new AugustineAdapter(this, getDatat());
        myRecycler.setAdapter(adapter);
        myRecycler.setLayoutManager(new LinearLayoutManager(this));

        TextView dayText = (TextView) findViewById(R.id.dayTitle);

        Calendar calendar = Calendar.getInstance();
        Date today = new Date();

        String theDamnText = getCustomDateString(today);


        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                dayText.setText("Monday");
                break;
            case Calendar.TUESDAY:
                dayText.setText("Tuesday");
                break;
            case Calendar.WEDNESDAY:
                dayText.setText("Wednesday");
                break;
            case Calendar.THURSDAY:
                dayText.setText("Thursday");
                break;
            case Calendar.FRIDAY:
                dayText.setText("Friday");
                break;
            case Calendar.SATURDAY:
                dayText.setText("Saturday");
                break;
            case Calendar.SUNDAY:
                dayText.setText("Sunday");
                break;
        }


        TextView dateText = (TextView) findViewById(R.id.dateTitle);

        switch (calendar.get(Calendar.DAY_OF_WEEK)) {

            case Calendar.SATURDAY:
                dateText.setText("Matiyao Be");
                break;
            case Calendar.SUNDAY:
                dateText.setText("Matiyao Be");
                break;

            default:
                dateText.setText(theDamnText);
                break;


        }

        //TODO: Add Shared Preferences
       /* SharedPreferences CurrentDay = getSharedPreferences("theCurrentDay", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = CurrentDay.edit();
        prefEditor.putString("theDate", theDamnText);


        if(!CurrentDay.contains("theCurrentDay")||(!Objects.equals(CurrentDay.getString("theCurrentDay", ""), theDamnText))){


            prefEditor.putString("theDate", theDamnText);


            prefEditor.putString("myDefString", "wowsaBowsa");

            prefEditor.commit();
        }



        /*
        String value = settings.getString("key", "");// Retrieving from the shared Preferen c s
         */


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Calendar theCalendar = Calendar.getInstance();
        theCalendar.setTimeInMillis(System.currentTimeMillis());
        theCalendar.set(Calendar.HOUR_OF_DAY, 19);
        theCalendar.set(Calendar.MINUTE, 0);
        theCalendar.set(Calendar.SECOND, 1);


        Intent notifyIntent = new Intent(this, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, theCalendar.getTimeInMillis(),// this is where the time goes
                1000 * 60 * 60 * 24, pendingIntent);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.attend_settings) {

            Intent settingsIntent = new Intent(this, AttendanceSettingsActivity.class);
            startActivity(settingsIntent);

        } else if (id == R.id.attendance_info) {
            Intent infoIntent = new Intent(this, AttendanceInfo.class);
            startActivity(infoIntent);
        } else if (id == R.id.exit) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.attendance_manager) {

            Intent attendance = new Intent(this, EditAttendanceinWeek.class);
            startActivity(attendance);


        } else if (id == R.id.nav_people) {

            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.settings) {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.bug_report) {

            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

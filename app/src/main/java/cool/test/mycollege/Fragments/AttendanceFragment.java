package cool.test.mycollege.Fragments;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cool.test.mycollege.Devs;
import cool.test.mycollege.EditAttendanceinWeek;
import cool.test.mycollege.Helpers.AugustineAdapter;
import cool.test.mycollege.Helpers.MyReceiver;
import cool.test.mycollege.Helpers.WeekSubjectDB;
import cool.test.mycollege.HomePage;
import cool.test.mycollege.MyDataTypes.SubjectInfo;
import cool.test.mycollege.R;


/**
 * Created by Vipin soni on 20-12-2017.
 */

public class AttendanceFragment extends Fragment {
    AugustineAdapter adapter;

    WeekSubjectDB today;

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
    public void shyaugustine(){
        Intent iiiu=new Intent(getContext(),Devs.class);
        startActivity(iiiu);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_attendancee,container,false);
        RecyclerView myRecycler = (RecyclerView) v.findViewById(R.id.homeRecycle);
        today = new WeekSubjectDB(v.getContext());
        adapter = new AugustineAdapter(getContext(), getDatat());
        myRecycler.setAdapter(adapter);
        myRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        TextView dayText = (TextView) v.findViewById(R.id.dayTitle);
        ImageView shya=v.findViewById(R.id.rocks);
        shya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shyaugustine();
            }
        });
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


        TextView dateText = (TextView) v.findViewById(R.id.dateTitle);

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



        Calendar theCalendar = Calendar.getInstance();
        theCalendar.setTimeInMillis(System.currentTimeMillis());
        theCalendar.set(Calendar.HOUR_OF_DAY, 19);
        theCalendar.set(Calendar.MINUTE, 0);
        theCalendar.set(Calendar.SECOND, 1);


        Intent notifyIntent = new Intent(v.getContext(), MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (v.getContext(), 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) v.getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, theCalendar.getTimeInMillis(),// this is where the time goes
                1000 * 60 * 60 * 24, pendingIntent);








        return v;

    }

    public void starteditattendance()
    {
        Intent attendance = new Intent(getView().getContext(), EditAttendanceinWeek.class);
        startActivity(attendance);

    }
}

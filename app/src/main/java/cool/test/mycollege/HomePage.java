package cool.test.mycollege;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;

import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import cool.test.mycollege.Fragments.AttendanceFragment;
import cool.test.mycollege.Fragments.LostAndFound;
import cool.test.mycollege.Fragments.MartFragmentMain;
import cool.test.mycollege.Fragments.MyProfileFragment;
import cool.test.mycollege.Fragments.StudyMaterial;
import cool.test.mycollege.Fragments.TrendingFragment;
import cool.test.mycollege.Fragments.WhatToDo;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
Intent ii;
FirebaseDatabase database;
MenuItem menuItem1,menuItem2,menuItem3,blab,menuitem4;
int once=0;
int id_01;
    Fragment menuFragment=null;
    int started;
    int menuhandler;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==46||requestCode==47||requestCode==48){
            getFragmentManager().beginTransaction().detach(menuFragment).attach(menuFragment).commit();
        }
       // Toast.makeText(this,String.valueOf(requestCode)+" -  "+String.valueOf(resultCode),Toast.LENGTH_LONG).show();
        if(requestCode==10){
            SharedPreferences prefs = getSharedPreferences("logindata", Context.MODE_PRIVATE);
            String ss=prefs.getString("issignup","false");
            SharedPreferences.Editor editor=prefs.edit();
            editor.putString("issignup","false");
            String pp=prefs.getString("isnormallogin","false");
            if(ss.contentEquals("true")&&pp.contentEquals("true"))
            {

                createbasicprofile();

            }
            else if (ss.contentEquals("false")&&pp.contentEquals("false")){
                createbasicprofile();
            }



        }
    }

    private void createbasicprofile() {

        SharedPreferences prefs = getSharedPreferences("logindata", Context.MODE_PRIVATE);
        String ss=prefs.getString("UID","Cool");
        String name=prefs.getString("name","Cool");
        String username=prefs.getString("ID","Error 404");
        database=FirebaseDatabase.getInstance();
        DatabaseReference referencev=database.getReference("Users/"+ss);
        referencev.child("name").setValue(name);
        SharedPreferences.Editor editor=prefs.edit();
        //referencev.child("PUN").setValue(0);

        referencev.child("email").setValue(username, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                } else {

                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_attendance_fragment, menu);
        started=123;
         menuItem1 = menu.findItem(R.id.editweek);
        menuItem1.setVisible(true);//
        menuItem2= menu.findItem(R.id.settings);
        menuItem2.setVisible(false);
        menuItem3= menu.findItem(R.id.showattendance);
        menuItem3.setVisible(true);
        menuitem4=menu.findItem(R.id.myuploaddd);
        menuitem4.setVisible(false);
        blab=menu.findItem(R.id.alarm);
        blab.setVisible(true);



        super.onCreateOptionsMenu(menu);
        return true;
    }
    public void alarmButtonClicked(final MenuItem item)
    {
        LayoutInflater inflater2 = LayoutInflater.from(this);//LayoutInflater.from(AttendanceSettingsActivity.this);

        final View view = inflater2.inflate(R.layout.dialog_alarm, null);

        final TimePicker myTime = (TimePicker) view.findViewById(R.id.timePicker);
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());

        builder1.setView(view);
        builder1.setMessage("Set the time to notify for this subject");
        builder1.setTitle("Set Time");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        final Calendar theCalendar = Calendar.getInstance();

                        final long theFutureTime = ((myTime.getHour() - theCalendar.get(Calendar.HOUR_OF_DAY)) * 60 * 60 * 1000 + (myTime.getMinute() - theCalendar.get(Calendar.MINUTE)) * 60 * 1000);
                        final int theFutureHour = myTime.getHour() - theCalendar.get(Calendar.HOUR_OF_DAY);
                        final int theFutureMinute = theFutureHour * 60 + (myTime.getMinute() - theCalendar.get(Calendar.MINUTE));



                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.setTimeInMillis(System.currentTimeMillis());
                        myCalendar.set(Calendar.HOUR_OF_DAY, myTime.getHour());
                        myCalendar.set(Calendar.MINUTE, myTime.getMinute());
                        myCalendar.set(Calendar.SECOND, 1);





                        Toast.makeText(view.getContext(), "Class" + " due in " + String.valueOf(theFutureMinute / 60) + " hour(s) and " + String.valueOf(theFutureMinute % 60) + " minute(s)", Toast.LENGTH_SHORT).show();


                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(view.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("hio",String.valueOf(started)+"  "+String.valueOf(menuhandler));
        if (started==123) {
            if (menuhandler == 1) {


                menuitem4.setVisible(false);

                menuItem2.setVisible(false);
                menuItem1.setVisible(true);
                blab.setVisible(true);

                menuItem3.setVisible(true);
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.editweek) {

            Intent attendanceIntent = new Intent(this,EditAttendanceinWeek.class);
            startActivityForResult(attendanceIntent,46);
        }  else if (id==R.id.settings){
            Intent attendanceIntent = new Intent(this,AttendanceSettingsActivity.class);
            startActivityForResult(attendanceIntent,47);

        }else if (id == R.id.showattendance) {

            Intent attendanceIntent = new Intent(this,AttendanceInfo.class);
            startActivityForResult(attendanceIntent,48);

        }
        else if (id== R.id.myuploaddd){
            Intent i=new Intent(this, Myuploads.class);
            startActivity(i);

        }





        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);



       /* try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "cool.test.mycollege",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.e("KeyHash:", "thisone1");

            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("KeyHash:", "thisone2");

        } catch (NoSuchAlgorithmException e) {
            Log.e("KeyHash:", "thisone3");

        }*/
        setSupportActionBar(toolbar);
        menuhandler=1;
        SharedPreferences prefs = getSharedPreferences("logindata", Context.MODE_PRIVATE);


            if (once==0){
                    MobileAds.initialize(this, "ca-app-pub-9534694647722812~4161321035");
                    once=1;

            }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment menuFragment;
        menuFragment = new MyProfileFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment, menuFragment)
                .commit();






        checkforpun();
        if (prefs.getString("isnormallogin","").equals("true")||prefs.getString("isnormallogin","").equals("skip"))
        {}
        else {
            Intent i=new Intent(this,login.class);
            startActivityForResult(i,10);
        }




        menuFragment = new AttendanceFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment, menuFragment)
                .commit();
//

        onNavigationItemSelected(null);

    }


    private void checkforpun() {
        SharedPreferences prefs = getSharedPreferences("logindata", Context.MODE_PRIVATE);
        String ss=prefs.getString("UID","Cool");
        int ver=prefs.getInt("VERSON",1);
        if(ver!=1)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(HomePage.this).create();
            alertDialog.setTitle("New Verson Required!!");
            alertDialog.setCancelable(false);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            int pid = android.os.Process.myPid();
                            android.os.Process.killProcess(pid);
                            dialog.dismiss();

                        }
                    });
            alertDialog.show();


        }


        database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference();
        DatabaseReference ref2=reference.child("verson");
        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int yoo = dataSnapshot.getValue(int.class);


                SharedPreferences prefs = getSharedPreferences("logindata", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("VERSON",yoo);
                editor.commit();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference ref=reference.child("Users").child(ss).child("PUN");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    int no = dataSnapshot.getValue(int.class);


                SharedPreferences prefs = getSharedPreferences("logindata", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("PUN", no);
                editor.commit();
            }
            else{
                    SharedPreferences prefs = getSharedPreferences("logindata", Context.MODE_PRIVATE);
                    String vil=prefs.getString("UID","NULL");
                    database=FirebaseDatabase.getInstance();
                    DatabaseReference rev=database.getReference("Users/"+vil);
                    rev.child("PUN").setValue(0);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (menuhandler!=1){
            menuhandler=1;
            menuFragment = new AttendanceFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, menuFragment)
                    .commit();

        }
        else {


             final AlertDialog.Builder buildd = new AlertDialog.Builder(HomePage.this);

            buildd.setMessage("Do You Want To Exit");
            buildd.setTitle("College +");
            buildd.setCancelable(true);
            buildd.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    System.exit(0);
                }
            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog fgi=buildd.create();
            fgi.show();






        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id;
        if (item==null)
            id=R.id.cattendancemanager;
        else
        id = item.getItemId();
        id_01=id;
if (id==R.id.cprofile){
    menuhandler=0;
            menuitem4.setVisible(true);
            menuItem2.setVisible(false);
            blab.setVisible(false);

            menuItem1.setVisible(false);
            menuItem3.setVisible(false);
        }

        else if (id == R.id.cattendancemanager){
    if (item==null){}
    else {
        menuitem4.setVisible(false);

        menuItem2.setVisible(false);
        menuItem1.setVisible(true);
        blab.setVisible(true);

        menuItem3.setVisible(true);
    }}else {
            menuitem4.setVisible(false);
            menuItem2.setVisible(false);
            blab.setVisible(false);

            menuItem1.setVisible(false);
            menuItem3.setVisible(false);
        }

            if (id == R.id.ctrending) {
                menuhandler=0;

                menuFragment = new TrendingFragment();
             FragmentManager fragmentManager = getFragmentManager();
             fragmentManager.beginTransaction()
                     .replace(R.id.main_fragment, menuFragment)
                     .commit();



        } else if (id == R.id.cattendancemanager) {
            menuhandler=1;
             menuFragment = new AttendanceFragment();
             FragmentManager fragmentManager = getFragmentManager();
             fragmentManager.beginTransaction()
                     .replace(R.id.main_fragment, menuFragment)
                     .commit();




        } else if (id == R.id.cstudymaterial) {
                menuhandler=0;


                menuFragment=new StudyMaterial();
             FragmentManager fragmentManager = getFragmentManager();
             fragmentManager.beginTransaction()
                     .replace(R.id.main_fragment, menuFragment)
                     .commit();

        } else if (id == R.id.cmart) {
                menuhandler=0;

                menuFragment=new MartFragmentMain();
             FragmentManager fragmentManager = getFragmentManager();
             fragmentManager.beginTransaction()
                     .replace(R.id.main_fragment, menuFragment)
                     .commit();

        } else if (id == R.id.clostandfound) {
                menuhandler=0;

             menuFragment=new LostAndFound();
             FragmentManager fragmentManager = getFragmentManager();
             fragmentManager.beginTransaction()
                     .replace(R.id.main_fragment, menuFragment)
                     .commit();
        } /*else if (id == R.id.cwhattodo) {
                menuhandler=0;

             menuFragment=new WhatToDo();
             FragmentManager fragmentManager = getFragmentManager();
             fragmentManager.beginTransaction()
                     .replace(R.id.main_fragment, menuFragment)
                     .commit();


        }*/ else if (id == R.id.csuggestions) {
            /*
             menuFragment=new CollegeInfo();
             FragmentManager fragmentManager = getFragmentManager();
             fragmentManager.beginTransaction()
                     .replace(R.id.main_fragment, menuFragment)
                     .commit();*/
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","goldenboatdev@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Suggestions");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));

        }
            else if (id == R.id.ccollegeinfo) {
            Intent i=new Intent(HomePage.this,CollegeInfo.class);
            startActivity(i);


            }else if (id == R.id.cprofile) {
                menuhandler=0;


                menuFragment = new MyProfileFragment();
             FragmentManager fragmentManager = getFragmentManager();
             fragmentManager.beginTransaction()
                     .replace(R.id.main_fragment, menuFragment)
                     .commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

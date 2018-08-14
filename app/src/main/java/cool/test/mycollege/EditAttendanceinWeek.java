package cool.test.mycollege;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import cool.test.mycollege.Fragments.FridayFragment;
import cool.test.mycollege.Fragments.MondayFragment;
import cool.test.mycollege.Fragments.ThursdayFragment;
import cool.test.mycollege.Fragments.TuesdayFragment;
import cool.test.mycollege.Fragments.WednesdayFragment;

public class EditAttendanceinWeek extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    //ListView myListView = (ListView) findViewById(R.id.myList);
    private ViewPager mViewPager;

    @Override
    protected void onResume() {
        super.onResume();
        if (!(mSectionsPagerAdapter == null)) {
            mSectionsPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        Calendar calendar = Calendar.getInstance();
        setContentView(R.layout.activity_attendance);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);



        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        int day = calendar.get(Calendar.DAY_OF_WEEK);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_attendance, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {

            Intent attendanceIntent = new Intent(this,AttendanceSettingsActivity.class);
            startActivity(attendanceIntent);
            return true;
        }  else if (id == android.R.id.home) {
         //   NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }


    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
/*
@include<iostream>
using namespaces std;
void main()
{

 */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_attendance, container, false);



           /* TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));*/


            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {
        return POSITION_NONE;
        }



        @Override
        public Fragment getItem(int position) {

            if (position == 0)
                return new MondayFragment();
            else if (position == 1)
                return new TuesdayFragment();
            else if (position == 2)
                return new WednesdayFragment();
            else if (position == 3)
                return new ThursdayFragment();
            else if (position == 4)
                return new FridayFragment();

            else
                return null;

        }

        @Override
        public int getCount() {
            return 5; // No of days
        }
    }
}

package cool.test.mycollege;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;

import cool.test.mycollege.Fragments.TrendingFragment;
import cool.test.mycollege.Fragments.funsection;
import cool.test.mycollege.Fragments.uploadslostandfound;
import cool.test.mycollege.Fragments.uploadsmymart;
import cool.test.mycollege.Fragments.uploadsmyoldmart;
import de.greenrobot.event.EventBus;

public class Myuploads extends AppCompatActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==19){
            Log.e("F","Fucking Great dude 19");
            EventBus.getDefault().post(new ActivityResultEvent(requestCode, resultCode, data));
        }
        if (requestCode==1911){
            Log.e("F","Fucking Great dude 1911");
            EventBus.getDefault().post(new ActivityResultEvent(requestCode, resultCode, data));
        }
        if (requestCode==1912){
            Log.e("F","Fucking Great dude 1912");
            EventBus.getDefault().post(new ActivityResultEvent(requestCode, resultCode, data));
        }
    }
        public class ActivityResultEvent{
        int re,res;
        Intent dat;

            public ActivityResultEvent(int re, int res, Intent dat) {
                this.re = re;
                this.res = res;
                this.dat = dat;
            }

            public int getRe() {
                return re;
            }

            public int getRes() {
                return res;
            }

            public Intent getDat() {
                return dat;
            }
        }
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myuploads);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        //Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        ViewGroup.LayoutParams params = mViewPager.getLayoutParams();


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        AppBarLayout appb=(AppBarLayout)findViewById(R.id.appbar);
        int he=appb.getHeight();

        params.height = height-he;
        mViewPager.setLayoutParams(params);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }


    /**
     * A placeholder fragment containing a simple view.
     */
    /*
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
    /*
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
      /*  public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;





        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_myuploads, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }*/

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            ///vipin u r so cool
            if (position == 0) {

                return new uploadsmymart();
            }
            else if (position==1)
            {
             return  new uploadsmyoldmart();
            }
            else

               return new uploadslostandfound();

        }

        @Override
        public int getCount() {
            //Show 3 total pages.
            return 3;
        }
    }
}

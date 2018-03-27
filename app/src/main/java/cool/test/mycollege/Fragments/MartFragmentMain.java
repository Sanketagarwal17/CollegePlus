package cool.test.mycollege.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import cool.test.mycollege.R;

/**
 * Created by Vipin soni on 10-03-2018.
 */

public class MartFragmentMain extends Fragment {

    SectionsPagerAdapterp mSectionsPagerAdapter;
    ViewPager mViewPager;
    private FragmentActivity myContext;
    View v;

    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        Log.e("Martmain","oncreate");
         v = inflater.inflate(R.layout.fragment_mart_main, container, false);
        mSectionsPagerAdapter = new SectionsPagerAdapterp(myContext.getSupportFragmentManager());

        //Set up the ViewPager with the sections adapter.
        mViewPager = v.findViewById(R.id.martcontainer);
        ViewGroup.LayoutParams params = mViewPager.getLayoutParams();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        myContext.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        AppBarLayout appb = (AppBarLayout) v.findViewById(R.id.appbar);
        int he = appb.getHeight();

        params.height = height - he;
        mViewPager.setLayoutParams(params);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.marttabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        return v;

    }


    public static class PlaceholderFragment extends android.support.v4.app.Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static MartFragmentMain.PlaceholderFragment newInstance(int sectionNumber) {
            MartFragmentMain.PlaceholderFragment fragment = new MartFragmentMain.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;


        }}

        public class SectionsPagerAdapterp extends FragmentStatePagerAdapter {

            public SectionsPagerAdapterp(FragmentManager fm) {

                super(fm);
            }

            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                // getItem is called to instantiate the fragment for the given page.
                // Return a PlaceholderFragment (defined as a static inner class below).


                ///vipin u r so cool
                if (position == 0) {
                    return new Mart();
                }

                return new oldMart();


            }

            @Override
            public int getCount() {
                //Show 3 total pages.
                return 2;
            }
        }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}


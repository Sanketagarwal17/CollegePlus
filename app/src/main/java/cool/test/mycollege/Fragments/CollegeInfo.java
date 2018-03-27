package cool.test.mycollege.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cool.test.mycollege.R;

/**
 * Created by Vipin soni on 20-12-2017.
 */

public class CollegeInfo extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_college_info,container,false);





        return v;
    }
}

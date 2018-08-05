package cool.test.mycollege.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cool.test.mycollege.Adapter.clubrecycleradapter;
import cool.test.mycollege.MyDataTypes.club_datatype;
import cool.test.mycollege.R;

/**
 * Created by Vipin soni on 29-07-2018.
 */

public class clubs extends Fragment {
RecyclerView clubrecycler;
ArrayList<club_datatype> myclubdata;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.clubs,null);
        myclubdata=new ArrayList<>();
        myclubdata.add(new club_datatype("Bla Bla Club","https://www.facebook.com/micdroptoastmasters/","Its A awesome club",R.drawable.vipin));


        clubrecycler=v.findViewById(R.id.club_recycler);
        clubrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        clubrecycler.setHasFixedSize(true);
        clubrecycleradapter clubadapter=new clubrecycleradapter(myclubdata);
        clubrecycler.setAdapter(clubadapter);




        return v;

    }
}

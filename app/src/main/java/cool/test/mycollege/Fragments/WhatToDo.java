package cool.test.mycollege.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cool.test.mycollege.Adapter.WhatToDoRecycler;
import cool.test.mycollege.Adapter.clubrecycleradapter;
import cool.test.mycollege.MyDataTypes.whattododata;
import cool.test.mycollege.R;

/**
 * Created by Vipin soni on 20-12-2017.
 */

public class WhatToDo extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        ArrayList<whattododata> list=new ArrayList<>();

        list.add(new whattododata("Cool ",R.drawable.back_main));

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        View v=inflater.inflate(R.layout.fragment_what_to_do,container,false);
        RecyclerView recyclerView=v.findViewById(R.id.whattodoo);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        WhatToDoRecycler whatadapter=new WhatToDoRecycler(list);
        recyclerView.setAdapter(whatadapter);


        return v;

    }
}

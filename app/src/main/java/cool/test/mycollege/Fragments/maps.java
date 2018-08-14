package cool.test.mycollege.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Locale;

import cool.test.mycollege.Adapter.RecyclerAdapter;
import cool.test.mycollege.MyDataTypes.map_datatype;
import cool.test.mycollege.R;

/**
 * Created by Vipin soni on 29-07-2018.
 */

public class maps extends Fragment {



    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    ArrayList<map_datatype> mydata;
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.maps,null);
        mydata=new ArrayList<>();
        mydata.add(new map_datatype((float) 23.821021,(float) 86.469849,"Yellow Chilli","A Restaurant in Ozone Galleria mall\nIndian food with classic modern touch" ));
        mydata.add(new map_datatype((float) 23.825753,(float) 86.459378,"Lemon Chilli","Restaurant" ));
        mydata.add(new map_datatype((float) 23.811513,(float) 86.447705,"SBI (IIT ISM Branch)","Bank Stuff" ));
        mydata.add(new map_datatype((float) 23.825753,(float) 86.459378,"Hirapur","Your Local Market" ));
        mydata.add(new map_datatype((float) 23.824113,(float) 86.434377,"Rang De Basanti","Restaurant" ));
        mydata.add(new map_datatype((float) 23.809161,(float) 86.442898,"Cakes And Bakes","Snacks Time" ));
        mydata.add(new map_datatype((float) 23.809161,(float) 86.442898,"Shawarma Street","Restaurant" ));
        mydata.add(new map_datatype((float) 23.812098,(float) 86.457750,"Pragarti Medical","Hospital" ));
        mydata.add(new map_datatype((float) 23.819651,(float) 86.468345,"Jungle Walk","Restaurant" ));
        mydata.add(new map_datatype((float) 23.820806,(float) 86.469880,"Sonotel","A Nice Restaurant" ));
        mydata.add(new map_datatype((float) 0.0,(float) 0.0,"my_fucking_ad","love this ad" ));

        mydata.add(new map_datatype((float) 23.782580,(float) 86.415287,"Seventeen Degrees","A Awesome Restaurant" ));
        mydata.add(new map_datatype((float) 23.788060,(float) 86.418153,"Bank More","--Maybe can help you--" ));
        mydata.add(new map_datatype((float) 23.805714,(float) 86.450229,"Central Hospital","Dhanbad Local Hospital" ));
        mydata.add(new map_datatype((float) 23.811667,(float) 86.442060,"Post Office","Campus post office" ));
        mydata.add(new map_datatype((float) 23.816465,(float) 86.440082,"RD (ram dhani)","Your morning Breakfast place \n and evening snacks" ));
        mydata.add(new map_datatype((float) 23.795760,(float) 86.443832,"Foundry Shop (Local)","--Maybe can help you--" ));
        mydata.add(new map_datatype((float) 23.830554,(float) 86.415646,"Birsa Munda Park","A fun park\nbest place to be with your friends" ));
        mydata.add(new map_datatype((float) 23.811853,(float) 86.439012,"Health Center (IIT ISM)","Health center of campus" ));
        mydata.add(new map_datatype((float) 0.0,(float) 0.0,"my_fucking_ad","love this ad" ));

        mydata.add(new map_datatype((float) 23.811169,(float) 86.439926,"Temple","Campus peace place" ));
        mydata.add(new map_datatype((float) 23.798147,(float) 86.427662,"Cocoon","Restaurant" ));
        mydata.add(new map_datatype((float) 23.787901,(float) 86.417636,"Central point mall","Mall" ));
        mydata.add(new map_datatype((float) 23.820329,(float) 86.434770,"Dhaiya gate","--Maybe can help you--" ));
        mydata.add(new map_datatype((float) 23.819031,(float) 86.438634,"Sriyam","Restaurant" ));
        mydata.add(new map_datatype((float) 23.820823,(float) 86.469673,"Mall (Ozone Galleria Mall)","Mall" ));
        mydata.add(new map_datatype((float) 23.820823,(float) 86.469673,"Bombay Sweets","A Awesome Restaurant" ));
        mydata.add(new map_datatype((float) 23.801524,(float) 86.444858,"Khushboo Restaurant","A Awesome Restaurant" ));
        mydata.add(new map_datatype((float) 23.787901,(float) 86.417636,"London Street","Restaurant" ));
        mydata.add(new map_datatype((float) 0.0,(float) 0.0,"my_fucking_ad","love this ad" ));

        mydata.add(new map_datatype((float) 23.813110,(float) 86.460638,"Black Berry","Restaurant" ));
        mydata.add(new map_datatype((float) 23.788668,(float) 86.420308,"Bar Be Que","Restaurant" ));
        mydata.add(new map_datatype((float) 23.811392,(float) 86.454369,"Mani's Cafe","Restaurant" ));
        mydata.add(new map_datatype((float) 23.810532,(float) 86.443426,"One.O","Saddacampus awesome Restaurant" ));
        mydata.add(new map_datatype((float) 23.811474,(float) 86.444375,"Shahi Darbar","Restaurant" ));
        mydata.add(new map_datatype((float) 23.793650,(float) 86.427330,"Shere Punjab","Restaurant" ));
















        EditText aaa=v.findViewById(R.id.search_it);
        aaa.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterit(s.toString());

            }
        });
        recyclerView=v.findViewById(R.id.recyclercampus);
        recyclerAdapter=new RecyclerAdapter(mydata);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(recyclerAdapter);


        return v;
    }

public void filterit(String local2){
        String s2;
        ArrayList<map_datatype> fine=new ArrayList<>();
    Log.e("ave hi", "filterit: ");
            for (int x=0;x<mydata.size();x++) {
                s2=mydata.get(x).getPlace_name();
                if (s2.toLowerCase().contains(local2.toLowerCase())) {
                    fine.add(mydata.get(x));
                }
            }
            if (local2.equals(""))
            {
                fine=mydata;
            }

    //calling a method of the adapter class and passing the filtered list
    recyclerAdapter.filterit(fine);


}



}

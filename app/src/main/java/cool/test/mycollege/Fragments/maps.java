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
        mydata.add(new map_datatype((float) 23.813978,(float) 86.441187,"Test","a sweet place"));
        mydata.add(new map_datatype((float) 23.813978,(float) 86.441187,"vipin","a sweet place"));
        mydata.add(new map_datatype((float) 23.813978,(float) 86.441187,"bla","a sweet place"));
        mydata.add(new map_datatype((float) 23.813978,(float) 86.441187,"vipmmd","a sweet place"));
        mydata.add(new map_datatype((float) 23.813978,(float) 86.441187,"sm","a sweet place"));
        mydata.add(new map_datatype((float) 23.813978,(float) 86.441187,"sdlk","a sweet place"));
        mydata.add(new map_datatype((float) 23.813978,(float) 86.441187,"sdkm","a sweet place"));
        mydata.add(new map_datatype((float) 23.813978,(float) 86.441187,"kjfsd","a sweet place"));
        mydata.add(new map_datatype((float) 23.813978,(float) 86.441187,"sdkm","a sweet place"));
        mydata.add(new map_datatype((float) 23.813978,(float) 86.441187,"dslkm","a sweet place"));
        mydata.add(new map_datatype((float) 23.813978,(float) 86.441187,"sdoivm","a sweet place"));
        mydata.add(new map_datatype((float) 23.813978,(float) 86.441187,"sdfoivmoi","a sweet place"));
        mydata.add(new map_datatype((float) 23.813978,(float) 86.441187,"dsoinc","a sweet place"));
        mydata.add(new map_datatype((float) 23.813978,(float) 86.441187,"iusdc","a sweet place"));

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

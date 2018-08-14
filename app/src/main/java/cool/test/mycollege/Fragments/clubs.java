package cool.test.mycollege.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import cool.test.mycollege.Adapter.clubrecycleradapter;
import cool.test.mycollege.MyDataTypes.club_datatype;
import cool.test.mycollege.MyDataTypes.map_datatype;
import cool.test.mycollege.R;

/**
 * Created by Vipin soni on 29-07-2018.
 */

public class clubs extends Fragment {
RecyclerView clubrecycler;
clubrecycleradapter clubadapter;
ArrayList<club_datatype> myclubdata;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.clubs,null);
        myclubdata=new ArrayList<>();
        myclubdata.add(new club_datatype("Mic Drop Toastmasters","https://www.facebook.com/micdroptoastmasters/",R.string.toastmaster,R.drawable.mic));
        myclubdata.add(new club_datatype("FotoFreaks","https://www.facebook.com/fotofreaks.iit.ism/",R.string.fotofreaks,R.drawable.fotofreaks));
        myclubdata.add(new club_datatype("Chayanika Sangh","https://www.facebook.com/chayanikaIITISMdhanbad/",R.string.chayanika,R.drawable.litc));
        myclubdata.add(new club_datatype("LITM (Legends In The Making)","https://www.facebook.com/LITM.ISM/",R.string.litm,R.drawable.litm));
        myclubdata.add(new club_datatype("WTC (We The Crew)","https://www.facebook.com/wethecrew/",R.string.wethecrew,R.drawable.wtc));
        myclubdata.add(new club_datatype("LITC","https://www.facebook.com/litcism/",R.string.litc,R.drawable.litcc));
        myclubdata.add(new club_datatype("AIESEC","https://www.facebook.com/aiesecindia/",R.string.aisec,R.drawable.aisec));
        myclubdata.add(new club_datatype("LCI (Lights Camera ISM) ","https://www.facebook.com/14ryproductions/",R.string.lci,R.drawable.lci));
        myclubdata.add(new club_datatype("FFI (Fast Forward India)","https://www.facebook.com/FastForwardIndia/",R.string.ffi,R.drawable.ffi));
        myclubdata.add(new club_datatype("Udaan","https://www.facebook.com/udaan.ism/",R.string.udaan,R.drawable.uddan));
        myclubdata.add(new club_datatype("Mechismu","https://www.facebook.com/mechismu/",R.string.mechismu,R.drawable.mech));
        myclubdata.add(new club_datatype("Manthan","https://www.facebook.com/manthan.iitism/",R.string.manthan,R.drawable.manthan));
        myclubdata.add(new club_datatype("MD (Mailer Demon)","https://www.facebook.com/MDiitism/",R.string.mailer,R.drawable.mailer));
        myclubdata.add(new club_datatype("Art Freaks","https://www.facebook.com/artfreaks.ism/",R.string.artfreaks,R.drawable.artfreaks));
        myclubdata.add(new club_datatype("ADC (Abhay Dramatic Club)","https://www.facebook.com/abhaydramaticsclub/",R.string.adc,R.drawable.abhay));
        myclubdata.add(new club_datatype("Cyber Labs","https://www.facebook.com/labscyber/",R.string.cyberlab,R.drawable.cyber));
        myclubdata.add(new club_datatype("Robo ISM","https://www.facebook.com/roboism/",R.string.roboism,R.drawable.robo));
        myclubdata.add(new club_datatype("E Cell","https://www.facebook.com/genesisecell/",R.string.ecell,R.drawable.ecell));
        myclubdata.add(new club_datatype("Quiziapa","https://www.facebook.com/groups/quiziapaclub.ism/about/",R.string.quiziappa,R.drawable.quiz));
        myclubdata.add(new club_datatype("Astronomy Club (ARKA)","https://www.facebook.com/ARKAiitism/",R.string.arka,R.drawable.astronomy));
        myclubdata.add(new club_datatype("Samitra","https://www.facebook.com/samitraism/",R.string.samitra,R.drawable.samitra));












        EditText aaa=v.findViewById(R.id.search_itt);
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









        clubrecycler=v.findViewById(R.id.club_recycler);
        clubrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        clubrecycler.setHasFixedSize(true);
        clubadapter=new clubrecycleradapter(myclubdata);
        clubrecycler.setAdapter(clubadapter);




        return v;

    }




    public void filterit(String local2){
        String s2;
        ArrayList<club_datatype> fine=new ArrayList<>();
        Log.e("ave hi", "filterit: ");
        for (int x=0;x<myclubdata.size();x++) {
            s2=myclubdata.get(x).getClub_name();
            if (s2.toLowerCase().contains(local2.toLowerCase())) {
                fine.add(myclubdata.get(x));
            }
        }
        if (local2.equals(""))
        {
            fine=myclubdata;
        }

        clubadapter.filterit(fine);


    }













}

package cool.test.mycollege.Helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import cool.test.mycollege.MyDataTypes.SubjectInfo;
import cool.test.mycollege.R;

/**
 * Created by Augustine on 3/11/2018.
 */

public class AugustineAdapter extends RecyclerView.Adapter<AugustineAdapter.MyfuckingHolder> {

    ArrayList<SubjectInfo> someDamnList = new ArrayList<>();
    Context theContext;
    private LayoutInflater myInflater;


    public AugustineAdapter(Context context, ArrayList<SubjectInfo> data) {
        myInflater = LayoutInflater.from(context);
        someDamnList = data;
        theContext = context;


    }


   /* public void mydelete(int position) {
        someDamnList.remove(position);
        notifyItemRemoved(position);
    }*/

    @Override
    public MyfuckingHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType==1||viewType==2)
        {
            View blabla=myInflater.inflate(R.layout.fancy_text,parent,false);
            MyfuckingHolder holder = new MyfuckingHolder(blabla);
            return holder;


        }
        else if (viewType==3){
            View blabla=myInflater.inflate(R.layout.card_ad_view,parent,false);
            MyfuckingHolder holder = new MyfuckingHolder(blabla);
            return holder;
        }
        View row_view = myInflater.inflate(R.layout.card_subj, parent, false);
        MyfuckingHolder holder = new MyfuckingHolder(row_view);


        return holder;
    }

    @Override
    public void onBindViewHolder(final MyfuckingHolder holder, int position) {

        if (holder.getItemViewType()==1){
            holder.vvv.setText("Today's Subject");
            return;

        }

        if (holder.getItemViewType()==2){
            holder.vvv.setText("All Subjects");
            return;

        }

        if (holder.getItemViewType()==3){

            AdRequest adRequest = new AdRequest.Builder().build();
            holder.adView.loadAd(adRequest);
            return;
        }

        Double currentPercent;

        final SubjectInfo current = someDamnList.get(position);
        holder.title.setText(current._subjectname);
        // holder.desc.setText(current._subjectname);

        final DBTools theDB = new DBTools(theContext);


        String theSubjectname = holder.title.getText().toString();
        Log.e("DemErrors", theSubjectname);
        int currentAttendance = theDB.getTheDamnAttendance(theSubjectname);
        Integer currentBunk = theDB.getTheDamnBunk(theSubjectname);
        if ((currentAttendance + currentBunk) != 0) {
            currentPercent = Double.valueOf((currentAttendance * 1000) / (currentAttendance + currentBunk));
        } else
            currentPercent = 0.0;


        String theAttendance = Integer.toString(currentAttendance);
        String theBunk = Integer.toString(currentBunk);
        String thePercent = Double.toString(currentPercent);
        Log.e("DemErrors", theSubjectname + " " + theAttendance + " " + theBunk + " " + thePercent);
        holder.attendNumber.setText(theAttendance);
        holder.bunkNumber.setText(theBunk);
        holder.percentNumber.setText(String.valueOf(thePercent.charAt(0))+String.valueOf(thePercent.charAt(1))+"."+String.valueOf(thePercent.charAt(2))+ "%");
        currentPercent=currentPercent/10;
        if (currentPercent==100)
        {
            holder.percentNumber.setText("100%");
        }
        if (currentPercent<10)
        {
            holder.percentNumber.setText(currentPercent.toString()+"%");
        }
        holder.myBar.setProgress(currentPercent.intValue());


        holder.attendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                // attend Button action
// Attend Button has been clicked
                String theSubjectname = holder.title.getText().toString();
                Log.e("DemErrors", theSubjectname);
                int currentAttendance = theDB.getTheDamnAttendance(theSubjectname) + 1;
                Integer currentBunk = theDB.getTheDamnBunk(theSubjectname);
                Double currentPercent;
                if ((currentAttendance + currentBunk) != 0) {
                    currentPercent = Double.valueOf((currentAttendance * 1000) / (currentAttendance + currentBunk));
                } else
                    currentPercent = 0.0;


                String theAttendance = Integer.toString(currentAttendance);
                String theBunk = Integer.toString(currentBunk);
                String thePercent = Double.toString(currentPercent);
                Log.e("DemErrors", theSubjectname + " " + theAttendance + " " + theBunk + " " + thePercent);
                holder.attendNumber.setText(theAttendance);
                holder.bunkNumber.setText(theBunk);
                holder.percentNumber.setText(String.valueOf(thePercent.charAt(0))+String.valueOf(thePercent.charAt(1))+"."+String.valueOf(thePercent.charAt(2))+ "%");
                currentPercent=currentPercent/10;
                if (currentPercent==100)
                    holder.percentNumber.setText("100.0%");
                else if (currentPercent<10)
                {
                        holder.percentNumber.setText(currentPercent.toString()+"%");
                }
                holder.myBar.setProgress(currentPercent.intValue());


                theDB.updateDB(theSubjectname, theAttendance, theBunk, thePercent);
                notifyDataSetChanged();

            }
        });
        holder.bunkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // bunk button action
                String theSubjectname = holder.title.getText().toString();
                Integer currentAttendance = theDB.getTheDamnAttendance(theSubjectname);
                Integer currentBunk = theDB.getTheDamnBunk(theSubjectname) + 1;
                Double currentPercent;
                if ((currentAttendance + currentBunk) != 0) {
                    currentPercent = Double.valueOf((currentAttendance * 1000) / (currentAttendance + currentBunk));
                } else
                    currentPercent = 0.0;

                String theAttendance = Integer.toString(currentAttendance);
                String theBunk = Integer.toString(currentBunk);
                String thePercent = Double.toString(currentPercent);
                holder.attendNumber.setText(theAttendance);
                holder.bunkNumber.setText(theBunk);
                Log.e("vvv",currentPercent.toString());
                Log.e("g",thePercent);
                holder.percentNumber.setText(String.valueOf(thePercent.charAt(0))+String.valueOf(thePercent.charAt(1))+"."+String.valueOf(thePercent.charAt(2))+ "%");
                currentPercent=currentPercent/10;
                if (currentPercent<10)
                {
                    holder.percentNumber.setText(currentPercent.toString()+"%");
                }
                holder.myBar.setProgress(currentPercent.intValue());


                theDB.updateDB(theSubjectname, theAttendance, theBunk, thePercent);
                notifyDataSetChanged();


            }
        });


        if (someDamnList.get(position).get_percent()==1011.1){
            holder.title.setText("Today's TimeTable");
            holder.attendNumber.setVisibility(View.INVISIBLE);
            holder.bunkNumber.setVisibility(View.INVISIBLE);
            holder.percentNumber.setVisibility(View.INVISIBLE);
            holder.myBar.setVisibility(View.INVISIBLE);
            holder.bunkButton.setVisibility(View.INVISIBLE);
            holder.attendButton.setVisibility(View.INVISIBLE);

        }

        if (someDamnList.get(position).get_percent()==1011.2){
            holder.title.setText("All Subjects");
            holder.attendNumber.setVisibility(View.INVISIBLE);
            holder.bunkNumber.setVisibility(View.INVISIBLE);
            holder.percentNumber.setVisibility(View.INVISIBLE);
            holder.myBar.setVisibility(View.INVISIBLE);
            holder.bunkButton.setVisibility(View.INVISIBLE);
            holder.attendButton.setVisibility(View.INVISIBLE);

        }


    }

    @Override
    public int getItemCount() {
        return someDamnList.size();


    }

    @Override
    public int getItemViewType(int position) {
        if (someDamnList.get(position).get_percent()==1011.1)
            return 1;
        else if (someDamnList.get(position).get_percent()==1011.2)
            return 2;
        else if (someDamnList.get(position).get_percent()==1011.3)
            return 3;
        return 0;
    }

    public class MyfuckingHolder extends RecyclerView.ViewHolder  {

        TextView vvv;
        TextView title;
        TextView attendNumber;
        TextView bunkNumber;
        TextView percentNumber;
        //TextView desc;
        Button cancelButton;
        Button attendButton;
        Button bunkButton;
        ProgressBar myBar;
        AdView adView;


        public MyfuckingHolder(View itemView) {
            super(itemView);
            adView=itemView.findViewById(R.id.adView);
            title = (TextView) itemView.findViewById(R.id.subjectTitle);
            attendNumber = (TextView) itemView.findViewById(R.id.attendNumber);
            bunkNumber = (TextView) itemView.findViewById(R.id.bunknumber);
            percentNumber = (TextView) itemView.findViewById(R.id.percentNumber);

            //desc=(TextView)itemView.findViewById(R.id.row_desc);
            vvv=itemView.findViewById(R.id.seperator_text);
            attendButton = (Button) itemView.findViewById(R.id.attendButton);
            bunkButton = (Button) itemView.findViewById(R.id.bunkButton);
            /*cancelButton = (Button) itemView.findViewById(R.id.cancelButton);
            cancelButton.setOnClickListener(this);*/

            myBar=(ProgressBar)itemView.findViewById(R.id.determinateBar);


        }


/*
        @Override
        public void onClick(View v) {
            mydelete(getLayoutPosition());

        }*/
    }


    public  class vipins_infoholder extends RecyclerView.ViewHolder{
        TextView k;

        public vipins_infoholder(View itemView) {
            super(itemView);

            k=itemView.findViewById(R.id.seperator_text);

        }
    }
}

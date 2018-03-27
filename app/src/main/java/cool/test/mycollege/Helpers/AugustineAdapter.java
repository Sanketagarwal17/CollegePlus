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
        View row_view = myInflater.inflate(R.layout.card_subj, parent, false);
        MyfuckingHolder holder = new MyfuckingHolder(row_view);


        return holder;
    }

    @Override
    public void onBindViewHolder(final MyfuckingHolder holder, int position) {

        SubjectInfo current = someDamnList.get(position);
        holder.title.setText(current._subjectname);
        // holder.desc.setText(current._subjectname);

        final DBTools theDB = new DBTools(theContext);


        String theSubjectname = holder.title.getText().toString();
        Log.e("DemErrors", theSubjectname);
        int currentAttendance = theDB.getTheDamnAttendance(theSubjectname);
        Integer currentBunk = theDB.getTheDamnBunk(theSubjectname);
        Double currentPercent;
        if ((currentAttendance + currentBunk) != 0) {
            currentPercent = Double.valueOf((currentAttendance * 100) / (currentAttendance + currentBunk));
        } else
            currentPercent = 0.0;


        String theAttendance = Integer.toString(currentAttendance);
        String theBunk = Integer.toString(currentBunk);
        String thePercent = Double.toString(currentPercent);
        Log.e("DemErrors", theSubjectname + " " + theAttendance + " " + theBunk + " " + thePercent);
        holder.attendNumber.setText(theAttendance);
        holder.bunkNumber.setText(theBunk);
        holder.percentNumber.setText(thePercent + "%");
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
                    currentPercent = Double.valueOf((currentAttendance * 100) / (currentAttendance + currentBunk));
                } else
                    currentPercent = 0.0;


                String theAttendance = Integer.toString(currentAttendance);
                String theBunk = Integer.toString(currentBunk);
                String thePercent = Double.toString(currentPercent);
                Log.e("DemErrors", theSubjectname + " " + theAttendance + " " + theBunk + " " + thePercent);
                holder.attendNumber.setText(theAttendance);
                holder.bunkNumber.setText(theBunk);
                holder.percentNumber.setText(thePercent + "%");
                holder.myBar.setProgress(currentPercent.intValue());


                theDB.updateDB(theSubjectname, theAttendance, theBunk, thePercent);

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
                    currentPercent = Double.valueOf((currentAttendance * 100) / (currentAttendance + currentBunk));
                } else
                    currentPercent = 0.0;

                String theAttendance = Integer.toString(currentAttendance);
                String theBunk = Integer.toString(currentBunk);
                String thePercent = Double.toString(currentPercent);
                holder.attendNumber.setText(theAttendance);
                holder.bunkNumber.setText(theBunk);
                holder.percentNumber.setText(thePercent + "%");
                holder.myBar.setProgress(currentPercent.intValue());


                theDB.updateDB(theSubjectname, theAttendance, theBunk, thePercent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return someDamnList.size();


    }

    public class MyfuckingHolder extends RecyclerView.ViewHolder  {

        TextView title;
        TextView attendNumber;
        TextView bunkNumber;
        TextView percentNumber;
        //TextView desc;
        Button cancelButton;
        Button attendButton;
        Button bunkButton;
        ProgressBar myBar;


        public MyfuckingHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.subjectTitle);
            attendNumber = (TextView) itemView.findViewById(R.id.attendNumber);
            bunkNumber = (TextView) itemView.findViewById(R.id.bunknumber);
            percentNumber = (TextView) itemView.findViewById(R.id.percentNumber);

            //desc=(TextView)itemView.findViewById(R.id.row_desc);

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
}

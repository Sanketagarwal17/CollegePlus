package cool.test.mycollege.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.zip.Inflater;

import cool.test.mycollege.MyDataTypes.whattododata;
import cool.test.mycollege.R;

import static cool.test.mycollege.Adapter.RecyclerAdapter.*;

/**
 * Created by Vipin soni on 10-08-2018.
 */

public class WhatToDoRecycler extends RecyclerView.Adapter<WhatToDoRecycler.whyfuck> {

    ArrayList<whattododata> local;
    View lp;


    @Override
    public void onBindViewHolder(@NonNull whyfuck holder, int position) {
        Glide.with(lp.getContext()).load(local.get(position).getImage()).into(holder.i);
        holder.t.setText(local.get(position).getWhat());


    }

    @NonNull
    @Override
    public whyfuck onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_whattodo,parent,false);
        lp=v;
        return new whyfuck(v);
    }

    @Override
    public int getItemCount() {
        return local.size();
    }

    public WhatToDoRecycler(ArrayList<whattododata> yoocool) {
        local=yoocool;

    }

    public class whyfuck extends RecyclerView.ViewHolder{
        ImageView i;
        TextView t;
        public whyfuck(View itemView) {
            super(itemView);

            i=itemView.findViewById(R.id.whattodoposter);
            t=itemView.findViewById(R.id.whattodotext);

        }
    }
}

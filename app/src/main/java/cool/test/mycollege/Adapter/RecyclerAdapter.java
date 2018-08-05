package cool.test.mycollege.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import cool.test.mycollege.MyDataTypes.map_datatype;
import cool.test.mycollege.R;

/**
 * Created by Vipin soni on 30-07-2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    ArrayList<map_datatype> local;
    ViewGroup localparent;
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView t,t2;
        ImageView imageView;
        RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            t=itemView.findViewById(R.id.map_text);
            t2=itemView.findViewById(R.id.map_desc);
            imageView=itemView.findViewById(R.id.map_open);
            relativeLayout=itemView.findViewById(R.id.rel_back_map);
        }

    }

    public RecyclerAdapter(ArrayList<map_datatype> aa) {
        local=aa;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_maps,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        localparent=parent;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.t.setText(local.get(position).getPlace_name());
        holder.t2.setText(local.get(position).getShort_desc());
        Random rand=new Random();
        int x=rand.nextInt(899999)+100000;
        int randomcolor = Color.parseColor("#24"+String.valueOf(x));

        holder.relativeLayout.setBackgroundColor(randomcolor);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotomap(local.get(position).getLat(),local.get(position).getLon());
            }
        });

    }
    public void updateList(ArrayList<map_datatype> list){
        local = list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return local.size();
    }


    public void gotomap(float lat,float lon){
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f?q=%f,%f?z=17", lat, lon,lat,lon);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        localparent.getContext().startActivity(intent);


    }

    public void filterit(ArrayList<map_datatype> search_data){

        local =search_data;
        notifyDataSetChanged();


    }

}

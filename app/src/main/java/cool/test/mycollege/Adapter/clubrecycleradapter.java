package cool.test.mycollege.Adapter;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import cool.test.mycollege.MyDataTypes.club_datatype;
import cool.test.mycollege.MyDataTypes.map_datatype;
import cool.test.mycollege.R;

/**
 * Created by Vipin soni on 30-07-2018.
 */

public class clubrecycleradapter extends RecyclerView.Adapter<clubrecycleradapter.ViewHolder> {


    ArrayList<club_datatype> local;
    ViewGroup localparent;
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView t;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            t=itemView.findViewById(R.id.club_name);
            imageView=itemView.findViewById(R.id.club_poster);

        }

    }

    public clubrecycleradapter(ArrayList<club_datatype> aa) {
        local=aa;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_clubs,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        localparent=parent;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.t.setText(local.get(position).getClub_name());
        //holder.imageView.setImageResource(local.get(position).getImage_id());
        Glide.with(localparent).load(local.get(position).getImage_id()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            ShowTheFuckingDialog(position);

            }
        });

    }

    private void ShowTheFuckingDialog(int p) {
        final int vv=p;
        final Dialog fullscreenDialog = new Dialog(localparent.getContext(), R.style.DialogFullscreen);
        fullscreenDialog.setContentView(R.layout.club_dialog);
        TextView tv=fullscreenDialog.findViewById(R.id.shortdesc);
        TextView tv2=fullscreenDialog.findViewById(R.id.top_text);
        tv2.setText(local.get(p).getClub_name());
        Button link=fullscreenDialog.findViewById(R.id.link_button);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(local.get(vv).getLink()));
                    localparent.getContext().startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(localparent.getContext().getApplicationContext(), "URL Fault ", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        tv.setText(local.get(p).getDesc());
        ImageView img_full_screen_dialog = fullscreenDialog.findViewById(R.id.club_image_dialog);
        Glide.with(localparent.getContext()).load(local.get(p).getImage_id()).into(img_full_screen_dialog);
        ImageView img_dialog_fullscreen_close = fullscreenDialog.findViewById(R.id.close_it);
        img_dialog_fullscreen_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullscreenDialog.dismiss();
            }
        });
        fullscreenDialog.show();
    }


    @Override
    public int getItemCount() {
        return local.size();
    }

    public void filterit(ArrayList<club_datatype> search_data){

        local =search_data;
        notifyDataSetChanged();


    }


}

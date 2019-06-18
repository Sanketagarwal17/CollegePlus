package cool.test.mycollege.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import cool.test.mycollege.R;
import cool.test.mycollege.Trending.TrendingActivity;

/**
 * Created by Vipin soni on 20-12-2017.
 */

public class TrendingFragment extends Fragment {
    FirebaseDatabase database;
    FloatingActionButton martadd;

    RecyclerView r;
    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.trendingfragment,container,false);
        r=(RecyclerView)v.findViewById(R.id.recyclerViewTrending);
        r.setLayoutManager(new LinearLayoutManager(getContext()));
        r.setHasFixedSize(true);
        progressBar=v.findViewById(R.id.progr);

        martadd = v.findViewById(R.id.martadd1);



        martadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // SharedPreferences sharedPreferences=v.getContext().getSharedPreferences("logindata",Context.MODE_PRIVATE);
                if(FirebaseAuth.getInstance().getCurrentUser()!= null)
                {
                    startActivity(new Intent(getActivity(),TrendingActivity.class));

                }

                else Toast.makeText(v.getContext(),"Login First",Toast.LENGTH_SHORT).show();

            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        database = FirebaseDatabase.getInstance();
        DatabaseReference hell = database.getReference("Trending");
        Query query = hell.orderByChild("PVN");//.startAt(loadPreferences()).endAt(loadPreferences());

        FirebaseRecyclerAdapter<productData, viewHolder> firebaseRecyclerAdapter = new
                FirebaseRecyclerAdapter<productData, viewHolder>(productData.class, R.layout.card_view_trending, viewHolder.class, hell) {

                    @Override
                    protected void populateViewHolder(viewHolder viewholder, productData model, int position) {

                        progressBar.setVisibility(View.INVISIBLE);

                        viewholder.geta(getActivity());
                        viewholder.givemodel(model);
                        viewholder.setTitle(model.getProductname());

                        // viewholder.setPrice(model.getPrice());
                        // viewholder.setDesc(model.getProductdesc());
                       viewholder.setlistener(model.getPicurl().equals("NULL"));
                        if (model.getPicurl().equals("NULL")) {
                            viewholder.setImage2();
                        } else {
                            viewholder.setImage(model.getPicurl());
                        }

                    }


                };
        r.setAdapter(firebaseRecyclerAdapter);

 /*   if (!isInternetConnection()){
        Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
    }*/
    }
    public  boolean isInternetConnection()
    {

        ConnectivityManager connectivityManager =  (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
    public static class viewHolder extends RecyclerView.ViewHolder {
        View productview;
        Activity a;
        productData data;


        public void geta(Activity b) {
            a = b;
        }

        public void givemodel(productData d) {
            data = d;
        }

        public viewHolder(final View itemView) {

            super(itemView);
            productview = itemView;



        }

        private void ShowMoreInfo() {
            {

                TextView v, p, i, n;
                ImageView im;
                Uri bla;


                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(a);
                LayoutInflater inflater = a.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.open_trending_feed, null);
                dialogBuilder.setView(dialogView);
                if (!data.getContactno().equals("NULL"))
                {     dialogBuilder.setPositiveButton("Link", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.getContactno()));
                                a.startActivity(myIntent);
                            } catch (ActivityNotFoundException e) {
                                Toast.makeText(a.getApplicationContext(), "URL Fault ", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    });
            }

            dialogBuilder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
                p = dialogView.findViewById(R.id.productnameit);
                p.setText(data.getProductname());
                i = dialogView.findViewById(R.id.producttdt);
                i.setText(data.getProductdesc());

                AlertDialog b = dialogBuilder.create();

                b.show();



            }
        }

        public void loadit(final ImageView iiii, Uri imagelink, Context c) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();

// Create a reference with an initial file path and name
           StorageReference pathReference = storageRef.child("Trending");
            //Picasso.with(c).load(imagelink).into(iiii);
            Glide
                    .with(c)
                    .load(imagelink)
                    .into(iiii);
        }

        public void setTitle(String stitle) {
            TextView a = productview.findViewById(R.id.producttittle);
            a.setText(stitle);


        }

        public void setImage(String stitle) {
            ImageView imageView = productview.findViewById(R.id.productimage);
            Uri uri = Uri.parse(stitle);
            imageView.setImageResource(R.drawable.noimage);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowMoreInfo();

                }
            });


            //  Picasso.with(productview.getContext()).load(uri).into(imageView);
            Glide
                    .with(productview.getContext())
                    .load(uri)
                    .into(imageView);

        }

        public void setImage2() {
            ImageView imageView = productview.findViewById(R.id.productimage);
            imageView.setImageResource(R.drawable.noimage);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowMoreInfo();

                }
            });


        }

        public void setlistener(boolean bb) {

            ImageView b = productview.findViewById(R.id.link);
if (bb=true){
    b.setVisibility(View.INVISIBLE);
}

        }




    }
    public static class productData {
        String productname, productdesc, contactno, price, producttype, email, picurl;
        long pvn;

        public productData() {
        }

        public String getPicurl() {
            return picurl;
        }

        public long getPVN() {
            return pvn;
        }

        public String getProductname() {
            return productname;
        }

        public String getProductdesc() {
            return productdesc;
        }

        public String getContactno() {
            return contactno;
        }

        public String getPrice() {
            return price;
        }

        public String getProducttype() {
            return producttype;
        }

        public String getEmail() {
            return email;
        }
    }
}

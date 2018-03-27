package cool.test.mycollege.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import cool.test.mycollege.Myuploads;
import cool.test.mycollege.R;
import de.greenrobot.event.EventBus;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Vipin soni on 08-03-2018.
 */

public class uploadsmyoldmart extends Fragment{

    String UID;
    static FirebaseDatabase database;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<productDatayoo, viewHolder> firebaseRecyclerAdapter;
    Context ctx;
    static int isimage=0;
    String yoo;
    Uri uri;
    static Bitmap bitmap;
    static ImageView imageView;
    ProgressBar vbg;

    public void onEvent(Myuploads.ActivityResultEvent event) {

        if(event.getRe()==1911&&event.getRes()==RESULT_OK)
        {
            Log.e("t","uploads Old mart");

            isimage = 1;

            try {

                uri = event.getDat().getData();
                ContentResolver resolver = getActivity().getContentResolver();
                bitmap = MediaStore.Images.Media.getBitmap(resolver, uri);

                imageView.setImageBitmap(bitmap);
                isimage=1;
                //upload();
            } catch (IOException e) {

                e.printStackTrace();
            }

        }




    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1911){

        }
        if (requestCode==1911&&resultCode==RESULT_OK){
            isimage = 1;

            try {

                uri = data.getData();
                ContentResolver resolver = getActivity().getContentResolver();
                bitmap = MediaStore.Images.Media.getBitmap(resolver, uri);

                imageView.setImageBitmap(bitmap);
                isimage=1;
                //upload();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        Log.e("t","On craere uploadmy old mart");

        View v = inflater.inflate(R.layout.uploads_my_old_mart, container, false);
      //  EventBus.getDefault().register(this);
        vbg=v.findViewById(R.id.vbg);
        SharedPreferences prefs = v.getContext().getSharedPreferences("logindata", Context.MODE_PRIVATE);
        UID = prefs.getString("UID", "NULL");
        recyclerView = v.findViewById(R.id.uploadsrecyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        ctx=v.getContext();

        return v;
    }

    @Override
    public void onStop() {
       // EventBus.getDefault().unregister(this);

        super.onStop();
    }
    @Override
    public void onDestroyView() {

        super.onDestroyView();

    }

    @Override
    public void onStart() {

        super.onStart();
        Log.e("t","On start uploadmy old mart");

        SharedPreferences prefs = getContext().getSharedPreferences("logindata", Context.MODE_PRIVATE);
        UID = prefs.getString("UID", "NULL");
        String em=prefs.getString("ID","NULL");
       // EventBus.getDefault().unregister(uploadsmymart.class);
        //EventBus.getDefault().unregister(uploadslostandfound.class);
        Log.e("t",String.valueOf( EventBus.getDefault().isRegistered(this)));

        database = FirebaseDatabase.getInstance();
        DatabaseReference hell = database.getReference("OldMart");
        Query query = hell.orderByChild("email").equalTo(em);//.startAt(loadPreferences()).endAt(loadPreferences());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (firebaseRecyclerAdapter.getItemCount()==0){
                    Toast.makeText(getContext(),"No items Uploaded in your old mart",Toast.LENGTH_SHORT).show();
                    vbg.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        firebaseRecyclerAdapter = new
                FirebaseRecyclerAdapter<productDatayoo, viewHolder>(productDatayoo.class, R.layout.card_mymart, viewHolder.class, query) {
             //

                    @Override
                    protected void populateViewHolder(viewHolder viewholder, productDatayoo model, int position) {
                            vbg.setVisibility(View.INVISIBLE);
                            viewholder.giveid(firebaseRecyclerAdapter.getRef(position).getKey());
                            viewholder.geta(getActivity());
                        viewholder.givemodel(model);
                        viewholder.setTitle(model.getProductname());
                        viewholder.setPrice(model.getPrice());
                        viewholder.setDesc(model.getProductdesc());
                        viewholder.setlistener();
                        if (model.getPicurl().equals("NULL")) {
                            viewholder.setImage2();
                        }
                        else {
                            viewholder.setImage(model.getPicurl());
                        }

                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }



    public static class viewHolder extends RecyclerView.ViewHolder {
        View productview;
        Activity a;
        productDatayoo data;
        String id;

        public void giveid(String iidd)
        {
            id=iidd;
        }

        public void geta(Activity b) {
            a = b;
        }

        public void givemodel(productDatayoo d) {
            data = d;
        }

        public viewHolder(final View itemView) {

            super(itemView);
            productview = itemView;


        }

        private void upload(final String noo) {


            final ProgressDialog progressDialog = new ProgressDialog(a);
            progressDialog.setTitle("Image Updating...");
            progressDialog.show();
            SharedPreferences preferences= a.getSharedPreferences("logindata", Context.MODE_PRIVATE);
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            String  UID = preferences.getString("UID", "Random");
            String no=noo.substring(28,31);
            StorageReference  ref = storageReference.child("OldMart/" + UID +"/"+ no + ".jpg");



            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            //Bitmap bitmap = imageView.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 15, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = ref.putBytes(data);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    progressDialog.dismiss();

                    Toast.makeText(a.getBaseContext(), "Image upload Failed !", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Toast.makeText(a.getBaseContext(), "Image Updated", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();


                    DatabaseReference livemart;
                    SharedPreferences prefs = a.getSharedPreferences("logindata", Context.MODE_PRIVATE);

                        livemart = database.getReference("OldMart/" + noo);




                    // DatabaseReference re=referencev.child(username);
                    livemart.child("picurl").setValue(downloadUrl.toString());

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                            .getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });


        }
        public void hello(final productDatayoo hell){



            final EditText alpha,beta,gamma,delta;
            final String yoo;
            Spinner objtype;

            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(a);
            LayoutInflater inflater = a.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_upload, null);
            dialogBuilder.setView(dialogView);

            alpha = (EditText) dialogView.findViewById(R.id.projectname);
            alpha.setText(hell.getProductname());
            beta = (EditText) dialogView.findViewById(R.id.projectdesc);
            beta.setText(hell.getProductdesc());
            gamma = dialogView.findViewById(R.id.contactno);
            gamma.setText(hell.getContactno());
            delta = dialogView.findViewById(R.id.productprice);
            delta.setText(hell.getPrice());
            objtype = dialogView.findViewById(R.id.typespinner);
            imageView=dialogView.findViewById(R.id.imageview);
            if (hell.getPicurl().equals("NULL")){
                imageView.setImageResource(R.drawable.noimage);
            }else {
                Glide
                        .with(a.getBaseContext())
                        .load(Uri.parse(hell.getPicurl()))
                        .into(imageView);
            }

            final String[] objects = {"General", "Mobile", "T-Shirts", "Electronics", "Study-Material"};


//Creating the ArrayAdapter instance having the bank name list
            ArrayAdapter aa = new ArrayAdapter(a.getBaseContext(), android.R.layout.simple_spinner_item, objects);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
            objtype.setAdapter(aa);
            objtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


    /*  imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_GET_CONTENT);
                    i.setType("image/*");
                    a.startActivityForResult(i, 1911);
                    

                }
            });*/

            dialogBuilder.setTitle("Edit your Product :");
            dialogBuilder.setPositiveButton("Re Upload", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    DatabaseReference livemart;
                    SharedPreferences prefs = a.getBaseContext().getSharedPreferences("logindata", Context.MODE_PRIVATE);
                    final String UID = prefs.getString("UID", "Random");
                    String username = prefs.getString("ID", "Error");
                    int PUN = prefs.getInt("PUN", 0);


                        livemart = database.getReference("OldMart/" + id);


                    // DatabaseReference re=referencev.child(username);

                    if (alpha.getText().toString().length() > 0 && beta.getText().toString().length() > 0 && gamma.getText().toString().length() > 0 && delta.getText().toString().length() > 0) {
                        if (isimage == 1) {
                            upload(id);
                            isimage = 0;

                        }else {


                        }
                        livemart.child("productname").setValue(alpha.getText().toString());
                        livemart.child("productdesc").setValue(beta.getText().toString());
                        livemart.child("contactno").setValue(gamma.getText().toString());
                        livemart.child("price").setValue(delta.getText().toString());
                        livemart.child("producttype").setValue("General");
                        livemart.child("PVN").setValue(System.currentTimeMillis());
                        livemart.child("email").setValue(username, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                                Toast.makeText(a.getBaseContext(), "Product Updated", Toast.LENGTH_SHORT).show();


                                if (databaseError != null) {

                                } else {

                                    //close dialoug


                                }
                            }
                        });

                    } else {
                        Toast.makeText(a.getBaseContext(), "Upload Failed : \nEnter All Fields", Toast.LENGTH_SHORT).show();
                    }


                }
            });
            dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });
            dialogBuilder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(!hell.getPicurl().equals("NULL"))
                    {
                        String fm=id;


                        SharedPreferences preferences= a.getSharedPreferences("logindata", Context.MODE_PRIVATE);
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                        String  UID = preferences.getString("UID", "Random");
                        String fno=fm.substring(28,31);
                        StorageReference  ref = storageReference.child("LostAndFound/" + UID +"/"+ fno + ".jpg");
                        ref.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                DatabaseReference livemart = database.getReference("OldMart/" + id);
                                livemart.removeValue();
                                Toast.makeText(a.getBaseContext(),"Product deleted",Toast.LENGTH_SHORT).show();


                            }
                        });

                    }
                    else {
                        DatabaseReference livemart = database.getReference("OldMart/" + id);
                        livemart.removeValue();
                        Toast.makeText(a.getBaseContext(),"Product deleted",Toast.LENGTH_SHORT).show();
                    }

                }
            });

            AlertDialog b = dialogBuilder.create();
            b.show();






        }





        public void setTitle(String stitle) {
            TextView a = productview.findViewById(R.id.producttittle);
            a.setText(stitle);


        }

        public void setImage(String stitle) {
            ImageView imageView = productview.findViewById(R.id.productimage);
            Uri uri = Uri.parse(stitle);
            //  Picasso.with(productview.getContext()).load(uri).into(imageView);
            Glide
                    .with(productview.getContext())
                    .load(uri)
                    .into(imageView);

        }

        public void setImage2() {
            ImageView imageView = productview.findViewById(R.id.productimage);
            imageView.setImageResource(R.drawable.noimage);


        }

        public void setlistener() {
            ImageView b = productview.findViewById(R.id.floatti);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                         hello(data);


                }
            });
        }

        public void setPrice(String price) {
            TextView b = productview.findViewById(R.id.money);
            b.setText(price);
        }

        public void setDesc(String setdesc) {
            TextView c = productview.findViewById(R.id.productdesc);
            c.setText(setdesc);
        }


    }

    public static class productDatayoo {
        String productname, productdesc, contactno, price, producttype, email, picurl;
        long pvn;


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
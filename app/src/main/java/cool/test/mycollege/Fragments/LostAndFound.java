package cool.test.mycollege.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

import cool.test.mycollege.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Vipin soni on 20-12-2017.
 */

public class LostAndFound extends Fragment implements AdapterView.OnItemSelectedListener {
    FloatingActionButton martadd;
    EditText a, b, c;

    ImageView imageView;
    Uri uri;
    Bitmap bitmap;
    String yoo = "General";
    FirebaseDatabase database;
    RecyclerView recyclerView;
    String[] objects = { "General", "Mobile", "T-Shirts", "Electronics", "Study-Material"};
    Context mctx = getActivity();
    FirebaseStorage storage;
    StorageReference storageReference;
    String UID;
    ProgressBar vvv;
    int PUN, pun2, lo;
    int isimage = 0;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

      //  yoo = objects[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            isimage = 1;
            pun2 = PUN;
            try {
                uri = data.getData();
                ContentResolver resolver = getActivity().getContentResolver();
                bitmap = MediaStore.Images.Media.getBitmap(resolver, uri);

                imageView.setImageBitmap(bitmap);
                //upload();
            } catch (IOException e) {

                e.printStackTrace();
            }

        }


    }


    /*public String compressImage(Bitmap n) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp =n;
        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {               imgRatio = maxHeight / actualHeight;                actualWidth = (int) (imgRatio * actualWidth);               actualHeight = (int) maxHeight;             } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }*/


    private void upload(final int vvv) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Image Uploading...");
        progressDialog.show();

        storageReference = storage.getReference();
        SharedPreferences prefs = getActivity().getSharedPreferences("logindata", Context.MODE_PRIVATE);
        UID = prefs.getString("UID", "Random");
        StorageReference ref;

        if (vvv < 10) {
            ref = storageReference.child("LostAndFound/" + UID + "/00" + vvv + ".jpg");

        } else if (vvv < 100&&vvv>9) {
            ref = storageReference.child("LostAndFound/" + UID + "/0" + vvv + ".jpg");


        } else {
            ref = storageReference.child("LostAndFound/" + UID + "/" + vvv + ".jpg");


        }
       // ref = storageReference.child("LostAndFound/" + UID + "/000" + ".jpg");

    /*    ref.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(mctx, "Error uploading Image", Toast.LENGTH_LONG).show();


                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploaded " + (int) progress + "%");
                    }
                });



*/


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


                Toast.makeText(getContext(), "Image upload Failed !", Toast.LENGTH_SHORT).show();


            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Toast.makeText(getContext(), "Image uploaded", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

                Uri downloadUrl = taskSnapshot.getDownloadUrl();


                DatabaseReference livemart;
                SharedPreferences prefs = getActivity().getSharedPreferences("logindata", Context.MODE_PRIVATE);
                UID = prefs.getString("UID", "Random");
                String username = prefs.getString("ID", "Error");
                PUN = prefs.getInt("PUN", 0);

                if (vvv < 10) {

                    livemart = database.getReference("LostAndFound/" + UID + "00" + String.valueOf(vvv));

                } else if (vvv < 100) {
                    livemart = database.getReference("LostAndFound/" + UID + "0" + String.valueOf(vvv));

                } else {
                    livemart = database.getReference("LostAndFound/" + UID + String.valueOf(vvv));

                }


                //livemart = database.getReference("LostAndFound/" + UID + "000");


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


    @Override
    public void onStart() {
        super.onStart();

        database = FirebaseDatabase.getInstance();
        DatabaseReference hell = database.getReference("LostAndFound");
        Query query = hell.orderByChild("PVN");//.startAt(loadPreferences()).endAt(loadPreferences());

        FirebaseRecyclerAdapter<productData, viewHolder> firebaseRecyclerAdapter = new
                FirebaseRecyclerAdapter<productData, viewHolder>(productData.class, R.layout.card_lost_and_found, viewHolder.class, query) {



                    @Override
                    protected void populateViewHolder(viewHolder viewholder, productData model, int position) {
                        vvv.setVisibility(View.INVISIBLE);
                        viewholder.geta(getActivity());
                        viewholder.givemodel(model);
                        viewholder.setTitle(model.getProductname());
                       // viewholder.setPrice(model.getPrice());
                        viewholder.setDesc(model.getProductdesc());
                        viewholder.setlistener(getRef(position).getKey(),model.getReport());
                        if (model.getPicurl().equals("NULL")) {
                            viewholder.setImage2();
                        } else {
                            viewholder.setImage(model.getPicurl());
                        }

                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);

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

        private void ShowMoreInfo(final String ss,final int f) {
            {

                TextView v, p, i, n;
                ImageView im;
                Button report;
                Uri bla;


                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(a);
                LayoutInflater inflater = a.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.open_lost_product, null);
                dialogBuilder.setView(dialogView);
                v = dialogView.findViewById(R.id.contactt);
                v.setText(data.getContactno());
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + data.getContactno()));
                        a.startActivity(intent);


                    }
                });
                report=dialogView.findViewById(R.id.report);
                report.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {


                        final SharedPreferences prefs = v.getContext().getSharedPreferences("logindata", Context.MODE_PRIVATE);
                        if (prefs.getString("isnormallogin", "").equals("true")) {




                        final AlertDialog.Builder alertdialog = new AlertDialog.Builder(v.getContext());
                        alertdialog.setTitle("Are You Sure You Want to report A spam For this Item");
                        //     final EditText input = new EditText(v.getContext());
                        //   LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        //         LinearLayout.LayoutParams.MATCH_PARENT,
                        //       LinearLayout.LayoutParams.MATCH_PARENT);
                        // input.setLayoutParams(lp);
                        //alertdialog.setView(input);
                        alertdialog.setIcon(R.drawable.ic_bug_report_black);

                        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                if (f != -1) {
                                    String UID = prefs.getString("UID", "Random");
                                    FirebaseDatabase reportcheck = FirebaseDatabase.getInstance();
                                    DatabaseReference cable;

                                    String email = firebaseUser.getEmail();
                                    String emailofuser = email.replace('.', ',');
                                    DatabaseReference report = reportcheck.getReference("LostAndFound/" + ss);

                                    int r = f + 1;
                                    report.child("report").setValue(r).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(a.getApplicationContext(), "Reported", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                    Log.e("working", "here");

                                    report.child(emailofuser).setValue("Report a Spam").addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.e("working", "yes");
                                            } else {
                                                Log.e("working", task.getException().getMessage());
                                            }
                                        }
                                    });


                                    //String message=input.grtText().toString();
                                } else {
                                    Toast.makeText(a.getApplicationContext(), "Product already verified by Admin", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        alertdialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        alertdialog.show();
                    }
                    else
                        {
                            Toast.makeText(a.getApplicationContext(),"Login First",Toast.LENGTH_SHORT).show();

                        }

                    }
                });
                p = dialogView.findViewById(R.id.productnamei);
                p.setText(data.getProductname());
                i = dialogView.findViewById(R.id.producttd);
                i.setText(data.getProductdesc());
              //  n = dialogView.findViewById(R.id.priceii);
              //  n.setText(data.getPrice());
                im = dialogView.findViewById(R.id.imageopen);
                String picc = data.getPicurl();
                if (picc.equals("NULL")) {
                    im.setImageResource(R.drawable.noimage);

                } else {
                    bla = Uri.parse(data.getPicurl());

                    loadit(im, bla, dialogView.getContext());
                }

                AlertDialog b = dialogBuilder.create();
                b.show();


            }
        }

        public void loadit(final ImageView iiii, Uri imagelink, Context c) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();

// Create a reference with an initial file path and name
    //        StorageReference pathReference = storageRef.child("image/latestproject.jpg");
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

        public void setlistener(final String s,final int f) {
            ImageView b = productview.findViewById(R.id.floatti);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowMoreInfo(s,f);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mart, container, false);
        vvv=v.findViewById(R.id.vipls);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        database = FirebaseDatabase.getInstance();
        martadd = v.findViewById(R.id.martadd);
        recyclerView = v.findViewById(R.id.martrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        martadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences=v.getContext().getSharedPreferences("logindata",Context.MODE_PRIVATE);
                if (sharedPreferences.getString("isnormallogin","skip").equals("true"))
                    showdialog();
                else Toast.makeText(v.getContext(),"Login First",Toast.LENGTH_SHORT).show();

            }
        });
        CheckMyList();


        return v;
    }

    private void CheckMyList() {

    }

    public void showdialog() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_upload_lost_and_found, null);
        dialogBuilder.setView(dialogView);

        a = (EditText) dialogView.findViewById(R.id.projectname);
        b = (EditText) dialogView.findViewById(R.id.projectdesc);
        c = dialogView.findViewById(R.id.contactno);
        //d = dialogView.findViewById(R.id.productprice);
/*        objtype = dialogView.findViewById(R.id.typespinner);
        objtype.setOnItemSelectedListener(this);

//Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, objects);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        objtype.setAdapter(aa);*/

        imageView = (ImageView) dialogView.findViewById(R.id.imageview);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i, 101);
            }
        });
        dialogBuilder.setTitle("Upload a Product :");
        dialogBuilder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                DatabaseReference livemart;
                SharedPreferences prefs = getActivity().getSharedPreferences("logindata", Context.MODE_PRIVATE);
                UID = prefs.getString("UID", "Random");
                String username = prefs.getString("ID", "Error");
                PUN = prefs.getInt("PUN", 0);
                lo = PUN;


                if (PUN < 10) {
                    livemart = database.getReference("LostAndFound/" + UID + "00" + String.valueOf(PUN));

                } else if (PUN < 100) {

                    livemart = database.getReference("LostAndFound/" + UID + "0" + String.valueOf(PUN));

                } else {
                    livemart = database.getReference("LostAndFound/" + UID + String.valueOf(PUN));

                }

               // livemart = database.getReference("LostAndFound/" + UID + "000" );


                // DatabaseReference re=referencev.child(username);

                if (a.getText().toString().length() > 0 && b.getText().toString().length() > 0 && c.getText().toString().length() > 9 ) {

                    if (isimage == 1) {
                        upload(lo);
                        isimage = 0;
                        livemart.child("picurl").setValue("");

                    }
                    livemart.child("picurl").setValue("NULL");

                    livemart.child("productname").setValue(a.getText().toString());
                    livemart.child("productdesc").setValue(b.getText().toString());
                    livemart.child("contactno").setValue(c.getText().toString());
                    livemart.child("price").setValue("0");
                    livemart.child("producttype").setValue(yoo);
                    livemart.child("report").setValue(0);

                    livemart.child("PVN").setValue(-1*System.currentTimeMillis());
                    livemart.child("email").setValue(username, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                            SharedPreferences prefs = getActivity().getSharedPreferences("logindata", Context.MODE_PRIVATE);
                            Toast.makeText(getContext(), "Product Uploaded", Toast.LENGTH_SHORT).show();
                            int a = prefs.getInt("PUN", 0);
                            a++;
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt("PUN", a);
                            editor.commit();

                            database = FirebaseDatabase.getInstance();
                            DatabaseReference referencev = database.getReference("Users/" + UID);
                            referencev.child("PUN").setValue(a);


                            if (databaseError != null) {
                            } else {

                                //close dialoug


                            }
                        }
                    });

                } else {
                    Toast.makeText(getContext(), "Upload Failed : \nEnter All Fields correctly", Toast.LENGTH_SHORT).show();
                }


            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        AlertDialog b = dialogBuilder.create();
        b.show();


    }


    public static class productData {
        String productname, productdesc, contactno, price, producttype, email, picurl;
        long pvn;
        int report;

        public int getReport() {
            return report;
        }

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
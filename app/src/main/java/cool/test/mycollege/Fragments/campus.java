package cool.test.mycollege.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import cool.test.mycollege.Adapter.RecyclerAdapter;
import cool.test.mycollege.R;

/**
 * Created by Vipin soni on 29-07-2018.
 */

public class campus extends Fragment {
    ImageView imageView;
    Spinner spinner;
    TextView textView;
    Toolbar toolbar;
    String[] buildings={"Heritage Building","Academic Block","Admin Block","Central Library","SAC (Student Activity Center)","NLHC","Sports Complex","Lower Ground","Upper Ground","GJLT","OLHC / OAT"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View v=inflater.inflate(R.layout.campus,null);
        imageView=v.findViewById(R.id.image_scrolling_top);
        Glide.with(this).load(R.drawable.heritage).apply(new RequestOptions().fitCenter()).into(imageView);
         final Toolbar toolbar=v.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        textView =v.findViewById(R.id.campustext);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,buildings);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner=v.findViewById(R.id.spinner_change_building);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {

                    case 0:
                        toolbar.setTitle("");

                        textView.setText(R.string.heritage);
                        Glide.with(getContext()).load(R.drawable.heritage).apply(new RequestOptions().fitCenter()).into(imageView);
                        break;

                    case 1:
//                        toolbar.setTitle("Academic Block");

                        textView.setText(R.string.academic);
                        Glide.with(getContext()).load(R.drawable.academic).apply(new RequestOptions().fitCenter()).into(imageView);
                        break;

                    case 2:
//                        toolbar.setTitle("Admin Block");

                        textView.setText(R.string.admin);
                        Glide.with(getContext()).load(R.drawable.admin).apply(new RequestOptions().fitCenter()).into(imageView);
                        break;
                    case 3:
//                        toolbar.setTitle("Library");

                        textView.setText(R.string.central);
                        Glide.with(getContext()).load(R.drawable.lib).apply(new RequestOptions().fitCenter()).into(imageView);
                        break;
                    case 4:
//                        toolbar.setTitle("SAC");

                        textView.setText(R.string.sac);
                        Glide.with(getContext()).load(R.drawable.sac).apply(new RequestOptions().fitCenter()).into(imageView);
                        break;
                    case 5:
//                        toolbar.setTitle("NLHC");

                        textView.setText(R.string.nlhc);
                        Glide.with(getContext()).load(R.drawable.nlhc).apply(new RequestOptions().fitCenter()).into(imageView);
                        break;
                    case 6:
//                        toolbar.setTitle("Sports Complex");

                        textView.setText(R.string.sports);
                        Glide.with(getContext()).load(R.drawable.sports_one).apply(new RequestOptions().fitCenter()).into(imageView);
                        break;
                    case 7:
//                        toolbar.setTitle("Lower Ground");

                        textView.setText(R.string.lower);
                        Glide.with(getContext()).load(R.drawable.sports).apply(new RequestOptions().fitCenter()).into(imageView);
                        break;
                    case 8:
//                        toolbar.setTitle("Upper Ground");

                        textView.setText(R.string.upper);
                        Glide.with(getContext()).load(R.drawable.lower).apply(new RequestOptions().fitCenter()).into(imageView);
                        break;
                    case 9:
//                        toolbar.setTitle("GJLT");

                        textView.setText(R.string.gjlt);
                        Glide.with(getContext()).load(R.drawable.gjlt).apply(new RequestOptions().fitCenter()).into(imageView);
                        break;

                    case 10:
//                        toolbar.setTitle("OLHC");

                        textView.setText(R.string.olhc);
                        Glide.with(getContext()).load(R.drawable.olhc).apply(new RequestOptions().fitCenter()).into(imageView);
                        break;


                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







        return v;
    }




}

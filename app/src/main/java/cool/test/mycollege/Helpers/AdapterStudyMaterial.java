package cool.test.mycollege.Helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import cool.test.mycollege.MyDataTypes.StudyMaterialBookData;
import cool.test.mycollege.R;

/**
 * Created by Vipin soni on 11-03-2018.
 */

public class AdapterStudyMaterial extends RecyclerView.Adapter<AdapterStudyMaterial.SimpleViewHolder>{
    private List<StudyMaterialBookData> studyMaterialBookData;
    Context context;
    LayoutInflater inflater;
    View view;
    SimpleViewHolder simpleViewHolder;

    public AdapterStudyMaterial(List<StudyMaterialBookData> list,Context context) {
        studyMaterialBookData=list;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view=LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_study_material, parent, false);
        simpleViewHolder=new SimpleViewHolder(view);
        return simpleViewHolder;
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        holder.simpleTextView.setText(studyMaterialBookData.get(position).getSubjectname());

    }

    @Override
    public int getItemCount() {
        return studyMaterialBookData.size();
    }


    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        private TextView simpleTextView;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            simpleTextView = (TextView) itemView.findViewById(R.id.subjectname);
        }
        public void bindData(final StudyMaterialBookData viewModel) {
            simpleTextView.setText(viewModel.getSubjectname());
        }
    }

}
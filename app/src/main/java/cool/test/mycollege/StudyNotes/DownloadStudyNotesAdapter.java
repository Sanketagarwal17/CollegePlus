package cool.test.mycollege.StudyNotes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import cool.test.mycollege.R;

public class DownloadStudyNotesAdapter extends RecyclerView.Adapter<DownloadStudyNotesAdapter.ViewHolder> {

    Context context;
    ArrayList<DownLoadStudyNotesModel> arrayList;

    public DownloadStudyNotesAdapter(Context context, ArrayList<DownLoadStudyNotesModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.studynotedownload, parent, false);
        return new ViewHolder(view);

    }

    public void filterList(ArrayList<DownLoadStudyNotesModel> filterdNames) {
        this.arrayList = filterdNames;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Log.e("Running","4");

        final DownLoadStudyNotesModel downLoadStudyNotesModel=arrayList.get(position);
        holder.name.setText(downLoadStudyNotesModel.getName());
        holder.topic.setText(downLoadStudyNotesModel.getTopic());
        holder.status.setText("Not Downloaded Yet?");


holder.download.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(downLoadStudyNotesModel.getUrl()));
        context.startActivity(intent);
        holder.status.setText("Already Downloaded");
    }
});





        }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }






    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,topic,status;
        Button download;
        public ViewHolder(View itemView)
        {
            super(itemView);

            name=itemView.findViewById(R.id.sname);
            topic=itemView.findViewById(R.id.stopic);
            status=itemView.findViewById(R.id.sstatus);
            download=itemView.findViewById(R.id.sdownload);
        }
    }
}

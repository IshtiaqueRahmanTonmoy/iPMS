package patient.patientmanagement.pms.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import patient.patientmanagement.pms.R;
import patient.patientmanagement.pms.entity.DoctorInfo;
import patient.patientmanagement.pms.entity.HealthNews;

public class HealthNewsAdapter extends RecyclerView.Adapter<HealthNewsAdapter.MyViewHolder>{
    private List<HealthNews> healthnewsList;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView id,headline,details,date;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
            id = (TextView) view.findViewById(R.id.idvalue);
            headline = (TextView) view.findViewById(R.id.headline);
            details = (TextView) view.findViewById(R.id.detailssome);
            date = (TextView) view.findViewById(R.id.uploadedon);

        }
    }


    public HealthNewsAdapter(List<HealthNews> healthnewsList) {
        this.healthnewsList = healthnewsList;
        Log.d("list", String.valueOf(healthnewsList.size()));
        //Toast.makeText(context, ""+specialistList.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public HealthNewsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.healthnews_row, parent, false);

        return new HealthNewsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HealthNewsAdapter.MyViewHolder holder, int position) {
        HealthNews healthnews = healthnewsList.get(position);
        //holder.image.setImageDrawable(R.drawable.ic_user);

        holder.id.setText(healthnews.getId());
        holder.headline.setText(healthnews.getHeading());
        holder.details.setText(healthnews.getDetails());
        holder.date.setText(healthnews.getDate());
        //holder.image.setText(doctor.getDesignation()+","+doctor.getHospitalName());
    }

    @Override
    public int getItemCount() {
        return healthnewsList.size();
    }
}

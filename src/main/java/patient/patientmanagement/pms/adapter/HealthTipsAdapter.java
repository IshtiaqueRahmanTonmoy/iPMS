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
import patient.patientmanagement.pms.entity.HealthNews;
import patient.patientmanagement.pms.entity.HealthTips;

public class HealthTipsAdapter extends RecyclerView.Adapter<HealthTipsAdapter.MyViewHolder>{
    private List<HealthTips> healthtipsList;
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


    public HealthTipsAdapter(List<HealthTips> healthtipsList) {
        this.healthtipsList = healthtipsList;
        Log.d("list", String.valueOf(healthtipsList.size()));
        //Toast.makeText(context, ""+specialistList.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public HealthTipsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.healthtips_row, parent, false);

        return new HealthTipsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HealthTipsAdapter.MyViewHolder holder, int position) {
        HealthTips healthtips = healthtipsList.get(position);
        //holder.image.setImageDrawable(R.drawable.ic_user);

        holder.id.setText(healthtips.getId());
        holder.headline.setText(healthtips.getHeading());
        holder.details.setText(healthtips.getDetails());
        holder.date.setText(healthtips.getDate());
        //holder.image.setText(doctor.getDesignation()+","+doctor.getHospitalName());
    }

    @Override
    public int getItemCount() {
        return healthtipsList.size();
    }
}

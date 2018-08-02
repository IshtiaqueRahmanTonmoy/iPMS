package patient.patientmanagement.pms.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import patient.patientmanagement.pms.HealthNewsActivity;
import patient.patientmanagement.pms.HealthNewsDetails;
import patient.patientmanagement.pms.HealthTipsDetails;
import patient.patientmanagement.pms.R;
import patient.patientmanagement.pms.entity.DoctorInfo;
import patient.patientmanagement.pms.entity.HealthNews;

public class HealthNewsAdapter extends RecyclerView.Adapter<HealthNewsAdapter.MyViewHolder>{
    private List<HealthNews> healthnewsList;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView id,headline,details,date,sharenow;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.full_image);
            id = (TextView) view.findViewById(R.id.idvalue);
            headline = (TextView) view.findViewById(R.id.headline);
            details = (TextView) view.findViewById(R.id.detailssomes);
            date = (TextView) view.findViewById(R.id.time_stamp);
            sharenow = (TextView) view.findViewById(R.id.sharenow);
        }
    }


    public HealthNewsAdapter(Context context, List<HealthNews> healthnewsList) {
        this.context = context;
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
    public void onBindViewHolder(final HealthNewsAdapter.MyViewHolder holder, int position) {
        HealthNews healthnews = healthnewsList.get(position);
        //holder.image.setImageDrawable(R.drawable.ic_user);
        Glide.with(context).load(healthnews.getImage()).into(holder.image);
        holder.id.setText(healthnews.getId());
        holder.headline.setText(healthnews.getHeading());
        holder.details.setText(healthnews.getDetails());
        holder.date.setText(healthnews.getDate());

        final String idvalues = holder.id.getText().toString();

        //holder.image.setImageDrawable(R.mipmap.image2);
        holder.headline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HealthNewsDetails.class);
                intent.putExtra("idvalue",idvalues);
                view.getContext().startActivity(intent);

            }
        });
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HealthNewsDetails.class);
                intent.putExtra("idvalue",idvalues);
                view.getContext().startActivity(intent);

            }
        });

        holder.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HealthNewsDetails.class);
                intent.putExtra("idvalue",idvalues);
                view.getContext().startActivity(intent);

            }
        });
        holder.headline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HealthNewsDetails.class);
                intent.putExtra("idvalue",idvalues);
                view.getContext().startActivity(intent);

            }
        });

        /*
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HealthNewsDetails.class);
                intent.putExtra("idvalue",idvalues);
                view.getContext().startActivity(intent);

            }
        });
        */
        holder.sharenow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String heading = holder.headline.getText().toString();
                String body = holder.details.getText().toString();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareboyd = "";
                //String shareboyd1 = heading;
                //String sharesub = body;
                //intent.putExtra(Intent.EXTRA_SUBJECT,shareboyd);
                intent.putExtra(Intent.EXTRA_SUBJECT,heading);
                intent.putExtra(Intent.EXTRA_TEXT,body);
                view.getContext().startActivity(Intent.createChooser(intent,"Share using"));
            }
        });

        //holder.image.setText(doctor.getDesignation()+","+doctor.getHospitalName());
    }

    @Override
    public int getItemCount() {
        return healthnewsList.size();
    }
}

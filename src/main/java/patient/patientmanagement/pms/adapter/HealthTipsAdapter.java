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

import java.util.List;

import patient.patientmanagement.pms.HealthNewsDetails;
import patient.patientmanagement.pms.HealthTipsDetails;
import patient.patientmanagement.pms.R;
import patient.patientmanagement.pms.entity.HealthNews;
import patient.patientmanagement.pms.entity.HealthTips;

public class HealthTipsAdapter extends RecyclerView.Adapter<HealthTipsAdapter.MyViewHolder>{
    private List<HealthTips> healthtipsList;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView id,headline,details,date,sharenow;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.imagearrows);
            id = (TextView) view.findViewById(R.id.idvalues);
            headline = (TextView) view.findViewById(R.id.headlines);
            details = (TextView) view.findViewById(R.id.detailssomes);
            date = (TextView) view.findViewById(R.id.uploadedons);
            sharenow = (TextView) view.findViewById(R.id.sharenows);
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
    public void onBindViewHolder(final HealthTipsAdapter.MyViewHolder holder, int position) {
        HealthTips healthtips = healthtipsList.get(position);
        //holder.image.setImageDrawable(R.drawable.ic_user);

        holder.id.setText(healthtips.getId());
        holder.headline.setText(healthtips.getHeading());
        holder.details.setText(healthtips.getDetails());
        holder.date.setText(healthtips.getDate());

        final String idvalues = holder.id.getText().toString();
        holder.headline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HealthTipsDetails.class);
                intent.putExtra("idvalue",idvalues);
                view.getContext().startActivity(intent);

            }
        });
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HealthTipsDetails.class);
                intent.putExtra("idvalue",idvalues);
                view.getContext().startActivity(intent);

            }
        });

        holder.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HealthTipsDetails.class);
                intent.putExtra("idvalue",idvalues);
                view.getContext().startActivity(intent);

            }
        });
        holder.headline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HealthTipsDetails.class);
                intent.putExtra("idvalue",idvalues);
                view.getContext().startActivity(intent);

            }
        });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HealthTipsDetails.class);
                intent.putExtra("idvalue",idvalues);
                view.getContext().startActivity(intent);

            }
        });
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
                view.getContext().startActivity(Intent.createChooser(intent,"Share using"));            }
        });


        //holder.image.setText(doctor.getDesignation()+","+doctor.getHospitalName());
    }

    @Override
    public int getItemCount() {
        return healthtipsList.size();
    }
}

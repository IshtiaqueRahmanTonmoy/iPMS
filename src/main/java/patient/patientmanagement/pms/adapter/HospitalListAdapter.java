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

import java.util.List;

import patient.patientmanagement.pms.HospitalActivity;
import patient.patientmanagement.pms.HospitalSearchActivity;
import patient.patientmanagement.pms.MapActivity;
import patient.patientmanagement.pms.R;
import patient.patientmanagement.pms.entity.DoctorInfo;
import patient.patientmanagement.pms.entity.Hospital;

public class HospitalListAdapter extends RecyclerView.Adapter<HospitalListAdapter.MyViewHolder>{
    private List<Hospital> hospitalList;
    String districtName;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView id,name,address,phone,maps,details;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
            id = (TextView) view.findViewById(R.id.idvalue);
            name = (TextView) view.findViewById(R.id.name);
            address = (TextView) view.findViewById(R.id.address);
            phone = (TextView) view.findViewById(R.id.phone);

            maps = (TextView) view.findViewById(R.id.maps);
            details = (TextView) view.findViewById(R.id.price);

        }
    }


    public HospitalListAdapter(Context context, List<Hospital> hospitalList, String districtName) {
        this.context = context;
        this.districtName = districtName;
        this.hospitalList = hospitalList;
        Log.d("list", String.valueOf(hospitalList.size()));
        //Toast.makeText(context, ""+specialistList.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public HospitalListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hospitalist_row, parent, false);

        return new HospitalListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HospitalListAdapter.MyViewHolder holder, int position) {
        final Hospital hospital = hospitalList.get(position);
        //holder.image.setImageDrawable(R.drawable.ic_user);

        holder.id.setText(hospital.getHospitalId());
        holder.name.setText(hospital.getHospitalName());
        holder.address.setText(hospital.getHospitalLocation());
        holder.phone.setText(hospital.getHospitalPhone());

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HospitalSearchActivity.class);
                intent.putExtra("district",districtName);
                intent.putExtra("hospital",hospital.getHospitalName());
                context.startActivity(intent);

            }
        });

        holder.maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MapActivity.class);
                intent.putExtra("district",districtName);
                intent.putExtra("hospital",hospital.getHospitalName());
                intent.putExtra("map","mapvalue");
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return hospitalList.size();
    }
}

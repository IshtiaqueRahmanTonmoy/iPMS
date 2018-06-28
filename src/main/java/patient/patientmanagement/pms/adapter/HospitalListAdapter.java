package patient.patientmanagement.pms.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import patient.patientmanagement.pms.HospitalActivity;
import patient.patientmanagement.pms.HospitalSearchActivity;
import patient.patientmanagement.pms.MapActivity;
import patient.patientmanagement.pms.R;
import patient.patientmanagement.pms.entity.DoctorInfo;
import patient.patientmanagement.pms.entity.Hospital;

import static android.content.Context.MODE_PRIVATE;

public class HospitalListAdapter extends RecyclerView.Adapter<HospitalListAdapter.MyViewHolder>{
    private List<Hospital> hospitalList;
    String districtName,hospitalName;
    Context context;
    public static final String PREFS_NAME = "MyApp_Settings";

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
        Log.d("list", districtName);
        //Toast.makeText(context, ""+specialistList.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public HospitalListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hospitalist_row, parent, false);

        return new HospitalListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HospitalListAdapter.MyViewHolder holder, int position) {
        final Hospital hospital = hospitalList.get(position);
        //holder.image.setImageDrawable(R.drawable.ic_user);

        holder.id.setText(hospital.getHospitalId());
        holder.name.setText(hospital.getHospitalName());
        holder.address.setText(hospital.getHospitalLocation());
        holder.phone.setText(hospital.getHospitalPhone());

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //String distname = districtName;
                //String hospname = holder.name.getText().toString();
                //Toast.makeText(view.getContext(), ""+distname, Toast.LENGTH_SHORT).show();
                //Toast.makeText(view.getContext(), ""+hospname, Toast.LENGTH_SHORT).show();

                //SharedPreferences settings = view.getContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                //districtName = settings.getString("districtkey", "");
                //hospitalName = settings.getString("hospitalkey","");

                hospitalName = holder.name.getText().toString();
                //Toast.makeText(view.getContext(), ""+hospitalName, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(view.getContext(), HospitalSearchActivity.class);
                intent.putExtra("district",districtName);
                intent.putExtra("idvalueforrecongnize","1");
                intent.putExtra("hospital",hospitalName);
                view.getContext().startActivity(intent);


            }
        });

        holder.maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String hos = holder.name.getText().toString();
                Log.d("hos",hos);


                Intent intent = new Intent(view.getContext(), MapActivity.class);
                intent.putExtra("district",districtName);
                intent.putExtra("hospital",hos);
                intent.putExtra("map","mapvalue");
                view.getContext().startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return hospitalList.size();
    }
}

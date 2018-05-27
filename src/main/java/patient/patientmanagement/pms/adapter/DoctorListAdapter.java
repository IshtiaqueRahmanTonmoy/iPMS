package patient.patientmanagement.pms.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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
import patient.patientmanagement.pms.entity.speciality;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.MyViewHolder>{
    private List<DoctorInfo> doctorList;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name,education,speciality,designationlocation;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
            name = (TextView) view.findViewById(R.id.name);
            education = (TextView) view.findViewById(R.id.education);
            speciality = (TextView) view.findViewById(R.id.speciality);
            designationlocation = (TextView) view.findViewById(R.id.designationlocation);
        }
    }


    public DoctorListAdapter(List<DoctorInfo> doctorList) {
        this.doctorList = doctorList;
        Log.d("list", String.valueOf(doctorList.size()));
        //Toast.makeText(context, ""+specialistList.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public DoctorListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctorlist_row, parent, false);

        return new DoctorListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DoctorListAdapter.MyViewHolder holder, int position) {
        DoctorInfo doctor = doctorList.get(position);

        //holder.image.setImageDrawable(R.drawable.ic_user);
        holder.name.setText(doctor.getDoctorName());
        holder.education.setText(doctor.getEducation());
        holder.speciality.setText(doctor.getSpecialist());
        holder.designationlocation.setText(doctor.getDesignation()+","+doctor.getHospitalName());
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }
}

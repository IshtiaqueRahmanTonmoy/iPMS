package patient.patientmanagement.pms.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import patient.patientmanagement.pms.AppoinmentBooking;
import patient.patientmanagement.pms.DoctorDetailsActivity;
import patient.patientmanagement.pms.DoctorList;
import patient.patientmanagement.pms.R;
import patient.patientmanagement.pms.entity.DoctorInfo;
import patient.patientmanagement.pms.entity.Hospital;
import patient.patientmanagement.pms.entity.speciality;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.MyViewHolder>{
    private List<DoctorInfo> doctorList;
    Context context;
    private ArrayList<DoctorInfo> arraylist;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView id,name,education,speciality,designationlocation,booknowTxt,profileTxt;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
            id = (TextView) view.findViewById(R.id.idvalue);
            name = (TextView) view.findViewById(R.id.name);
            education = (TextView) view.findViewById(R.id.education);
            speciality = (TextView) view.findViewById(R.id.speciality);
            designationlocation = (TextView) view.findViewById(R.id.designationlocation);

            booknowTxt = (TextView) view.findViewById(R.id.time);
            profileTxt = (TextView) view.findViewById(R.id.price);
        }
    }


    public DoctorListAdapter(Context context,List<DoctorInfo> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
        Log.d("list", String.valueOf(doctorList.size()));

        this.arraylist = new ArrayList<DoctorInfo>();
        this.arraylist.addAll(doctorList);
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
        final DoctorInfo doctor = doctorList.get(position);
        //holder.image.setImageDrawable(R.drawable.ic_user);

        holder.id.setText(doctor.getIdval());
        holder.name.setText(doctor.getDoctorName());
        holder.education.setText(doctor.getEducation());
        holder.speciality.setText(doctor.getSpecialist());
        holder.designationlocation.setText(doctor.getDesignation()+","+doctor.getHospitalName());

        /*
        holder.booknowTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AppoinmentBooking.class);
                intent.putExtra("idvalue",doctor.getIdval());
                intent.putExtra("name",doctor.getHospitalName());

                context.startActivity(intent);
            }
        });

        holder.profileTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DoctorDetailsActivity.class);
                intent.putExtra("idvalue",doctor.getIdval());
                intent.putExtra("name",doctor.getDoctorName());
                intent.putExtra("designationlocation", doctor.getDesignation()+","+doctor.getHospitalName());

                intent.putExtra("education",doctor.getEducation());
                intent.putExtra("speciality",doctor.getSpecialist());

                context.startActivity(intent);
            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        doctorList.clear();
        if (charText.length() == 0) {
            doctorList.addAll(arraylist);
        } else {
            for (DoctorInfo wp : arraylist) {
                if (wp.getDoctorName().toLowerCase(Locale.getDefault()).contains(charText) || wp.getDesignation().toLowerCase(Locale.getDefault()).contains(charText) || wp.getEducation().toLowerCase(Locale.getDefault()).contains(charText) || wp.getHospitalName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    doctorList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}

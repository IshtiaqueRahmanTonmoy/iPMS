package patient.patientmanagement.pms.patient.patientmanagement.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import patient.patientmanagement.pms.DoctorDetailsActivity;
import patient.patientmanagement.pms.DoctorList;
import patient.patientmanagement.pms.R;
import patient.patientmanagement.pms.adapter.DoctorListAdapter;
import patient.patientmanagement.pms.entity.DividerItemDecoration;
import patient.patientmanagement.pms.entity.DoctorInfo;
import patient.patientmanagement.pms.entity.RecyclerItemClickListener;

public class RelatedFragment extends Fragment {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefDoctor = database.getReference("doctorInfo");
    DatabaseReference myRefExpertise = database.getReference("speciality");
    DatabaseReference myRefHospital = database.getReference("hospitalInfo");
    private String idval,ImageDoctor,doctorName,education,specialityName,designation,location,specialistId,hospitalsId,hospitalName;
    private ProgressDialog progressDialog;
    private List<DoctorInfo> doctorList = new ArrayList<>();
    private DoctorListAdapter mAdapter;
    String speciality,specialityId;
    private RecyclerView recyclerView;

    public static Fragment newInstance() {
        RelatedFragment fragment = new RelatedFragment();
        //Toast.makeText(fragment.getActivity(), "ss", Toast.LENGTH_SHORT).show();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.related_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        showProcessDialog();
        Bundle extras = getActivity().getIntent().getExtras();

        if (extras != null) {


            //Toast.makeText(getActivity(), ""+idvalue, Toast.LENGTH_SHORT).show();

            education = extras.getString("education");
            speciality = extras.getString("speciality");

            //Toast.makeText(getActivity(), ""+education, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getActivity(), ""+speciality, Toast.LENGTH_SHORT).show();
            getValue(education,speciality);
        }

        return view;
    }

    private void showProcessDialog() {
        progressDialog = new ProgressDialog(getActivity());
        //progressDialog.setTitle("Getting Informations");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

    }

    private void getValue(final String education, String speciality) {
        myRefExpertise.orderByChild("specialityName").equalTo(String.valueOf(speciality)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    specialityId = String.valueOf(childDataSnapshot.child("specialityId").getValue());
                    valuegetsetvalue(education,specialityId);
                    //Log.d("valuesspecialist",districtId+specialityId);
                }
                //Toast.makeText(context, ""+dataSnapshot, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void valuegetsetvalue(final String education, final String specialityId) {
        myRefDoctor.orderByChild("education").equalTo(education).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

                    idval = childDataSnapshot.getKey();
                    //Toast.makeText(DoctorList.this, "snapshot"+id, Toast.LENGTH_SHORT).show();
                    //Log.d("iddatasnap",id);

                    ImageDoctor = String.valueOf(childDataSnapshot.child("image").getValue());
                    doctorName = String.valueOf(childDataSnapshot.child("name").getValue());
                    //education = String.valueOf(childDataSnapshot.child("education").getValue());
                    specialistId = String.valueOf(childDataSnapshot.child("specialityId").getValue());
                    designation = String.valueOf(childDataSnapshot.child("designation").getValue());
                    hospitalsId = String.valueOf(childDataSnapshot.child("hospitalId").getValue());

                    int special = Integer.parseInt(specialistId);
                    int specialitymatch = Integer.parseInt(specialityId);
                    if(special == specialitymatch){
                        getvalue(idval,ImageDoctor,doctorName,education,specialistId,designation,hospitalsId);
                    }
                    else{
                        Toast.makeText(getActivity(), "Value not found..", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    //Toast.makeText(DoctorList.this, ""+doctorName+education+designation, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getvalue(final String idval, String imageDoctor, final String doctorName, final String education, String specialistId, final String designation, final String hospitalsId) {
        int specialistid = Integer.parseInt(specialistId);
        Log.d("special", String.valueOf(specialistid));

        myRefExpertise.orderByChild("specialityId").equalTo(specialistid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    specialityName = String.valueOf(childDataSnapshot.child("specialityName").getValue());
                    //Toast.makeText(DoctorList.this, "SpecialityName"+specialityName, Toast.LENGTH_SHORT).show();
                    //getFinalCriteria(districtId,specialityId);
                }
                //Toast.makeText(context, ""+dataSnapshot, Toast.LENGTH_SHORT).show();
                getvalues(idval,ImageDoctor,doctorName,education,specialityName,designation,hospitalsId);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getvalues(final String idval, final String imageDoctor, final String doctorName, final String education, final String specialityName, final String designation, String hospitalsId) {
        int hospitalid = Integer.parseInt(hospitalsId);

        //Toast.makeText(DoctorList.this, ""+hospitalid, Toast.LENGTH_SHORT).show();
        myRefHospital.orderByChild("hospitalId").equalTo(hospitalid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    location = String.valueOf(childDataSnapshot.child("hospitalName").getValue());

                    //Toast.makeText(DoctorList.this, ""+location, Toast.LENGTH_SHORT).show();
                }
                setValueinList(idval,imageDoctor,doctorName,education,specialityName,designation,location);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setValueinList(String idval, String imageDoctor, String doctorName, String education, String specialityName, String designation, String location) {

        doctorList.add(new DoctorInfo(idval,imageDoctor,doctorName,education,specialityName,designation,location));
        mAdapter = new DoctorListAdapter(doctorList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), R.drawable.divider));

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        progressDialog.dismiss();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        TextView idtxtview = (TextView) view.findViewById(R.id.idvalue);
                        TextView nametxtview = (TextView) view.findViewById(R.id.name);
                        TextView designationtxtview = (TextView) view.findViewById(R.id.designationlocation);
                        TextView educationtxtview = (TextView) view.findViewById(R.id.education);
                        TextView specialitytxtview = (TextView) view.findViewById(R.id.speciality);

                        //String specialityName = txtview.getText().toString();

                        String idvalue = idtxtview.getText().toString();
                        String name = nametxtview.getText().toString();
                        String designatinlocation = designationtxtview.getText().toString();

                        String education = educationtxtview.getText().toString();
                        String specialtiy = specialitytxtview.getText().toString();

                        Intent intent = new Intent(getActivity(), DoctorDetailsActivity.class);
                        intent.putExtra("idvalue",idvalue);
                        intent.putExtra("name",name);
                        intent.putExtra("designationlocation", designatinlocation);

                        intent.putExtra("education",education);
                        intent.putExtra("speciality",specialtiy);

                        startActivity(intent);

                        //Toast.makeText(getContext(), ""+district+""+hospitalName, Toast.LENGTH_SHORT).show();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }
}

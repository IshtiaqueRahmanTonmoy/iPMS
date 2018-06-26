package patient.patientmanagement.pms.patient.patientmanagement.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import patient.patientmanagement.pms.AppoinmentBooking;
import patient.patientmanagement.pms.DoctorList;
import patient.patientmanagement.pms.R;
import patient.patientmanagement.pms.adapter.ChamberAdapter;
import patient.patientmanagement.pms.adapter.ShadowTransformer;
import patient.patientmanagement.pms.adapter.SpecialistAdapter;
import patient.patientmanagement.pms.entity.Chamber;
import patient.patientmanagement.pms.entity.DividerItemDecoration;
import patient.patientmanagement.pms.entity.RecyclerItemClickListener;
import patient.patientmanagement.pms.entity.speciality;

/**
 * Created by suraj on 23/6/17.
 */

public class AboutFragment extends Fragment {

    private ViewPager mViewPager;

    private ChamberAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private TextView nameTxt, shortDescriptionTxt, educationTxt, specialityTxt, noofchamberTxt;
    private TextView mTextMessage;
    private String idvalue, name, shortdescription, education, speciality,district,hospital,expertise;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("doctorInfo");

    private ProgressDialog progressDialog;
    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);


        nameTxt = (TextView) view.findViewById(R.id.name);
        noofchamberTxt = (TextView) view.findViewById(R.id.noofchamber);
        shortDescriptionTxt = (TextView) view.findViewById(R.id.short_description);
        educationTxt = (TextView) view.findViewById(R.id.education);
        specialityTxt = (TextView) view.findViewById(R.id.speciality);


        showProcessDialog();

        Bundle extras = getActivity().getIntent().getExtras();

        if (extras != null) {

            district = extras.getString("district");
            hospital = extras.getString("hospital");
            expertise = extras.getString("expertise");

            idvalue = extras.getString("idvalue");
            getvalue(idvalue);
            //Toast.makeText(getActivity(), ""+idvalue, Toast.LENGTH_SHORT).show();
            name = extras.getString("name");
            shortdescription = extras.getString("designationlocation");

            education = extras.getString("education");
            speciality = extras.getString("speciality");

            nameTxt.setText(name);
            shortDescriptionTxt.setText(shortdescription);

            educationTxt.setText(education);
            specialityTxt.setText(speciality);

        }

        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);



        mTextMessage = (TextView) view.findViewById(R.id.message);

        return view;

    }

    private void showProcessDialog() {
        progressDialog = new ProgressDialog(getActivity());
        //progressDialog.setTitle("Getting Informations");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
    }

    private void getvalue(final String idvalue) {

        mCardAdapter = new ChamberAdapter(getActivity());
        myRef.child(idvalue).child("chamber").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("datasnpahsopt", String.valueOf(dataSnapshot.getChildrenCount()));

                noofchamberTxt.setText(""+dataSnapshot.getChildrenCount());
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.child("chambername").getValue(String.class);
                    //names.add(name);
                    String number = ds.child("location").getValue(String.class);


                    mCardAdapter.addCardItem(new Chamber(idvalue,name,number,"BOOK APPOINMENT NOW",district,hospital,expertise,shortdescription,education,speciality));
                    //mCardAdapter.addCardItem(new Chamber("Syed Diagonstics & Consultation Center,Main Branch", "Ka 164/2(Ground Floor),Bottola,Khilagaon,Dhaka", "BOOK APPOINMENT NOW"));

                    Log.d("TAG", name + " / " + number);
                }

                mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);


                mViewPager.setAdapter(mCardAdapter);
                mViewPager.setPageTransformer(false, mCardShadowTransformer);
                mViewPager.setOffscreenPageLimit(3);

                progressDialog.dismiss();


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}

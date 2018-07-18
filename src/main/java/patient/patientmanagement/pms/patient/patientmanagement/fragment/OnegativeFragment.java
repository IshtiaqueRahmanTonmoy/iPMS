package patient.patientmanagement.pms.patient.patientmanagement.fragment;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import patient.patientmanagement.pms.R;
import patient.patientmanagement.pms.adapter.BloodListAdapter;
import patient.patientmanagement.pms.entity.Blood;
import patient.patientmanagement.pms.entity.DividerItemDecoration;

/**
 * Created by suraj on 23/6/17.
 */

public class OnegativeFragment extends Fragment {

    String districtName,thanaName,bloodGroup;
    RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("bloodDonor");
    DatabaseReference myRefDistrict = database.getReference("district");
    DatabaseReference myRefThana = database.getReference("thana");

    private List<Blood> bloodList = new ArrayList<Blood>();
    private BloodListAdapter bloodAdapter;

    String districtId,thanaId,name,address,phone;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_oneg, container, false);

        bloodList.clear();
        showProcessDialog();


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            districtName = extras.getString("district");
            thanaName = extras.getString("thana");
            bloodGroup = extras.getString("bloodgroup");

            bloodGroup = "O-";

            if(!districtName.equals("null") && thanaName.equals("")){
                //Toast.makeText(getContext(), "with district", Toast.LENGTH_SHORT).show();
                getDistrict();
                //Toast.makeText(getContext(), ""+districtName, Toast.LENGTH_SHORT).show();
            }
            else{
                //Toast.makeText(getContext(), "with district and thana", Toast.LENGTH_SHORT).show();
                getdistrictAndThana();
                //Toast.makeText(getContext(), ""+districtName, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(), ""+thanaName, Toast.LENGTH_SHORT).show();
            }
        }
         return view;
    }

    private void getDistrict() {
        myRefDistrict.orderByChild("districtName").equalTo(String.valueOf(districtName)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    String id = String.valueOf(childDataSnapshot.child("districtId").getValue());
                    //Toast.makeText(getActivity(), ""+id, Toast.LENGTH_SHORT).show();
                    getValuesget(id,bloodGroup);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getValuesget(String id, String bloodGroup) {
        Log.d("value",id);
        //Log.d("values",idthana);
        Log.d("valuess",bloodGroup);

        final int ids = Integer.parseInt(id);
        //final int idsthana = Integer.parseInt(idthana);

        myRef.orderByChild("bloodgroup").equalTo(bloodGroup).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {


                    districtId = String.valueOf(childDataSnapshot.child("districtId").getValue());
                    thanaId = String.valueOf(childDataSnapshot.child("thanaId").getValue());
                    name = String.valueOf(childDataSnapshot.child("name").getValue());
                    address = String.valueOf(childDataSnapshot.child("address").getValue());
                    phone = String.valueOf(childDataSnapshot.child("phone").getValue());


                    //Toast.makeText(getActivity(), ""+name+""+address, Toast.LENGTH_SHORT).show();
                    int districtmatch = Integer.parseInt(districtId);
                    int thanamatch = Integer.parseInt(thanaId);

                    if(ids == districtmatch){
                        bloodList.add(new Blood(name,address,phone));
                        bloodAdapter = new BloodListAdapter(getActivity(),bloodList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        //recyclerView.addItemDecoration(
                        //        new DividerItemDecoration(getActivity(), R.drawable.divider));

                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(bloodAdapter);
                        progressDialog.dismiss();
                        //getvalue(idval,ImageDoctor,doctorName,education,specialistId,designation,hospitalsId);
                    }
                    else{
                        //Toast.makeText(getActivity(), "Value not found..", Toast.LENGTH_SHORT).show();
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

    private void getdistrictAndThana() {
        myRefDistrict.orderByChild("districtName").equalTo(String.valueOf(districtName)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    String id = String.valueOf(childDataSnapshot.child("districtId").getValue());
                    //Toast.makeText(getActivity(), ""+id, Toast.LENGTH_SHORT).show();
                    getValue(id,thanaName,bloodGroup);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void showProcessDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Getting Blood Groups");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
    }

    private void getValue(final String id, final String thanaName, final String bloodGroup) {

        myRefThana.orderByChild("thanaName").equalTo(String.valueOf(thanaName)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    String idthana = String.valueOf(childDataSnapshot.child("thanaId").getValue());
                    //Toast.makeText(getActivity(), ""+idthana, Toast.LENGTH_SHORT).show();
                    setValue(id,idthana,bloodGroup);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setValue(final String id, String idthana, String bloodGroup) {

        final int ids = Integer.parseInt(id);
        final int idsthana = Integer.parseInt(idthana);

        myRef.orderByChild("bloodgroup").equalTo(bloodGroup).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {


                    districtId = String.valueOf(childDataSnapshot.child("districtId").getValue());
                    thanaId = String.valueOf(childDataSnapshot.child("thanaId").getValue());
                    name = String.valueOf(childDataSnapshot.child("name").getValue());
                    address = String.valueOf(childDataSnapshot.child("address").getValue());
                    phone = String.valueOf(childDataSnapshot.child("phone").getValue());


                    //Toast.makeText(getActivity(), ""+name+""+address, Toast.LENGTH_SHORT).show();
                    int districtmatch = Integer.parseInt(districtId);
                    int thanamatch = Integer.parseInt(thanaId);

                    if(ids == districtmatch && idsthana == thanamatch){
                        bloodList.add(new Blood(name,address,phone));
                        bloodAdapter = new BloodListAdapter(getActivity(),bloodList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        //recyclerView.addItemDecoration(
                        //        new DividerItemDecoration(getActivity(), R.drawable.divider));

                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(bloodAdapter);
                        progressDialog.dismiss();


                        //getvalue(idval,ImageDoctor,doctorName,education,specialistId,designation,hospitalsId);
                    }
                    else{
                        //Toast.makeText(getActivity(), "Value not found..", Toast.LENGTH_SHORT).show();
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
}


package patient.patientmanagement.pms.patient.patientmanagement.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
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

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import patient.patientmanagement.pms.DoctorList;
import patient.patientmanagement.pms.R;
import patient.patientmanagement.pms.adapter.SpecialistAdapter;
import patient.patientmanagement.pms.entity.DividerItemDecoration;
import patient.patientmanagement.pms.entity.RecyclerItemClickListener;
import patient.patientmanagement.pms.entity.speciality;

/**
 * Created by suraj on 23/6/17.
 */

public class Tab1Fragment extends Fragment {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("district");
    private DatabaseReference myRefHospital = database.getReference("hospitalInfo");
    private DatabaseReference myRefSpecialites = database.getReference("speciality");
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private String district,districtId,specialitstId,hospitalName,hospitalAddress,hospitalPhone,hospitalId;
    private int specialityId;
    private List<speciality> specialistList = new ArrayList<>();
    private SpecialistAdapter mAdapter;
    private TextView hospitalTxt,addressTxt,phoneTxt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_tab1, container, false);

        Firebase.setAndroidContext(getActivity());


        showProcessDialog();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        hospitalTxt = (TextView) view.findViewById(R.id.hospital);
        addressTxt = (TextView) view.findViewById(R.id.address);
        phoneTxt = (TextView) view.findViewById(R.id.phone);

        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            district = extras.getString("district");
            hospitalName = extras.getString("hospital");

            hospitalTxt.setText(hospitalName);


            myRefHospital.orderByChild("hospitalName").equalTo(String.valueOf(hospitalName)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        hospitalAddress = String.valueOf(childDataSnapshot.child("hospitalAddress").getValue());
                        hospitalPhone = String.valueOf(childDataSnapshot.child("phone").getValue());
                        hospitalId = String.valueOf(childDataSnapshot.child("hospitalId").getValue());

                        //Toast.makeText(getContext().getApplicationContext(), ""+hospitalId, Toast.LENGTH_SHORT).show();
                        addressTxt.setText(hospitalAddress);
                        phoneTxt.setText(hospitalPhone);
                        //Toast.makeText(getActivity().getApplicationContext(), ""+districtId, Toast.LENGTH_SHORT).show();
                        //getAllValueCriteria2(districtId,expertise);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            myRef.orderByChild("districtName").equalTo(String.valueOf(district)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        districtId = String.valueOf(childDataSnapshot.child("districtId").getValue());
                        getSpecialities(districtId);
                        //Toast.makeText(getActivity().getApplicationContext(), ""+districtId, Toast.LENGTH_SHORT).show();
                        //getAllValueCriteria2(districtId,expertise);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //Toast.makeText(getActivity().getApplicationContext(), ""+district, Toast.LENGTH_SHORT).show();
        }

        return view;
    }


    private void showProcessDialog() {
            progressDialog = new ProgressDialog(getActivity());
            //progressDialog.setTitle("Getting Informations");
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

    }

    private void getSpecialities(final String districtId) {
        final Firebase ref = new Firebase("https://patientmanagementsystem-c2ab6.firebaseio.com/hospitalInfo");
        final Query queryRefs = ref.orderByChild("districtId").equalTo(districtId);

        // Attach an listener to read the data at our posts reference
       queryRefs.addValueEventListener(new com.firebase.client.ValueEventListener() {
           @Override
           public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
               Log.d("data", String.valueOf(dataSnapshot));
               //Toast.makeText(getContext(), ""+dataSnapshot.getValue(), Toast.LENGTH_SHORT).show();
               //Log.d("booksvalue", String.valueOf(dataSnapshot.getValue()));
               String myParentNode = dataSnapshot.getKey();
               Log.d("node",myParentNode);

               final Firebase refs = new Firebase("https://patientmanagementsystem-c2ab6.firebaseio.com/hospitalInfo/0/specialities");
               Query query = refs.orderByChild("specialities");
               query.addValueEventListener(new com.firebase.client.ValueEventListener() {
                  @Override
                  public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                      Log.d("datasnapshopt", String.valueOf(dataSnapshot.getValue()));

                      for (com.firebase.client.DataSnapshot categorySnapshot: dataSnapshot.getChildren()) {
                          specialityId = Integer.parseInt(categorySnapshot.child("specialityId").getValue().toString());
                          myRefSpecialites.orderByChild("specialityId").equalTo(specialityId).addListenerForSingleValueEvent(new ValueEventListener() {
                              @Override
                              public void onDataChange(DataSnapshot dataSnapshot) {
                                  for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                                      String name = String.valueOf(childDataSnapshot.child("specialityName").getValue());
                                      specialistList.add(new speciality(name));
                                      //Toast.makeText(getContext(), ""+name, Toast.LENGTH_SHORT).show();

                                      //value(id);
                                  }

                                  mAdapter = new SpecialistAdapter(specialistList);
                                  RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                                  //recyclerView.addItemDecoration(
                                  //        new DividerItemDecoration(getActivity(), R.drawable.divider));

                                  recyclerView.setLayoutManager(mLayoutManager);
                                  recyclerView.setItemAnimator(new DefaultItemAnimator());
                                  recyclerView.setAdapter(mAdapter);

                                  progressDialog.dismiss();
                                  recyclerView.addOnItemTouchListener(
                                          new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                                              @Override public void onItemClick(View view, int position) {
                                                  TextView txtview = (TextView) view.findViewById(R.id.title);
                                                  String specialityName = txtview.getText().toString();


                                                  Intent intent = new Intent(getContext(), DoctorList.class);
                                                  intent.putExtra("district", district);
                                                  intent.putExtra("hospital", hospitalName);
                                                  intent.putExtra("expertise", specialityName);
                                                  startActivity(intent);

                                                  //Toast.makeText(getContext(), ""+district+""+hospitalName, Toast.LENGTH_SHORT).show();
                                              }

                                              @Override public void onLongItemClick(View view, int position) {
                                                  // do whatever
                                              }
                                          })
                                  );




                                  //Toast.makeText(context, ""+dataSnapshot, Toast.LENGTH_SHORT).show();
                              }

                              @Override
                              public void onCancelled(DatabaseError databaseError) {

                              }
                          });
                          //Toast.makeText(getContext(), ""+categoryName, Toast.LENGTH_SHORT).show();

                          //Log.d("result", categoryName);
                      }
                  }

                  @Override
                  public void onCancelled(FirebaseError firebaseError) {

                  }
              });
           }

           @Override
           public void onCancelled(FirebaseError firebaseError) {

           }
       });


    }
}

package patient.patientmanagement.pms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import patient.patientmanagement.pms.adapter.DoctorListAdapter;
import patient.patientmanagement.pms.adapter.HospitalListAdapter;
import patient.patientmanagement.pms.entity.DividerItemDecoration;
import patient.patientmanagement.pms.entity.DoctorInfo;
import patient.patientmanagement.pms.entity.Hospital;
import patient.patientmanagement.pms.entity.RecyclerItemClickListener;

public class HospitalActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myRefDistrict = database.getReference("district");
    DatabaseReference myRef = database.getReference("hospitalInfo");
    String districtName;
    private List<Hospital> hospitalList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HospitalListAdapter mAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle extras = getIntent().getExtras();

        showProcessDialog();
        if (extras != null) {
            districtName = extras.getString("district");

        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            myRefDistrict.orderByChild("districtName").equalTo(String.valueOf(districtName)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    String id = String.valueOf(childDataSnapshot.child("districtId").getValue());
                    value(districtName,id);
                }
                //Toast.makeText(context, ""+dataSnapshot, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showProcessDialog() {
        progressDialog = new ProgressDialog(HospitalActivity.this);
        //progressDialog.setTitle("Getting Informations");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

    }

    private void value(final String districtName, final String id) {
        int ids = Integer.parseInt(id);
        myRef.orderByChild("districtId").equalTo(ids).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    String hospitalName = String.valueOf(childDataSnapshot.child("hospitalName").getValue());
                    String hospitalAddress = String.valueOf(childDataSnapshot.child("hospitalAddress").getValue());
                    String hospitalPhone = String.valueOf(childDataSnapshot.child("phone").getValue());

                    hospitalList.add(new Hospital(id,hospitalName,hospitalAddress,hospitalPhone));
                    //value(id);
                }

                mAdapter = new HospitalListAdapter(hospitalList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(HospitalActivity.this);
                recyclerView.addItemDecoration(
                        new DividerItemDecoration(HospitalActivity.this, R.drawable.divider));

                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
                progressDialog.dismiss();

                recyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(HospitalActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                            @Override public void onItemClick(View view, int position) {

                                TextView idtxtview = (TextView) view.findViewById(R.id.idvalue);
                                TextView nametxtview = (TextView) view.findViewById(R.id.name);

                                String Hospitalname = nametxtview.getText().toString();

                                Intent intent = new Intent(HospitalActivity.this, HospitalSearchActivity.class);
                                intent.putExtra("district",districtName);
                                intent.putExtra("hospital",Hospitalname);
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
    }
}

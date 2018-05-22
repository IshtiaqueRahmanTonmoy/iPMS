package patient.patientmanagement.pms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorList extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();


    DatabaseReference myRef = database.getReference("district");
    DatabaseReference myRefHospital = database.getReference("hospitalInfo");
    DatabaseReference myRefExpertise = database.getReference("speciality");

    private String districtId,hospitalId,specialityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        Bundle extras = getIntent().getExtras();
        String district,hospital,expertise;

        if (extras != null) {
            district = extras.getString("district");
            hospital = extras.getString("hospital");
            expertise = extras.getString("expertise");


            if(!district.isEmpty() && !hospital.isEmpty() && !expertise.isEmpty()){
                Toast.makeText(this, "none of null", Toast.LENGTH_SHORT).show();
                getAllIdValues(district,hospital,expertise);
            }
            else if(!district.isEmpty() && !hospital.isEmpty()){
                Toast.makeText(this, "expertise null", Toast.LENGTH_SHORT).show();
                getAllIdValuesSearch(district,hospital);
            }
            else if(!district.isEmpty() && !expertise.isEmpty()){
                Toast.makeText(this, "hospital null", Toast.LENGTH_SHORT).show();
                getAllIdValuesSearch2(district,expertise);
            }
            else{
                Toast.makeText(this, "all null", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void getAllIdValuesSearch2(String district, final String expertise) {
        myRef.orderByChild("districtName").equalTo(String.valueOf(district)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    districtId = String.valueOf(childDataSnapshot.child("districtId").getValue());
                    getAllValueCriteria2(districtId,expertise);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getAllValueCriteria2(final String districtId, String expertise) {
        myRefExpertise.orderByChild("specialityName").equalTo(String.valueOf(expertise)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    specialityId = String.valueOf(childDataSnapshot.child("specialityId").getValue());
                    getFinalCriteria(districtId,specialityId);
                }
                //Toast.makeText(context, ""+dataSnapshot, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*Final search criteria 3*/
    private void getFinalCriteria(String districtId, String specialityId) {
        Toast.makeText(this, "districtid"+districtId+"specialityid"+specialityId, Toast.LENGTH_SHORT).show();
    }
    /* End of search criteria 3*/

    private void getAllIdValuesSearch(String district, final String hospital) {
        myRef.orderByChild("districtName").equalTo(String.valueOf(district)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    districtId = String.valueOf(childDataSnapshot.child("districtId").getValue());
                    getAllValue(districtId,hospital);
                }

                //Toast.makeText(DoctorList.this, ""+districtId, Toast.LENGTH_SHORT).show();

                //Toast.makeText(context, ""+dataSnapshot, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        }

    private void getAllValue(final String districtId, String hospital) {
        myRefHospital.orderByChild("hospitalName").equalTo(String.valueOf(hospital)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    hospitalId = String.valueOf(childDataSnapshot.child("hospitalId").getValue());
                    getFinalSearch(districtId,hospitalId);
                }
                //Toast.makeText(context, ""+dataSnapshot, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /* Search criteria 2 */
    private void getFinalSearch(String districtId, String hospitalId) {
        Toast.makeText(this, "districtid"+districtId+"hospitalid"+hospitalId, Toast.LENGTH_SHORT).show();

    }
    /* End of Search criteria 2 */


    private void getAllIdValues(String district, final String hospital, final String expertise) {
        myRef.orderByChild("districtName").equalTo(String.valueOf(district)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    districtId = String.valueOf(childDataSnapshot.child("districtId").getValue());
                    value(districtId,hospital,expertise);
                }

                //Toast.makeText(DoctorList.this, ""+districtId, Toast.LENGTH_SHORT).show();

                //Toast.makeText(context, ""+dataSnapshot, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //Toast.makeText(DoctorList.this, ""+districtId, Toast.LENGTH_SHORT).show();
        //Log.d("values",districtId+"hosptialId"+hospitalId+"expersitsid"+specialityId);
    }

    private void value(final String districtId, String hospital, final String expertise) {

        //Toast.makeText(this, ""+districtId+hospital+expertise, Toast.LENGTH_SHORT).show();
        myRefHospital.orderByChild("hospitalName").equalTo(String.valueOf(hospital)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    hospitalId = String.valueOf(childDataSnapshot.child("hospitalId").getValue());
                    valueId(hospitalId,districtId,expertise);
                }
                //Toast.makeText(context, ""+dataSnapshot, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void valueId(final String hospitalId, final String districtId, String expertise) {
        Log.d("values",hospitalId+districtId);
        myRefExpertise.orderByChild("specialityName").equalTo(String.valueOf(expertise)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    specialityId = String.valueOf(childDataSnapshot.child("specialityId").getValue());
                    valueget(hospitalId,districtId,specialityId);
                    Log.d("values",hospitalId+districtId+specialityId);
                }
                //Toast.makeText(context, ""+dataSnapshot, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /* Search criteria 1*/
    private void valueget(String hospitalId, String districtId, String specialityId) {
        //Toast.makeText(this, "districtid"+districtId+"hospitalid"+hospitalId+"expertiseid"+specialityId, Toast.LENGTH_SHORT).show();

    }
    /* End of search criteria 1*/
}

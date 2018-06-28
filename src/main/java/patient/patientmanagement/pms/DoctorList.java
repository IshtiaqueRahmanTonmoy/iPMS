package patient.patientmanagement.pms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import patient.patientmanagement.pms.adapter.DoctorListAdapter;
import patient.patientmanagement.pms.adapter.SpecialistAdapter;
import patient.patientmanagement.pms.entity.DividerItemDecoration;
import patient.patientmanagement.pms.entity.DoctorInfo;
import patient.patientmanagement.pms.entity.RecyclerItemClickListener;
import patient.patientmanagement.pms.entity.speciality;

public class DoctorList extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();


    DatabaseReference myRef = database.getReference("district");
    DatabaseReference myRefHospital = database.getReference("hospitalInfo");
    DatabaseReference myRefExpertise = database.getReference("speciality");
    DatabaseReference myRefDoctor = database.getReference("doctorInfo");
    private String district,hospital,expertise;
    private String districtId,hospitalId,specialityId;
    private List<DoctorInfo> doctorList = new ArrayList<>();
    private DoctorListAdapter mAdapter;
    private ProgressDialog progressDialog;

    private String idvalrecong,idvalrecongd,idvalrecondoctor,idval,ImageDoctor,doctorName,education,specialityName,designation,location,specialistId,hospitalsId,hospitalName;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        showProcessDialog();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            district = extras.getString("district");
            hospital = extras.getString("hospital");
            expertise = extras.getString("expertise");
            idvalrecong = extras.getString("idvalueforrecongnize");
            idvalrecongd = extras.getString("idvalueforrecongnized");
            idvalrecondoctor = extras.getString("idvalueforrecongnizedoctor");

          Toast.makeText(DoctorList.this, ""+idvalrecondoctor, Toast.LENGTH_SHORT).show();
          Toast.makeText(DoctorList.this, ""+idvalrecong, Toast.LENGTH_SHORT).show();
          Toast.makeText(DoctorList.this, ""+idvalrecongd, Toast.LENGTH_SHORT).show();
            //Log.d("idvalrecondotcor",idvalrecondoctor);

            if(hospital.matches("null")){
                gethosptialwithout(district,expertise);
                //Toast.makeText(DoctorList.this, "fdsf"+hospital, Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(DoctorList.this, "fdsf"+hospital, Toast.LENGTH_SHORT).show();

            //Log.d("valuetotget",district+"hospital"+hospital+"expertise"+expertise);

            if(!district.isEmpty() && !hospital.isEmpty() && !expertise.isEmpty()){
                //Toast.makeText(this, "none of null", Toast.LENGTH_SHORT).show();
                getAllIdValues(district,hospital,expertise);
            }

            else{
                Toast.makeText(this, "all null", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        
        idvalrecong = idvalrecondoctor;
        idvalrecong = idvalrecong;
        idvalrecong = idvalrecongd;

        //Toast.makeText(this, ""+idvalrecong, Toast.LENGTH_SHORT).show();

        if(id == android.R.id.home){

            if(idvalrecong.equals("1")){
                Intent intent = new Intent(DoctorList.this,DashboardActivity.class);
                intent.putExtra("hospital", hospital);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
            else if(idvalrecong.equals("2")){
                Intent intent = new Intent(DoctorList.this,HospitalSearchActivity.class);
                intent.putExtra("district", district);
                intent.putExtra("hospital", hospital);
                intent.putExtra("idvalueforrecongnize","3");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
            else if(idvalrecong.equals("3")){
                Intent intent = new Intent(DoctorList.this,HospitalSearchActivity.class);
                intent.putExtra("district", district);
                intent.putExtra("hospital", hospital);
                intent.putExtra("idvalueforrecongnized","3");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
            else if(idvalrecong.equals("4")){
                Intent intent = new Intent(DoctorList.this,DashboardActivity.class);
                intent.putExtra("hospital", hospital);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
            /*
            Intent intent = new Intent(DoctorList.this,HospitalSearchActivity.class);
            intent.putExtra("hospital", hospital);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            */
        }


        return super.onOptionsItemSelected(item);
    }

    private void gethosptialwithout(String district, final String expertise) {
        myRef.orderByChild("districtName").equalTo(String.valueOf(district)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    districtId = String.valueOf(childDataSnapshot.child("districtId").getValue());
                    setvalue(districtId,expertise);
                }

                //Toast.makeText(DoctorList.this, ""+districtId, Toast.LENGTH_SHORT).show();

                //Toast.makeText(context, ""+dataSnapshot, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setvalue(final String districtId, String expertise) {
        myRefExpertise.orderByChild("specialityName").equalTo(String.valueOf(expertise)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    specialityId = String.valueOf(childDataSnapshot.child("specialityId").getValue());
                    valuegetsetvalue(districtId,specialityId);
                    //Log.d("valuesspecialist",districtId+specialityId);
                }
                //Toast.makeText(context, ""+dataSnapshot, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void valuegetsetvalue(String districtId, String specialityId) {

        int districtid = Integer.parseInt(districtId);
        final int specialid = Integer.parseInt(specialityId);

        myRefDoctor.orderByChild("districtId").equalTo(districtid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

                    idval = childDataSnapshot.getKey();
                    //Toast.makeText(DoctorList.this, "snapshot"+id, Toast.LENGTH_SHORT).show();
                    //Log.d("iddatasnap",id);

                    ImageDoctor = String.valueOf(childDataSnapshot.child("image").getValue());
                    doctorName = String.valueOf(childDataSnapshot.child("name").getValue());
                    education = String.valueOf(childDataSnapshot.child("education").getValue());
                    specialistId = String.valueOf(childDataSnapshot.child("specialityId").getValue());
                    designation = String.valueOf(childDataSnapshot.child("designation").getValue());
                    hospitalsId = String.valueOf(childDataSnapshot.child("hospitalId").getValue());

                    int specialitymatch = Integer.parseInt(specialistId);
                    if(specialid == specialitymatch){
                        getvalue(idval,ImageDoctor,doctorName,education,specialistId,designation,hospitalsId);
                    }
                    else{
                        Toast.makeText(DoctorList.this, "Value not found..", Toast.LENGTH_SHORT).show();
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


    private void showProcessDialog() {
            progressDialog = new ProgressDialog(DoctorList.this);
            //progressDialog.setTitle("Getting Informations");
            progressDialog.setMessage("Please wait...");
            progressDialog.show();


    }


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
        Log.d("valuesexpertise",expertise);
        myRefExpertise.orderByChild("specialityName").equalTo(String.valueOf(expertise)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    specialityId = String.valueOf(childDataSnapshot.child("specialityId").getValue());
                    valueget(hospitalId,districtId,specialityId);
                    Log.d("valuesspecialist",hospitalId+districtId+specialityId);
                }
                //Toast.makeText(context, ""+dataSnapshot, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /* Search criteria 1*/
    private void valueget(final String hospitalId, String districtId, final String specialityId) {
        //Toast.makeText(this, "districtid"+districtId+"hospitalid"+hospitalId+"expertiseid"+specialityId, Toast.LENGTH_SHORT).show();

        int hosptialid = Integer.parseInt(hospitalId);
        final int specialid = Integer.parseInt(specialityId);

        myRefDoctor.orderByChild("hospitalId").equalTo(hosptialid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

                    idval = childDataSnapshot.getKey();
                    //Toast.makeText(DoctorList.this, "snapshot"+id, Toast.LENGTH_SHORT).show();
                    //Log.d("iddatasnap",id);

                    ImageDoctor = String.valueOf(childDataSnapshot.child("image").getValue());
                    doctorName = String.valueOf(childDataSnapshot.child("name").getValue());
                    education = String.valueOf(childDataSnapshot.child("education").getValue());
                    specialistId = String.valueOf(childDataSnapshot.child("specialityId").getValue());
                    designation = String.valueOf(childDataSnapshot.child("designation").getValue());
                    hospitalsId = String.valueOf(childDataSnapshot.child("hospitalId").getValue());

                    int specialitymatch = Integer.parseInt(specialistId);
                    if(specialid == specialitymatch){
                        getvalue(idval,ImageDoctor,doctorName,education,specialistId,designation,hospitalsId);
                    }
                    else{
                        Toast.makeText(DoctorList.this, "Value not found..", Toast.LENGTH_SHORT).show();
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

    private void getvalue(final String idval, final String ImageDoctor, final String doctorName, final String education, final String specialistId, final String designation, final String hospitalsId) {

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

    private void getvalues(final String idval,final String imageDoctor, final String doctorName, final String education, final String specialityName, final String designation, String hospitalsId) {
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

    private void setValueinList(String idval,String imageDoctor, String doctorName, String education, final String specialityName, String designation, String hospitalName) {
        //Toast.makeText(DoctorList.this, ""+doctorName+education+designation+specialityName+hospitalName, Toast.LENGTH_SHORT).show();

        doctorList.add(new DoctorInfo(idval,imageDoctor,doctorName,education,specialityName,designation,hospitalName));
        mAdapter = new DoctorListAdapter(DoctorList.this,doctorList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DoctorList.this);
        //recyclerView.addItemDecoration(
        //        new DividerItemDecoration(DoctorList.this, R.drawable.divider));

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        progressDialog.dismiss();


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(DoctorList.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
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

                        Intent intent = new Intent(DoctorList.this, DoctorDetailsActivity.class);
                        intent.putExtra("idvalue",idvalue);
                        intent.putExtra("name",name);
                        intent.putExtra("idvalueforrecongnize","1");
                        intent.putExtra("idvalueforrecongnized","3");
                        intent.putExtra("idvalueforrecongnizedoctor","4");
                        intent.putExtra("designationlocation", designatinlocation);

                        intent.putExtra("education",education);
                        intent.putExtra("speciality",specialtiy);

                        intent.putExtra("district", district);
                        intent.putExtra("hospital", hospital);
                        intent.putExtra("expertise", expertise);

                        startActivity(intent);

                        //Toast.makeText(getContext(), ""+district+""+hospitalName, Toast.LENGTH_SHORT).show();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }
    /* End of search criteria 1*/
}

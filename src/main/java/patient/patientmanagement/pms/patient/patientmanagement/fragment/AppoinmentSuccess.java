package patient.patientmanagement.pms.patient.patientmanagement.fragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import patient.patientmanagement.pms.AppoinmentBooking;
import patient.patientmanagement.pms.DashboardActivity;
import patient.patientmanagement.pms.DoctorList;
import patient.patientmanagement.pms.HospitalSearchActivity;
import patient.patientmanagement.pms.LoginActivity;
import patient.patientmanagement.pms.R;
import patient.patientmanagement.pms.SignupActivity;

public class AppoinmentSuccess extends AppCompatActivity {

    Button gotohome;
    private String photo,email,name,phone,gender,age,bloodgroup,password,created,uid,fromonlydistrict,fromdistandhos;

    ScrollView scrollview;
    String date,disease,doctorId,hospitalId,dates,time,serialNo,confirm,id,doctorlist;
    String description,speciality,education,district,hospital,expertise,ids,namevalue,idvalues;
    int District,DistrictAndHos,DistrictHosSpeciality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoinment_success);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            //idvalue = extras.getString("idvalue");

            date = extras.getString("format");
            disease = extras.getString("symptom");
            doctorId = extras.getString("id");
            hospitalId = extras.getString("hospitalId");
            dates = extras.getString("date");
            time = extras.getString("time");
            serialNo = extras.getString("serial");
            confirm = extras.getString("confirm");
            //id = Long.parseLong(extras.getString("appoinmentid"));
            id = extras.getString("appoinmentid");

            description = extras.getString("description");
            speciality = extras.getString("speciality");
            education = extras.getString("education");
            district = extras.getString("district");
            hospital = extras.getString("hospital");
            expertise = extras.getString("expertise");
            idvalues = extras.getString("idvalues");
            namevalue = extras.getString("name");

            fromonlydistrict = extras.getString("fromonlydistrict");
            fromdistandhos = extras.getString("fromonlydistrictandhosptial");
            doctorlist = extras.getString("doctorlist");

            District = extras.getInt("District");
            DistrictAndHos = extras.getInt("DistrictAndHos");
            DistrictHosSpeciality = extras.getInt("DistrictHosSpeciality");



            Log.d("extra",id);
            Log.d("value",date+"\n"+disease+"\n"+doctorId+"\n"+hospitalId+"\n"+id+"\n"+null+"\n"+serialNo+"\n"+1+"\n"+time);

            //Toast.makeText(this, ""+idvalue, Toast.LENGTH_SHORT).show();
        }

        gotohome = (Button) findViewById(R.id.btn_chg_password);
        gotohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppoinmentSuccess.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
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

        //Toast.makeText(this, ""+idvalrecong, Toast.LENGTH_SHORT).show();

        if(id == android.R.id.home){
            Intent intent = new Intent(AppoinmentSuccess.this,LoginActivity.class);
            intent.putExtra("format",date);
            intent.putExtra("symptom",disease);
            intent.putExtra("id",doctorId);
            intent.putExtra("hospitalId",hospitalId);
            intent.putExtra("date",dates);
            intent.putExtra("time",time);
            intent.putExtra("serial",serialNo);
            intent.putExtra("confirm",confirm);
            intent.putExtra("appoinmentid",String.valueOf(id));

            intent.putExtra("description",description);
            intent.putExtra("speciality",speciality);
            intent.putExtra("education",education);
            intent.putExtra("district",district);
            intent.putExtra("hospital",hospital);
            intent.putExtra("expertise",expertise);
            intent.putExtra("idvalues",idvalues);
            intent.putExtra("name",namevalue);
            intent.putExtra("fromonlydistrict",fromonlydistrict);
            intent.putExtra("fromonlydistrictandhosptial", fromdistandhos);
            intent.putExtra("doctorlist", doctorlist);

            intent.putExtra("District",District);
            intent.putExtra("DistrictAndHos",DistrictAndHos);
            intent.putExtra("DistrictHosSpeciality",DistrictHosSpeciality);

            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }
}



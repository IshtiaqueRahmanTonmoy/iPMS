package patient.patientmanagement.pms;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import patient.patientmanagement.pms.adapter.ConfirmAdapter;
import patient.patientmanagement.pms.adapter.ShadowTransformer;
import patient.patientmanagement.pms.entity.AvailableTIme;

public class AppoinmentBooking extends AppCompatActivity {

    ConfirmAdapter pagerAdapter;
    ShadowTransformer fragmentCardShadowTransformer;
    TextView location,datetxt,time,name,shortdescription,bookappoint;
    String currentime,newtime;
    String format,id,dateTime;
    String dates,month,year,monthformat,dayformat,serialNo,hosptialid,appoinmentTime,timeappoinment;
    Long formatdate;
    long appid = 0;
    String district,hospital,expertise,namevalue,ids;
    private ProgressDialog progressDialog;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRefHospital = database.getReference("hospitalInfo");
    private DatabaseReference myRefDoctor = database.getReference("doctorInfo");
    private DatabaseReference myRefAppSchedule = database.getReference("appoinmentSchedule");

    String fromonlydistrict,idvalrecondoctor,strDateFormat,strfullFormat,strDateFormats,names,description,speciality,education,idvalrecong,idvalrecongd;
    private String fromonlydistrictandhos;
    private String doctorlist;
    private Button rightarrow,leftarrow;

    int District,DistrictAndHos,DistrictHosSpeciality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoinment_booking);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Date date = new Date();
        Calendar clndr = Calendar.getInstance();
        dates = String.valueOf(clndr.get(Calendar.DATE));
        month = String.valueOf(clndr.get(Calendar.MONTH)+1);
        year = String.valueOf(clndr.get(Calendar.YEAR));

        if(dates.length() == 1){
            dayformat = "0"+dates;
        }
        else{
            dayformat = dates;
        }

        if(month.length() == 1){
            monthformat = "0"+month;
        }
        else{
            monthformat = month;
        }

        showProcessDialog();

        Date dNow = new Date( );
        SimpleDateFormat ft1 = new SimpleDateFormat ("dd-MMM-yyyy");
        //System.out.println("Date 2: " + ft1.format(dNow));
        appoinmentTime = ft1.format(dNow);
        format = year+"-"+monthformat+"-"+dayformat;
        //Log.d("sdf", String.valueOf(clndr.get(Calendar.DATE)));


        Log.d("format",format);
        //formatting time to have AM/PM text using 'a' format
        strDateFormat = "hh:mm a";
        strfullFormat = "HH:mm:ss";

        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        currentime = sdf.format(date);
        Log.d("sdf",sdf.format(date));

        location = (TextView) findViewById(R.id.location);
        datetxt = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        name = (TextView) findViewById(R.id.name);
        shortdescription = (TextView) findViewById(R.id.short_description);
        bookappoint = (TextView) findViewById(R.id.bookappoinment);

        leftarrow = (Button) findViewById(R.id.leftArrow);
        rightarrow = (Button) findViewById(R.id.rightArrow);

        SimpleDateFormat parseFormat = new SimpleDateFormat("E MMMM dd,yyyy");
        Date datev =new Date();
        String s = parseFormat.format(datev);
        bookappoint.setText(s);

        Bundle extras = getIntent().getExtras();


        if (extras != null) {

            description = extras.getString("description");
            speciality = extras.getString("speciality");
            education = extras.getString("education");

            district = extras.getString("district");
            hospital = extras.getString("hospital");
            expertise = extras.getString("expertise");
            id = extras.getString("idvalue");
            namevalue = extras.getString("name");

            fromonlydistrict = extras.getString("fromonlydistrict");
            fromonlydistrictandhos = extras.getString("fromonlydistrictandhosptial");
            doctorlist = extras.getString("doctorlist");

            District = extras.getInt("District");
            DistrictAndHos = extras.getInt("DistrictAndHos");
            DistrictHosSpeciality = extras.getInt("DistrictHosSpeciality");

            //Toast.makeText(AppoinmentBooking.this, "Districtappbooking"+District, Toast.LENGTH_SHORT).show();
            //Toast.makeText(AppoinmentBooking.this, "Hospitalappbooking"+DistrictAndHos, Toast.LENGTH_SHORT).show();
            //Toast.makeText(AppoinmentBooking.this, "Specialityappbooking"+DistrictHosSpeciality, Toast.LENGTH_SHORT).show();


            location.setText(namevalue);
            time.setText(currentime);
            datetxt.setText(format);

            getSupportActionBar().setTitle(namevalue);
            getSupportActionBar().setSubtitle(expertise);
            getSupportActionBar().setSubtitle(hospital);

            getvalue(id,namevalue,format);

            Log.d("idhosptial",fromonlydistrictandhos);

        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if(id == android.R.id.home){

            if(District == 1 && DistrictAndHos == 0 && DistrictHosSpeciality == 0) {
                Intent intent = new Intent(AppoinmentBooking.this,DoctorDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("idvalue", "ZXri36XsB6UYqdK9CLiqV32U6ml2");
                //intent.putExtra("idvalue", id);
                intent.putExtra("district", district);
                intent.putExtra("hospital", hospital);
                intent.putExtra("expertise", expertise);
                intent.putExtra("name",namevalue);
                intent.putExtra("education",education);
                intent.putExtra("description",description);
                intent.putExtra("speciality",speciality);
                intent.putExtra("fromonlydistrict",fromonlydistrict);
                intent.putExtra("fromonlydistrictandhosptial", fromonlydistrictandhos);
                intent.putExtra("doctorlist", doctorlist);
                intent.putExtra("designationlocation", speciality+","+hospital);

                intent.putExtra("District",District);
                intent.putExtra("DistrictAndHos",DistrictAndHos);
                intent.putExtra("DistrictHosSpeciality",DistrictHosSpeciality);

                startActivity(intent);
                finish();
            }

            if(District == 0 && DistrictAndHos == 0 && DistrictHosSpeciality == 1) {
                Intent intent = new Intent(AppoinmentBooking.this,DoctorDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("idvalue", "ZXri36XsB6UYqdK9CLiqV32U6ml2");
                //intent.putExtra("idvalue", id);
                intent.putExtra("district", district);
                intent.putExtra("hospital", hospital);
                intent.putExtra("expertise", expertise);
                intent.putExtra("name",namevalue);
                intent.putExtra("education",education);
                intent.putExtra("description",description);
                intent.putExtra("speciality",speciality);
                intent.putExtra("fromonlydistrict",fromonlydistrict);
                intent.putExtra("fromonlydistrictandhosptial", fromonlydistrictandhos);
                intent.putExtra("doctorlist", doctorlist);
                intent.putExtra("designationlocation", speciality+","+hospital);

                intent.putExtra("District",District);
                intent.putExtra("DistrictAndHos",DistrictAndHos);
                intent.putExtra("DistrictHosSpeciality",DistrictHosSpeciality);

                startActivity(intent);
                finish();
            }


        }


        return super.onOptionsItemSelected(item);
    }

    private void showProcessDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Getting Data");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
    }

    private void getvalue(final String id, final String location, final String format) {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("doctorInfo");
        ref.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String namevalue = String.valueOf(dataSnapshot.child("name").getValue());
                String hospitalid = String.valueOf(dataSnapshot.child("hospitalId").getValue());
                name.setText(namevalue);
                getvalueid(id,hospitalid,location,format);
                //Log.d("names",description);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getvalueid(final String id, String hospitalid, final String location, final String format) {

        //Toast.makeText(AppoinmentBooking.this, "hosptialid"+hospitalid, Toast.LENGTH_SHORT).show();
        int hospid = Integer.parseInt(hospitalid);
        myRefHospital.orderByChild("hospitalId").equalTo(hospid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    String hospitalAddress = String.valueOf(childDataSnapshot.child("hospitalName").getValue());
                    shortdescription.setText(hospitalAddress);
                    getvalues(id,location,format);
                    Log.d("address",hospitalAddress);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getvalues(final String id,final String location,final String format) {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("doctorInfo").child(id).child("chamber");
        ref.orderByChild("chambername").equalTo(String.valueOf(location)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    String day = String.valueOf(childDataSnapshot.child("chamberday").getValue());
                    String hospitalId = String.valueOf(childDataSnapshot.child("hospitalId").getValue());

                    Log.d("day",day);
                    Log.d("hospitalid",hospitalId);
                    matchcurrentDate(id,day,hospitalId,format);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void matchcurrentDate(final String id, String day, final String hospitalId, final String format) {

        Log.d("day",format);

        int hosid = Integer.parseInt(hospitalId);

        //Toast.makeText(AppoinmentBooking.this, "format"+format, Toast.LENGTH_SHORT).show();
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("appoinmentSchedule");

        ref.orderByChild("hospitalId").equalTo(hosid).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

                    dateTime = String.valueOf(childDataSnapshot.child("date").getValue());

                    //Toast.makeText(AppoinmentBooking.this, "datetime"+dateTime, Toast.LENGTH_SHORT).show();

                    if(dateTime.equals(format)){
                        //Toast.makeText(AppoinmentBooking.this, ""+dateTime, Toast.LENGTH_SHORT).show();
                        hosptialid  = String.valueOf(childDataSnapshot.child("hospitalId").getValue());
                        serialNo = String.valueOf(childDataSnapshot.child("serialNo").getValue());
                        timeappoinment = String.valueOf(childDataSnapshot.child("time").getValue());

                        appid = (long) childDataSnapshot.child("id").getValue();


                        SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
                        Date d = null;
                        try {
                            d = df.parse(timeappoinment);
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(d);
                            cal.add(Calendar.MINUTE, 30);
                            newtime = df.format(cal.getTime());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        int slno = Integer.parseInt(serialNo);
                        int serialno = slno + 1;
                        String sno = String.valueOf(serialno);

                        long aid = appid + 1;
                        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

                        /*
                        Log.d("descriptions",description);
                        Log.d("speciality",speciality);
                        Log.d("education",education);
                        Log.d("district",district);
                        Log.d("hospital",hospital);
                        Log.d("expertise",expertise);
                        Log.d("id",id);
                        Log.d("namevalue",namevalue);
                        Log.d("aid", String.valueOf(aid));

                        Log.d("hospitalid", hospitalId);
                        Log.d("serialno", String.valueOf(slno));
                        Log.d("newtime", newtime);
                        */
                        //String ids = String.valueOf(aid);

                        //Log.d("sno",sno);
                        pagerAdapter = new ConfirmAdapter(AppoinmentBooking.this,description,speciality,education,district,hospital,expertise,id,namevalue,fromonlydistrict,fromonlydistrictandhos,doctorlist,District,DistrictAndHos,DistrictHosSpeciality);
                        pagerAdapter.addCardItem(new AvailableTIme(format,"fever",id,hospitalId,aid,null,sno,"1",newtime,"Confirm Book"));
                        //pagerAdapter.addCardItem(new AvailableTIme(format,"fever",id,hospitalId,4,null,"10","1",newtime,"Confirm Book"));


                        fragmentCardShadowTransformer = new ShadowTransformer(viewPager, pagerAdapter);
                        fragmentCardShadowTransformer.enableScaling(true);


                        viewPager.setAdapter(pagerAdapter);
                        viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
                        viewPager.setOffscreenPageLimit(3);
                        //Log.d("diff", String.valueOf(serialno));
                        progressDialog.dismiss();
                    }

                    else{
                        //Toast.makeText(AppoinmentBooking.this, "not matched", Toast.LENGTH_SHORT).show();
                        hosptialid  = String.valueOf(childDataSnapshot.child("hospitalId").getValue());
                        serialNo = String.valueOf(childDataSnapshot.child("serialNo").getValue());
                        //timeappoinment = String.valueOf(childDataSnapshot.child("time").getValue());

                       // appid = (long) childDataSnapshot.child("id").getValue();

                        strDateFormats = "hh:mm a";
                        Date date = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormats);
                        String currtime = sdf.format(date);

                        int slno = Integer.parseInt(serialNo);
                        int serialno = 1;
                        String sno = String.valueOf(serialno);

                        long aid = appid + 1;

                        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

                        //String ids = String.valueOf(aid);

                        //Log.d("sno",sno);

                        pagerAdapter = new ConfirmAdapter(AppoinmentBooking.this, description, speciality, education, district, hospital, expertise, id, namevalue,fromonlydistrict,fromonlydistrictandhos,doctorlist, District, DistrictAndHos, DistrictHosSpeciality);
                        pagerAdapter.addCardItem(new AvailableTIme(format,"fever",id,hospitalId,aid,null,sno,"1",currtime,"Confirm Book"));

                        fragmentCardShadowTransformer = new ShadowTransformer(viewPager, pagerAdapter);
                        fragmentCardShadowTransformer.enableScaling(true);


                        viewPager.setAdapter(pagerAdapter);
                        viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
                        viewPager.setOffscreenPageLimit(3);
                        //Log.d("diff", String.valueOf(serialno));
                        progressDialog.dismiss();
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(AppoinmentBooking.this, "ds", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }
}

package patient.patientmanagement.pms;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Query;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import patient.patientmanagement.pms.adapter.ConfirmAdapter;
import patient.patientmanagement.pms.adapter.ShadowTransformer;
import patient.patientmanagement.pms.entity.AvailableTIme;
import patient.patientmanagement.pms.entity.Chamber;

public class AppoinmentBooking extends AppCompatActivity {

    ConfirmAdapter pagerAdapter;
    ShadowTransformer fragmentCardShadowTransformer;
    TextView location,datetxt,time,name,shortdescription;
    String currentime;
    String format,id,dateTime;
    String dates,month,year,monthformat,dayformat,serialNo,hosptialid,appoinmentTime;
    Long formatdate;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRefHospital = database.getReference("hospitalInfo");
    private DatabaseReference myRefDoctor = database.getReference("doctorInfo");
    private DatabaseReference myRefAppSchedule = database.getReference("appoinmentSchedule");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoinment_booking);

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

        Date dNow = new Date( );
        SimpleDateFormat ft1 = new SimpleDateFormat ("dd-MMM-yyyy");
        //System.out.println("Date 2: " + ft1.format(dNow));
        appoinmentTime = ft1.format(dNow);
        format = year+"-"+monthformat+"-"+dayformat;
        //Log.d("sdf", String.valueOf(clndr.get(Calendar.DATE)));


        Log.d("format",format);
        //formatting time to have AM/PM text using 'a' format
        String strDateFormat = "hh:mm a";
        String strfullFormat = "HH:mm:ss";

        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        currentime = sdf.format(date);
        Log.d("sdf",sdf.format(date));

        location = (TextView) findViewById(R.id.location);
        datetxt = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        name = (TextView) findViewById(R.id.name);
        shortdescription = (TextView) findViewById(R.id.short_description);

        Bundle extras = getIntent().getExtras();


        if (extras != null) {

            id = extras.getString("idvalue");
            String namevalue = extras.getString("name");
            location.setText(namevalue);
            time.setText(currentime);
            datetxt.setText(format);
            getvalue(id,namevalue);
            //Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        }



    }

    private void getvalue(final String id, final String location) {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("doctorInfo");
        ref.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String namevalue = String.valueOf(dataSnapshot.child("name").getValue());
                String hospitalid = String.valueOf(dataSnapshot.child("hospitalId").getValue());
                name.setText(namevalue);
                getvalueid(id,hospitalid,location);
                //Log.d("names",description);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getvalueid(final String id, String hospitalid, final String location) {

        int hospid = Integer.parseInt(hospitalid);
        myRefHospital.orderByChild("hospitalId").equalTo(hospid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    String hospitalAddress = String.valueOf(childDataSnapshot.child("hospitalName").getValue());
                    shortdescription.setText(hospitalAddress);
                    getvalues(id,location);
                    Log.d("address",hospitalAddress);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getvalues(final String id,final String location) {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("doctorInfo").child(id).child("chamber");
        ref.orderByChild("chambername").equalTo(String.valueOf(location)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    String day = String.valueOf(childDataSnapshot.child("chamberday").getValue());
                    String hospitalId = String.valueOf(childDataSnapshot.child("hospitalId").getValue());

                    Log.d("day",day);
                    Log.d("hospitalid",hospitalId);
                    matchcurrentDate(day,hospitalId);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void matchcurrentDate(String day, String hospitalId) {

        Log.d("day",day);

        int hosid = Integer.parseInt(hospitalId);
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("appoinmentSchedule");
        ref.orderByChild("date").equalTo(format).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    //dateTime = String.valueOf(childDataSnapshot.child("datetime").getValue());
                    hosptialid  = String.valueOf(childDataSnapshot.child("hospitalId").getValue());
                    serialNo = String.valueOf(childDataSnapshot.child("serialNo").getValue());

                    ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

                    pagerAdapter = new ConfirmAdapter(AppoinmentBooking.this);
                    pagerAdapter.addCardItem(new AvailableTIme(appoinmentTime,currentime,"Approximate SLNO - " + serialNo,"Confirm"));

                    fragmentCardShadowTransformer = new ShadowTransformer(viewPager, pagerAdapter);
                    fragmentCardShadowTransformer.enableScaling(true);


                    viewPager.setAdapter(pagerAdapter);
                    viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
                    viewPager.setOffscreenPageLimit(3);
                    Log.d("diff", String.valueOf(serialNo));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }
}

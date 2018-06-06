package patient.patientmanagement.pms;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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

import patient.patientmanagement.pms.adapter.ConfirmAdapter;
import patient.patientmanagement.pms.adapter.ShadowTransformer;
import patient.patientmanagement.pms.entity.AvailableTIme;
import patient.patientmanagement.pms.entity.Chamber;

public class AppoinmentBooking extends AppCompatActivity {

    ConfirmAdapter pagerAdapter;
    ShadowTransformer fragmentCardShadowTransformer;
    TextView location,datetxt,time,name,shortdescription;
    String currentime;
    String dates,month,year,format,id;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRefHospital = database.getReference("hospitalInfo");
    private DatabaseReference myRefDoctor = database.getReference("doctorInfo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoinment_booking);

        Date date = new Date();
        Calendar clndr = Calendar.getInstance();
        dates = String.valueOf(clndr.get(Calendar.DATE));
        month = String.valueOf(clndr.get(Calendar.MONTH)+1);
        year = String.valueOf(clndr.get(Calendar.YEAR));
        format = year+"-"+month+"-"+dates;
        Log.d("sdf", String.valueOf(clndr.get(Calendar.DATE)));


        //formatting time to have AM/PM text using 'a' format
        String strDateFormat = "hh:mm a";
        String strfullFormat = "HH:mm:ss";

        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        currentime = sdf.format(date);
        Log.d("sdf",sdf.format(date));


        Timestamp stamp = new Timestamp(System.currentTimeMillis());
        Date dates = new Date(stamp.getTime());
        //Toast.makeText(this, ""+dates, Toast.LENGTH_SHORT).show();

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


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        pagerAdapter = new ConfirmAdapter(AppoinmentBooking.this);
        pagerAdapter.addCardItem(new AvailableTIme("Jun 05,2018","03:00 PM","Approximate SLNO-3","Confirm"));

        fragmentCardShadowTransformer = new ShadowTransformer(viewPager, pagerAdapter);
        fragmentCardShadowTransformer.enableScaling(true);


        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);
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

    private void getvalueid(String id, String hospitalid, final String location) {

        int hospid = Integer.parseInt(hospitalid);
        myRefHospital.orderByChild("hospitalId").equalTo(hospid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    String hospitalAddress = String.valueOf(childDataSnapshot.child("hospitalName").getValue());
                    shortdescription.setText(hospitalAddress);
                    Log.d("address",hospitalAddress);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("doctorInfo");
        ref.child(id).child("chamber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("datasnapshot", String.valueOf(dataSnapshot));
                myRefDoctor.orderByChild("chambername").equalTo(location).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                            String chamberday = String.valueOf(childDataSnapshot.child("chamberday").getValue());
                            //shortdescription.setText(hospitalAddress);
                            Log.d("chamber",chamberday);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

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

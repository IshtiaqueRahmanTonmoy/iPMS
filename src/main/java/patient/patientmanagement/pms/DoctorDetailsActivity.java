package patient.patientmanagement.pms;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.FirebaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import patient.patientmanagement.pms.adapter.CardPagerAdapter;
import patient.patientmanagement.pms.adapter.ChamberAdapter;
import patient.patientmanagement.pms.adapter.ShadowTransformer;
import patient.patientmanagement.pms.entity.CardItem;
import patient.patientmanagement.pms.entity.Chamber;

public class DoctorDetailsActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    private ChamberAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private TextView nameTxt, shortDescriptionTxt, educationTxt, specialityTxt;
    private TextView mTextMessage;
    private String idvalue, name, shortdescription, education, speciality;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("doctorInfo");


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        nameTxt = (TextView) findViewById(R.id.name);
        shortDescriptionTxt = (TextView) findViewById(R.id.short_description);
        educationTxt = (TextView) findViewById(R.id.education);
        specialityTxt = (TextView) findViewById(R.id.speciality);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            idvalue = extras.getString("idvalue");
            getvalue(idvalue);
            //Toast.makeText(this, ""+idvalue, Toast.LENGTH_SHORT).show();
            name = extras.getString("name");
            shortdescription = extras.getString("designationlocation");

            education = extras.getString("education");
            speciality = extras.getString("speciality");

            nameTxt.setText(name);
            shortDescriptionTxt.setText(shortdescription);

            educationTxt.setText(education);
            specialityTxt.setText(speciality);

        }

        mViewPager = (ViewPager) findViewById(R.id.viewPager);


        mCardAdapter = new ChamberAdapter();
        mCardAdapter.addCardItem(new Chamber("Syed Diagonstics & Consultation Center,Main Branch", "Ka 164/2(Ground Floor),Bottola,Khilagaon,Dhaka", "BOOK APPOINMENT NOW"));
        mCardAdapter.addCardItem(new Chamber("Syed Diagonstics & Consultation Center,Main Branch", "Ka 164/2(Ground Floor),Bottola,Khilagaon,Dhaka", "BOOK APPOINMENT NOW"));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);


        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void getvalue(String idvalue) {
        myRef.child(idvalue).child("chamber").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("datasnpahsopt", String.valueOf(dataSnapshot.getValue()));
            }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }
}
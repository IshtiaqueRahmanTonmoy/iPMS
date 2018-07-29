package patient.patientmanagement.pms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import patient.patientmanagement.pms.adapter.Pager;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.Tab3Fragment;

public class HospitalSearchActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private String mapvalue,district,hospitalName,fromonlydistrict,redirect;
    Intent intent;
    String hosptial,idvalrecong,idvalrecongd,hospitalactivity,fromonlydistrictandhos;
    int District,DistrictAndHos,DistrictHosSpeciality;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRefHospital = database.getReference("hospitalInfo");
    private String hospitalPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_search);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            district = extras.getString("district");
            hospitalName = extras.getString("hospital");
            fromonlydistrict = extras.getString("fromonlydistrict");
            fromonlydistrictandhos = extras.getString("fromonlydistrictandhosptial");


            District = extras.getInt("District");
            DistrictAndHos = extras.getInt("DistrictAndHos");
            DistrictHosSpeciality = extras.getInt("DistrictHosSpeciality");


            myRefHospital.orderByChild("hospitalName").equalTo(String.valueOf(hospitalName)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        hospitalPhone = String.valueOf(childDataSnapshot.child("phone").getValue());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            getSupportActionBar().setTitle(district);
            getSupportActionBar().setSubtitle(hospitalName);
            //getSupportActionBar().setTitle(district);

            //Toast.makeText(this, ""+fromonlydistrict, Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, ""+fromonlydistrictandhos, Toast.LENGTH_SHORT).show();

            Log.d("hospitalName",hospitalName+district);
        }

        //tab id
        mTabLayout=(TabLayout)findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        //add the tabs
        mTabLayout.addTab(mTabLayout.newTab().setText("Info"));
        //mTabLayout.addTab(mTabLayout.newTab().setText("Doctors"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Location Map"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);

        Pager adapter=new Pager(getSupportFragmentManager(), mTabLayout.getTabCount(),hospitalName,fromonlydistrict,fromonlydistrictandhos);

        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setScrollPosition(position, 0, true);
                mTabLayout.setSelected(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
                builder1.setMessage("Want to call this hospital ??.");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String phoneNumber = String.format("tel: %s", hospitalPhone);
                                // Create the intent.
                                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                                // Set the data for the intent as the phone number.
                                dialIntent.setData(Uri.parse(phoneNumber));
                                // If package resolves to an app, send intent.
                                if (dialIntent.resolveActivity(HospitalSearchActivity.this.getPackageManager()) != null) {
                                    HospitalSearchActivity.this.startActivity(dialIntent);
                                    dialog.cancel();
                                } else {
                                    Log.e("intent", "Can't resolve app for ACTION_DIAL Intent.");
                                }
                            }


                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
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

        //redirect = fromonlydistrict;
        //redirect = fromonlydistrictandhos;
        int id = item.getItemId();

        if(id == android.R.id.home){

            if(District == 1 && DistrictAndHos == 0 && DistrictHosSpeciality == 0) {
                Intent intent = new Intent(HospitalSearchActivity.this,HospitalActivity.class);
                intent.putExtra("district",district);
                intent.putExtra("hospital",hospitalName);
                intent.putExtra("fromonlydistrict",fromonlydistrict);

                intent.putExtra("District",District);
                intent.putExtra("DistrictAndHos",DistrictAndHos);
                intent.putExtra("DistrictHosSpeciality",DistrictHosSpeciality);
                startActivity(intent);
            }
            else if(District == 0 && DistrictAndHos == 1 && DistrictHosSpeciality == 0){
                Intent intent = new Intent(HospitalSearchActivity.this,DashboardActivity.class);
                intent.putExtra("district",district);
                intent.putExtra("hospital",hospitalName);
                intent.putExtra("fromonlydistrictandhosptial",fromonlydistrictandhos);
                startActivity(intent);
            }
            /*
            if(fromonlydistrict.equals("1") && fromonlydistrictandhos.equals("null")){
                Intent intent = new Intent(HospitalSearchActivity.this,HospitalActivity.class);
                intent.putExtra("district",district);
                intent.putExtra("hospital",hospitalName);
                intent.putExtra("fromonlydistrict",fromonlydistrict);
                startActivity(intent);
            }
            else if(fromonlydistrictandhos.equals("2") && fromonlydistrict.equals("null")){
                Intent intent = new Intent(HospitalSearchActivity.this,DashboardActivity.class);
                intent.putExtra("district",district);
                intent.putExtra("hospital",hospitalName);
                intent.putExtra("fromonlydistrictandhosptial",fromonlydistrictandhos);
                startActivity(intent);
            }
            */

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent("custom-event-name");
        // You can also include some extra data.
        intent.putExtra("hospitalName", hosptial);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}

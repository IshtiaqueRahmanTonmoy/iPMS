package patient.patientmanagement.pms;

import android.content.Intent;
import android.content.SharedPreferences;
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

import patient.patientmanagement.pms.adapter.Pager;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.Tab3Fragment;

public class HospitalSearchActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private String mapvalue,district,hospitalName;
    Intent intent;
    String hosptial,idvalrecong,idvalrecongd;

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
            idvalrecong = extras.getString("idvalueforrecongnize");
            idvalrecongd = extras.getString("idvalueforrecongnized");

            district = extras.getString("district");
            hospitalName = extras.getString("hospital");

            //getSupportActionBar().setTitle(district);
            //getSupportActionBar().setSubtitle(hospitalName);
            //getSupportActionBar().setTitle(district);

            //Toast.makeText(this, ""+idvalrecong, Toast.LENGTH_SHORT).show();

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

        Pager adapter=new Pager(getSupportFragmentManager(), mTabLayout.getTabCount(),hospitalName);

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

        if(id == android.R.id.home){

            /*
                if(idvalrecongd.equals("3")){
                    Intent intent = new Intent(HospitalSearchActivity.this, DashboardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                }
                */
                if(idvalrecong.equals("2")){
                    Intent intent = new Intent(HospitalSearchActivity.this, HospitalActivity.class);
                    intent.putExtra("district", district);
                    //intent.putExtra("district",district);
                    intent.putExtra("hospital", hospitalName);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                }

                else {
                    Intent intent = new Intent(HospitalSearchActivity.this, DashboardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                }
                /*
                else{
                    Intent intent = new Intent(HospitalSearchActivity.this, HospitalActivity.class);
                    intent.putExtra("district", district);
                    //intent.putExtra("district",district);
                    intent.putExtra("hospital", hospitalName);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                }
                */

            /*
            LocalBroadcastManager.getInstance(HospitalSearchActivity.this).sendBroadcast(intent);
            intent = new Intent("BROADCAST_RANDOM_NUMBER");
            // Put the random number to intent to broadcast it
            intent.putExtra("RandomNumber",hospitalName);
            */

            //startActivity(intent);
            finish();

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

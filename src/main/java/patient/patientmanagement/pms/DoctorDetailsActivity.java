package patient.patientmanagement.pms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import patient.patientmanagement.pms.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import patient.patientmanagement.pms.adapter.ChamberAdapter;
import patient.patientmanagement.pms.adapter.ShadowTransformer;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.AboutFragment;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.AppoinmentFragment;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.MapFragment;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.RelatedFragment;

public class DoctorDetailsActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    private ChamberAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private TextView nameTxt, shortDescriptionTxt, educationTxt, specialityTxt;
    private TextView mTextMessage;
    String  fromonlydistrict,fromvalue,designation;
    private String idvalue,fromonlydistrictandhos,name, shortdescription, education,idvalrecong,idvalrecongd, idvalrecondoctor,speciality,district,hospital,expertise;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("doctorInfo");
    private String doctorlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("name");
            designation = extras.getString("designationlocation");
            district = extras.getString("district");
            hospital = extras.getString("hospital");
            expertise = extras.getString("expertise");
            fromonlydistrict = extras.getString("fromonlydistrict");
            fromonlydistrictandhos = extras.getString("fromonlydistrictandhosptial");
            doctorlist = extras.getString("doctorlist");

            getSupportActionBar().setTitle(name);
            getSupportActionBar().setSubtitle(designation);

            //Toast.makeText(DoctorDetailsActivity.this, "district"+fromonlydistrict, Toast.LENGTH_SHORT).show();
            //Toast.makeText(DoctorDetailsActivity.this, "fromonlydistrict"+fromonlydistrictandhos, Toast.LENGTH_SHORT).show();
            //Toast.makeText(DoctorDetailsActivity.this, "doctorlist"+doctorlist, Toast.LENGTH_SHORT).show();

        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                                case R.id.navigation_about:
                                    Bundle bundle = new Bundle();
                                    bundle.putString("district", district);
                                    bundle.putString("hosptial", hospital);
                                    bundle.putString("expertise", expertise);
                                    bundle.putString("idvalrecongd",idvalrecongd);

                                    bundle.putString("fromonlydistrict",fromonlydistrict);
                                    bundle.putString("fromonlydistrictandhos",fromonlydistrictandhos);
                                    bundle.putString("doctorlist",doctorlist);

                                    selectedFragment = AboutFragment.newInstance();
                                    AboutFragment fragobj = new AboutFragment();
                                    fragobj.setArguments(bundle);
                                break;

                                case R.id.navigation_map:
                                selectedFragment = MapFragment.newInstance();
                                break;

                            case R.id.navigation_related:
                                selectedFragment = RelatedFragment.newInstance();
                                break;
                        }

                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;

                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, AboutFragment.newInstance());
        transaction.commit();

        //Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);

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
            if(fromonlydistrict.equals("1")){
                Intent intent = new Intent(DoctorDetailsActivity.this,DoctorList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("district", district);
                intent.putExtra("hospital", hospital);
                intent.putExtra("expertise", expertise);
                intent.putExtra("fromonlydistrict",fromonlydistrict);
                intent.putExtra("fromonlydistrictandhosptial", "null");
                intent.putExtra("doctorlist", "null");
                startActivity(intent);
                finish();
            }
            else if(fromonlydistrictandhos.equals("2")){
                Intent intent = new Intent(DoctorDetailsActivity.this,DoctorList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("district", district);
                intent.putExtra("hospital", hospital);
                intent.putExtra("expertise", expertise);
                intent.putExtra("fromonlydistrict","null");
                intent.putExtra("fromonlydistrictandhosptial", fromonlydistrictandhos);
                intent.putExtra("doctorlist", "null");
                startActivity(intent);
                finish();
            }
            else if(doctorlist.equals("3")){
                Intent intent = new Intent(DoctorDetailsActivity.this,DoctorList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("district", district);
                intent.putExtra("hospital", hospital);
                intent.putExtra("expertise", expertise);
                intent.putExtra("fromonlydistrict","null");
                intent.putExtra("fromonlydistrictandhosptial", "null");
                intent.putExtra("doctorlist", doctorlist);
                startActivity(intent);
                finish();
            }

        }


        return super.onOptionsItemSelected(item);
    }

}
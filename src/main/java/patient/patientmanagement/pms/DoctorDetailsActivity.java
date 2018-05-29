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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import patient.patientmanagement.pms.patient.patientmanagement.fragment.AboutFragment;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.AppoinmentFragment;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.MapFragment;

public class DoctorDetailsActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    private ChamberAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private TextView nameTxt, shortDescriptionTxt, educationTxt, specialityTxt;
    private TextView mTextMessage;
    private String idvalue, name, shortdescription, education, speciality;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("doctorInfo");



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

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.navigation_about:
                                selectedFragment = AboutFragment.newInstance();
                                break;
                            case R.id.navigation_appoinment:
                                selectedFragment = AppoinmentFragment.newInstance();
                                break;
                            case R.id.navigation_map:
                                selectedFragment = MapFragment.newInstance();
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
}
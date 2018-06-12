package patient.patientmanagement.pms;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;

import patient.patientmanagement.pms.adapter.Pager;
import patient.patientmanagement.pms.adapter.PagerBlood;

public class BloodActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private String bloodgroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //tab id
        mTabLayout=(TabLayout)findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);

        //add the tabs
        mTabLayout.addTab(mTabLayout.newTab().setText("A+"));
        mTabLayout.addTab(mTabLayout.newTab().setText("A-"));
        mTabLayout.addTab(mTabLayout.newTab().setText("B+"));
        mTabLayout.addTab(mTabLayout.newTab().setText("B-"));
        mTabLayout.addTab(mTabLayout.newTab().setText("AB+"));
        mTabLayout.addTab(mTabLayout.newTab().setText("AB-"));
        mTabLayout.addTab(mTabLayout.newTab().setText("O+"));
        mTabLayout.addTab(mTabLayout.newTab().setText("O-"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);



        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        PagerBlood adapter=new PagerBlood(getSupportFragmentManager(), mTabLayout.getTabCount());

        mViewPager.setAdapter(adapter);



        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            bloodgroup = extras.getString("bloodgroup");
            if(bloodgroup.equals("A+")){
                TabLayout.Tab tab = mTabLayout.getTabAt(0);
                tab.select();
            }
            else if(bloodgroup.equals("A-")){
                TabLayout.Tab tab = mTabLayout.getTabAt(1);
                tab.select();
            }
            else if(bloodgroup.equals("B+")){
                TabLayout.Tab tab = mTabLayout.getTabAt(2);
                tab.select();
            }
            else if(bloodgroup.equals("B-")){
                TabLayout.Tab tab = mTabLayout.getTabAt(3);
                tab.select();
            }
            else if(bloodgroup.equals("AB+")){
                TabLayout.Tab tab = mTabLayout.getTabAt(4);
                tab.select();
            }
            else if(bloodgroup.equals("AB-")){
                TabLayout.Tab tab = mTabLayout.getTabAt(5);
                tab.select();
            }
            else if(bloodgroup.equals("O+")){
                TabLayout.Tab tab = mTabLayout.getTabAt(6);
                tab.select();
            }
            else if(bloodgroup.equals("O-")){
                TabLayout.Tab tab = mTabLayout.getTabAt(7);
                tab.select();
            }

        }

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

    }
}

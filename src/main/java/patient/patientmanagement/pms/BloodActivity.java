package patient.patientmanagement.pms;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import patient.patientmanagement.pms.adapter.Pager;
import patient.patientmanagement.pms.adapter.PagerBlood;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.OpositiveFragment;

public class BloodActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private String bloodgroup;
    private SearchView searchView;

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

        getSupportActionBar().setTitle("Search Blood");

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

        //Toast.makeText(BloodActivity.this, ""+mTabLayout.getSelectedTabPosition(), Toast.LENGTH_SHORT).show();


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        PagerBlood adapter=new PagerBlood(getSupportFragmentManager(), mTabLayout.getTabCount());

        mViewPager.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            bloodgroup = extras.getString("bloodgroup");

            //Toast.makeText(this, ""+bloodgroup, Toast.LENGTH_SHORT).show();

            if(bloodgroup.equals("A+")){
                TabLayout.Tab tab = mTabLayout.getTabAt(0);
                switchtotab(0);
                tab.select();

            }
            if(bloodgroup.equals("A-")){
                TabLayout.Tab tab = mTabLayout.getTabAt(1);
                switchtotab(1);
                tab.select();
            }
            if(bloodgroup.equals("B+")){
                TabLayout.Tab tab = mTabLayout.getTabAt(2);
                switchtotab(2);
                tab.select();
            }
            if(bloodgroup.equals("B-")){
                TabLayout.Tab tab = mTabLayout.getTabAt(3);
                switchtotab(3);
                tab.select();
            }
            if(bloodgroup.equals("AB+")){
                TabLayout.Tab tab = mTabLayout.getTabAt(4);
                switchtotab(4);
                tab.select();
            }
            if(bloodgroup.equals("AB-")){
                TabLayout.Tab tab = mTabLayout.getTabAt(5);
                switchtotab(5);
                tab.select();
            }
            if(bloodgroup.equals("O+")){
                TabLayout.Tab tab = mTabLayout.getTabAt(6);
                switchtotab(6);
                tab.select();
            }
            if(bloodgroup.equals("O-")){
                TabLayout.Tab tab = mTabLayout.getTabAt(7);
                switchtotab(7);
                tab.select();
            }

        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                //Toast.makeText(BloodActivity.this, ""+position, Toast.LENGTH_SHORT).show();
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
                //Toast.makeText(BloodActivity.this, ""+tab.getPosition(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void switchtotab(int i) {
        mViewPager.setCurrentItem(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.hospitalsearch, menu);

        final MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setQueryHint("Search by doctorname/speciality/designation");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast like print
                myActionMenuItem.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                //Toast.makeText(HospitalActivity.this, ""+s, Toast.LENGTH_SHORT).show();
                //PagerBlood page = new PagerBlood(getSupportFragmentManager(),s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if(id == android.R.id.home){
            Intent intent = new Intent(BloodActivity.this,DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

}

package patient.patientmanagement.pms;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.drm.DrmStore;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Dash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import patient.patientmanagement.pms.adapter.BloodListAdapter;
import patient.patientmanagement.pms.adapter.CardPagerAdapter;
import patient.patientmanagement.pms.adapter.CardPagerAdapterBlood;
import patient.patientmanagement.pms.adapter.ShadowTransformer;
import patient.patientmanagement.pms.adapter.ViewPagerAdapter;
import patient.patientmanagement.pms.entity.CardItem;
import patient.patientmanagement.pms.entity.Item;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.AboutActivity;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean doubleBackToExitPressedOnce = false;
    private RecyclerView recyclerView;
    private List<Item> data;
    private String[] names = {"Hospital,Blood,HealthNews,HealthTipsActivity"};
    private int flags[] = {R.mipmap.hospital,R.mipmap.blood,R.mipmap.healthnews,R.mipmap.healthtips};
    private HorizontalAdapter horizontalAdapter;
    private ConstraintLayout constraintLayout;
    private DatabaseReference mDatabase;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private ViewPager mViewPager;
    private ProgressDialog progressDialog;
    private CardPagerAdapter mCardAdapter;
    private CardPagerAdapterBlood mCardAdapterBlood;
    private ShadowTransformer mCardShadowTransformer;
    private DrawerLayout drawerlayout;
    List<CardItem> dataModels;
    DrawerLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("i-PMS");


        checkConnection();

        //DashboardActivity.PhoneCallListener phoneListener = new Da.PhoneCallListener();
        //TelephonyManager telephonyManager = (TelephonyManager) DashboardActivity.this.getSystemService(Context.TELEPHONY_SERVICE);
        //telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);


        dataModels= new ArrayList<CardItem>();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mViewPager = (ViewPager) findViewById(R.id.viewpagerDashboard);

        //adapter = new CustomAdapter(dataModels);
        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem(R.string.title_1, R.string.text_1));
        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("district");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                            //String name = (String) messageSnapshot.child("name").getValue();
                            String districtName = (String) messageSnapshot.child("districtName").getValue();

                            dataModels.add(new CardItem(districtName));

                            //adapter = new CustomAdapter(dataModels);
                            mCardAdapter = new CardPagerAdapter();
                            mCardAdapter.addCardItem(new CardItem(R.string.title_1, R.string.text_1));
                            mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);

                            mViewPager.setAdapter(mCardAdapter);
                            mViewPager.setPageTransformer(false, mCardShadowTransformer);
                            mViewPager.setOffscreenPageLimit(3);
                            //progressDialog.dismiss();

                            //dataModels.add(new CardItem(districtName));
                            //adapter = new CustomAdapter(DashboardActivity.this,dataModels);
                            //Log.d("message",districtName);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        /*

        */


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar snackbar = Snackbar
                        .make(drawerlayout, "Feature coming soon..", Snackbar.LENGTH_LONG);

                snackbar.show();
                /*
                //String phone = blood.getPhoneNumber().toString();
                //Toast.makeText(context, "" + phone, Toast.LENGTH_SHORT).show();
                String phoneNumber = String.format("tel: %s", "1212");
                // Create the intent.
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                // Set the data for the intent as the phone number.
                dialIntent.setData(Uri.parse(phoneNumber));
                // If package resolves to an app, send intent.
                if (dialIntent.resolveActivity(DashboardActivity.this.getPackageManager()) != null) {
                    DashboardActivity.this.startActivity(dialIntent);
                } else {
                    Log.e("intent", "Can't resolve app for ACTION_DIAL Intent.");
                }
                */
            }

        });



        viewPager = (ViewPager) findViewById(R.id.viewPager);

        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        data = fill_with_data();

        constraintLayout = (ConstraintLayout) findViewById(R.id.parent_constraint_layout);

        horizontalAdapter = new HorizontalAdapter(data, getApplication());

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setAdapter(horizontalAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(position == 0){
                    mCardAdapter = new CardPagerAdapter(DashboardActivity.this);
                    mCardAdapter.addCardItem(new CardItem(R.string.title_1, R.string.text_1));
                    mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);

                    mViewPager.setAdapter(mCardAdapter);
                    mViewPager.setPageTransformer(false, mCardShadowTransformer);
                    mViewPager.setOffscreenPageLimit(3);

                }

                if(position == 1){
                    mCardAdapterBlood = new CardPagerAdapterBlood(DashboardActivity.this);
                    mCardAdapterBlood.addCardItem(new CardItem(R.string.title_1, R.string.text_1));
                    mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapterBlood);

                    mViewPager.setAdapter(mCardAdapterBlood);
                    mViewPager.setPageTransformer(false, mCardShadowTransformer);
                    mViewPager.setOffscreenPageLimit(3);
                }

                if(position == 2){
                    Intent intent = new Intent(DashboardActivity.this,HealthNewsActivity.class);
                    startActivity(intent);
                }

                if(position == 3){
                    Intent intent = new Intent(DashboardActivity.this,HealthTipsActivity.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onLongClick(View view, int position) { }
        }));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void checkConnection() {
        if(isOnline()){
            showProcessDialog();
            progressDialog.dismiss();
        }else{

            /*
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(DashboardActivity.this).create();

                alertDialog.setTitle("Info");
                alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });

                alertDialog.show();
            } catch (Exception e) {
                //Log.d(Constants.TAG, "Show Dialog: " + e.getMessage());
            }
            */

            Snackbar snackbar = Snackbar
                    .make(drawerlayout, "No internet connection!", Snackbar.LENGTH_LONG).setDuration(30000)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showProcessDialog();

                            //WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                            //wifi.setWifiEnabled(true);

                            //startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);

                            mCardAdapter = new CardPagerAdapter(DashboardActivity.this);
                            mCardAdapter.addCardItem(new CardItem(R.string.title_1, R.string.text_1));
                            mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);

                            mViewPager.setAdapter(mCardAdapter);
                            mViewPager.setPageTransformer(false, mCardShadowTransformer);
                            mViewPager.setOffscreenPageLimit(3);
                            progressDialog.dismiss();
                        }
                    });

            // Changing message text color
            snackbar.setActionTextColor(Color.RED);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);

            snackbar.show();
        }
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private void showProcessDialog() {
        progressDialog = new ProgressDialog(DashboardActivity.this);
        //progressDialog.setTitle("Getting Informations");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

    }

    private List<Item> fill_with_data() {
        List<Item> data = new ArrayList<>();

        data.add(new Item( R.mipmap.hospital, "Hospital"));
        data.add(new Item( R.mipmap.blood, "Blood"));
        data.add(new Item( R.mipmap.healthnews, "HealthNews"));
        data.add(new Item( R.mipmap.healthtips, "HealthTips"));
        return data;
    }

    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //if (drawer.isDrawerOpen(GravityCompat.START)) {
        //    drawer.closeDrawer(GravityCompat.START);
        //} else {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
        //}
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.ask_doctor) {
            // Handle the camera action
        } else if (id == R.id.nav_bloodregister) {
            Intent intent = new Intent(DashboardActivity.this,BloodDonorActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_faq) {

        } else if (id == R.id.nav_appointment) {

        } else if (id == R.id.nav_blog) {

        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(DashboardActivity.this,AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_privacypolicy) {

        }else if (id == R.id.nav_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareboyd = "";
            String shareboyd1 = "Get Patient Management Application From link below";
            String sharesub = "https://play.google.com/store/apps/details?id=patientsmanagement.patientmanagement.patientsmanagementsystem";
            intent.putExtra(Intent.EXTRA_SUBJECT,shareboyd);
            intent.putExtra(Intent.EXTRA_SUBJECT,shareboyd1);
            intent.putExtra(Intent.EXTRA_TEXT,sharesub);
            startActivity(Intent.createChooser(intent,"Share using"));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {


        List<Item> horizontalList = Collections.emptyList();
        Context context;
        View animatedView = null;


        public HorizontalAdapter(List<Item> horizontalList, Context context) {
            this.horizontalList = horizontalList;
            this.context = context;
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView txtview;

            public MyViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.imageview);
                txtview = (TextView) view.findViewById(R.id.txtview);
            }
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // You can tweak with the effects here
                    if (animatedView == null) {
                        animatedView = itemView;
                    } else {
                        animatedView.setAnimation(null);
                        animatedView = itemView;
                    }
                    ScaleAnimation fade_in = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.2f, Animation.RELATIVE_TO_SELF, 0.2f);
                    fade_in.setDuration(100);     // animation duration in milliseconds
                    fade_in.setFillAfter(true);    // If fillAfter is true, the transformation that this animation performed will persist when it is finished.
                    itemView.startAnimation(fade_in);
                }
            });
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.imageView.setImageResource(horizontalList.get(position).imageId);
            holder.txtview.setText(horizontalList.get(position).txt);

            /*
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    category = horizontalList.get(position).txt.toString();
                    Toast.makeText(TransactionsActivity.this, category, Toast.LENGTH_SHORT).show();
                }
            });
            */

        }


        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }

    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");

                    // restart app
                    Intent i = DashboardActivity.this.getApplicationContext().getPackageManager()
                            .getLaunchIntentForPackage(
                                    DashboardActivity.this.getApplicationContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    DashboardActivity.this.startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }
    }
}

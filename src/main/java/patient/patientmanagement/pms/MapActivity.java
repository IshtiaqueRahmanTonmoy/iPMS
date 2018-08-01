package patient.patientmanagement.pms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import patient.patientmanagement.pms.entity.VolleyCallback;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    LocationManager locationManager ;
    String provider;
    GoogleMap gMap;
    double latitude,longitude;
    LatLng p1 = null;
    String location;
    private String hospitalName,hospitalAddress,districtName;
    private DatabaseReference myRefHospital = database.getReference("hospitalInfo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("i-PMS");

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            districtName = extras.getString("district");

        }

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if(id == android.R.id.home){
            Intent intent = new Intent(MapActivity.this,HospitalActivity.class);
            intent.putExtra("district",districtName);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        Bundle extras = MapActivity.this.getIntent().getExtras();
        if (extras != null) {
            hospitalName = extras.getString("hospital");

            //Toast.makeText(getActivity(), ""+hospitalName, Toast.LENGTH_SHORT).show();
            myRefHospital.orderByChild("hospitalName").equalTo(String.valueOf(hospitalName)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        hospitalAddress = String.valueOf(childDataSnapshot.child("hospitalAddress").getValue());
                        Geocoder coder = new Geocoder(MapActivity.this);
                        List<Address> address;
                        try {
                            // May throw an IOException
                            address = coder.getFromLocationName(hospitalAddress, 5);
                            Address locations = address.get(0);
                            latitude = locations.getLatitude();
                            longitude = locations.getLongitude();
                            p1 = new LatLng(latitude, longitude);

                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(latitude,longitude))
                                    .title("LinkedIn")
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));


                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 18));

                            Log.d("latitude", String.valueOf(latitude));
                            Log.d("longitude", String.valueOf(longitude));

                            //Toast.makeText(MapActivity.this, "latitude" + latitude + "longitude" + longitude, Toast.LENGTH_SHORT).show();


                        } catch (IOException ex) {

                            ex.printStackTrace();
                        }


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
package patient.patientmanagement.pms;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

public class MapActivity extends AppCompatActivity implements LocationListener, OnMapReadyCallback {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    LocationManager locationManager ;
    String provider;
    GoogleMap gMap;
    double latitude,longitude;
    LatLng p1 = null;
    String location;
    private String hospitalName,hospitalAddress;
    private DatabaseReference myRefHospital = database.getReference("hospitalInfo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            hospitalName = extras.getString("hospital");
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

                            //Toast.makeText(MapActivity.this, "latitude"+lat+"longitude"+longt, Toast.LENGTH_SHORT).show();


                        } catch (IOException ex) {

                            ex.printStackTrace();
                        }


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map_main);
            mapFragment.getMapAsync(this);
            //gMap.setMyLocationEnabled(true);

            locationManager = (LocationManager) MapActivity.this.getSystemService(Context.LOCATION_SERVICE);

            // Creating an empty criteria object
            Criteria criteria = new Criteria();

            // Getting the name of the provider that meets the criteria
            provider = locationManager.getBestProvider(criteria, false);

            if (provider != null && !provider.equals("")) {

                // Get the location from the given provider
                Location location = locationManager.getLastKnownLocation(provider);

                locationManager.requestLocationUpdates(provider, 20000, 1, this);

                if (location != null)
                    onLocationChanged(location);
                else
                    Toast.makeText(MapActivity.this, "Location can't be retrieved", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(MapActivity.this, "No Provider Found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        //LatLng latLng = new LatLng(latitude, longitude);
        gMap.addMarker(new
                MarkerOptions().position(p1).title("Location"));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(p1));
        gMap.animateCamera(CameraUpdateFactory.zoomTo(18));
        gMap.setMyLocationEnabled(true);
        // Enabling MyLocation Layer of Google Map
        googleMap.setMyLocationEnabled(true);
    }
}

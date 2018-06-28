package patient.patientmanagement.pms.patient.patientmanagement.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import patient.patientmanagement.pms.R;
import patient.patientmanagement.pms.entity.VolleyCallback;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by suraj on 23/6/17.
 */

public class Tab3Fragment extends Fragment implements LocationListener, OnMapReadyCallback {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    LocationManager locationManager ;
    String provider;
    GoogleMap gMap;
    double latitude,longitude;
    LatLng p1 = null;
    String location;
    private String hospitalName,hospitalAddress;
    private DatabaseReference myRefHospital = database.getReference("hospitalInfo");
    private VolleyCallback callback;

    public static Fragment newInstance() {
        Tab3Fragment fragment = new Tab3Fragment();
        //Toast.makeText(fragment.getActivity(), "ss", Toast.LENGTH_SHORT).show();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab3, container, false);

        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {

            p1 = new LatLng(23.8103, 90.4125);
            //hospitalName = "Apollo Gleneagles Hospitals";
            hospitalName = extras.getString("hospital");

            //Toast.makeText(getActivity(), ""+hospitalName, Toast.LENGTH_SHORT).show();
            myRefHospital.orderByChild("hospitalName").equalTo(String.valueOf(hospitalName)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        hospitalAddress = String.valueOf(childDataSnapshot.child("hospitalAddress").getValue());
                        Geocoder coder = new Geocoder(getActivity());
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

            SupportMapFragment fragment = (SupportMapFragment) this.getChildFragmentManager()
                    .findFragmentById(R.id.map_main);
            fragment.getMapAsync(this);
            //gMap.setMyLocationEnabled(true);

            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

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
                    Toast.makeText(getActivity(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getActivity(), "No Provider Found", Toast.LENGTH_SHORT).show();
            }
        }
        return view;
    }

    @Override
    public void onLocationChanged(Location location) {

        //Toast.makeText(getActivity(), ""+location.getLatitude()+""+location.getLongitude(), Toast.LENGTH_SHORT).show();

        //latitude = location.getLatitude();
        //longitude = location.getLongitude();
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

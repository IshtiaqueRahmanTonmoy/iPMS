package patient.patientmanagement.pms.patient.patientmanagement.fragment;


import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationManager;
import android.support.v4.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import patient.patientmanagement.pms.R;
import patient.patientmanagement.pms.entity.GPSTracker;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapFragment extends Fragment implements LocationListener, OnMapReadyCallback {


    LocationManager locationManager ;
    String provider;
    GoogleMap gMap;
    double latitude,longitude;
    LatLng p1 = null;
    String locations;

    public static Fragment newInstance() {
        MapFragment fragment = new MapFragment();
        //Toast.makeText(fragment.getActivity(), "ss", Toast.LENGTH_SHORT).show();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab3, container, false);

        p1 = new LatLng(23.8103, 90.4125);
        Bundle extras = getActivity().getIntent().getExtras();

        if (extras != null) {
            locations = extras.getString("designationlocation");
        }

        Geocoder coder = new Geocoder(getActivity());
        List<Address> address;
        try {
            // May throw an IOException
            address = coder.getFromLocationName(locations, 5);
            Address locations = address.get(0);
            latitude = locations.getLatitude();
            longitude = locations.getLongitude();
            p1 = new LatLng(latitude, longitude);

            //Toast.makeText(MapActivity.this, "latitude"+lat+"longitude"+longt, Toast.LENGTH_SHORT).show();


        } catch (IOException ex) {

            ex.printStackTrace();
        }


        SupportMapFragment fragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map_main);
        fragment.getMapAsync(this);
        //gMap.setMyLocationEnabled(true);

        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        // Creating an empty criteria object
        Criteria criteria = new Criteria();

        // Getting the name of the provider that meets the criteria
        provider = locationManager.getBestProvider(criteria, false);

        if(provider!=null && !provider.equals("")){

            // Get the location from the given provider
            Location location = locationManager.getLastKnownLocation(provider);

            locationManager.requestLocationUpdates(provider, 20000, 1, this);

            if(location!=null)
                onLocationChanged(location);
            else
                Toast.makeText(getActivity(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(getActivity(), "No Provider Found", Toast.LENGTH_SHORT).show();
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

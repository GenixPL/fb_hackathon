package nodatingapp.fb.someapp.Activities;

import nodatingapp.fb.someapp.Event.EventMap;
import nodatingapp.fb.someapp.LocationStuff.*;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import nodatingapp.fb.someapp.R;

public class MapAcitivity extends AppCompatActivity implements OnMapReadyCallback {

    private OurLocationProvider ourLocationProvider;
    private GoogleMap mMap;


    public static int mapLevel = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        //VARIABLES
        ourLocationProvider = new OurLocationProvider(this);

        //LOCATION
        Location loc = ourLocationProvider.getCurrentUserLocation();
        if(loc != null){

        } else {
            makeToast("CAN'T FIND LOCATION");
        }
    }

    private void makeToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in current user position
        double lat = ourLocationProvider.getCurrentUserLocation().getLatitude();
        double lon = ourLocationProvider.getCurrentUserLocation().getLongitude();
        LatLng currentPosition = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(currentPosition).title("Your current position"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, mapLevel));
    }

    public void goToCurrentEvents_But(View view) {
        makeToast("Should swap to current events activity");
    }

    public void setAvailability_But(View view) {
        startActivity(new Intent(this, SetAvailabilityActivity.class));
    }
}

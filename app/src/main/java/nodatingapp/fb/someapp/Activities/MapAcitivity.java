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
    public static Date mapViewDate;
    private TextView date_textView;
    private TextView place_textView;
    private static final int NEW_EVENT_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        //VARIABLES
        ourLocationProvider = new OurLocationProvider(this);
        date_textView = findViewById(R.id.textView_date);

        //INIT VARIABLES
        if(mapViewDate != null) {
            DateFormat formater = new SimpleDateFormat("MM-dd hh:mm");
            makeToast(mapViewDate.toString());
            date_textView.setText("Date: " + mapViewDate.getYear() + "-" + formater.format(mapViewDate).toString());
        }

        //LOCATION
        Location loc = ourLocationProvider.getCurrentUserLocation();
        if(loc != null){
            makeToast(loc.getLatitude() +" " + loc.getLongitude() + "");
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

        // Add a marker in Sydney and move the camera
        double lat = ourLocationProvider.getCurrentUserLocation().getLatitude();
        double lon = ourLocationProvider.getCurrentUserLocation().getLongitude();
        LatLng currentPosition = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(currentPosition).title("Marker in current user position"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15));
    }

    public void goToCurrentEvents_But(View view) {
        makeToast("Should swap to current events activity");
    }


    public void goToPickDate_But(View view) {
        startActivity(new Intent(this, PickDateActivity.class));
        this.finish();
    }

    public void goToPickPlace_But(View view) {
        startActivity(new Intent(this, EventMap.class));
        this.finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == NEW_EVENT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // get String data from Intent
                Double lat = data.getDoubleExtra("latitude", 0f);
                Double lng = data.getDoubleExtra("longitude", 0f);

                Log.d("NewEvent", "Lat: " + lat + " Lng: " + lng);

                Geocoder geocoder = new Geocoder(this);
                try {
                    List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);

                    place_textView.setText("Place: " + addresses.get(0).getAddressLine(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

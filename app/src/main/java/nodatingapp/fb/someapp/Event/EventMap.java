package nodatingapp.fb.someapp.Event;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import nodatingapp.fb.someapp.Activities.MapAcitivity;
import nodatingapp.fb.someapp.Helpers.Helper;
import nodatingapp.fb.someapp.LocationStuff.OurLocationProvider;
import nodatingapp.fb.someapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventMap extends AppCompatActivity {

    private MapView mapView;
    private GoogleMap map;
    private LatLng latLng;
    private MarkerOptions markerOptions = new MarkerOptions();
    private OurLocationProvider ourLocationProvider;

    private Button buttonFinish;

    private String[] appPermisions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    public EventMap() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_event_map);

        buttonFinish = findViewById(R.id.buttonFinish);
        mapView = findViewById(R.id.mapViewEventPosition);
        ourLocationProvider = new OurLocationProvider(this);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(onMapReadyCallback);

        buttonFinish.setOnClickListener(onButtonFinishClickListener);
    }

    private View.OnClickListener onButtonFinishClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.putExtra("latitude", latLng.latitude);
            intent.putExtra("longitude", latLng.longitude);
            setResult(RESULT_OK, intent);
            finish();
        }
    };

    private OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;

            map.getUiSettings().setMyLocationButtonEnabled(false);

            int result;
            List<String> listPermissionsNeeded = new ArrayList<>();
            for (String p : appPermisions)
            {
                result = ContextCompat.checkSelfPermission(EventMap.this, p);
                if (result != PackageManager.PERMISSION_GRANTED)
                {
                    listPermissionsNeeded.add(p);
                }
            }

            if (!listPermissionsNeeded.isEmpty())
                ActivityCompat.requestPermissions(EventMap.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 10 );

            map.setMyLocationEnabled(true);

            map.setOnMapClickListener(onMapClickListener);

            // Add a marker in current user position
            double lat = ourLocationProvider.getCurrentUserLocation().getLatitude();
            double lon = ourLocationProvider.getCurrentUserLocation().getLongitude();
            LatLng currentPosition = new LatLng(lat, lon);
            map.addMarker(new MarkerOptions().position(currentPosition).title("Your current position"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, MapAcitivity.mapLevel));
        }
    };

    private GoogleMap.OnMapClickListener onMapClickListener = new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng ll) {
            latLng = ll;

            buttonFinish.setVisibility(Button.VISIBLE);

            //remove makers
            map.clear();

            // Add a marker in current user position
            double lat = ourLocationProvider.getCurrentUserLocation().getLatitude();
            double lon = ourLocationProvider.getCurrentUserLocation().getLongitude();
            LatLng currentPosition = new LatLng(lat, lon);
            map.addMarker(new MarkerOptions().position(currentPosition).title("Your current position"));

            markerOptions.position(latLng);
            map.addMarker(markerOptions);
        }
    };

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}

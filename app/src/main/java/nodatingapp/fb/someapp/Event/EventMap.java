package nodatingapp.fb.someapp.Event;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import nodatingapp.fb.someapp.Helpers.Helper;
import nodatingapp.fb.someapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventMap extends Fragment {

    private MapView mapView;
    private GoogleMap map;

    private Button buttonFinish;

    private MarkerOptions markerOptions = new MarkerOptions();

    private String[] appPermisions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    public EventMap() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_map, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceBundle) {
        buttonFinish = view.findViewById(R.id.buttonFinish);
        mapView = view.findViewById(R.id.mapViewEventPosition);

        mapView.onCreate(savedInstanceBundle);
        mapView.getMapAsync(onMapReadyCallback);

        buttonFinish.setOnClickListener(onButtonFinishClickListener);
    }

    private View.OnClickListener onButtonFinishClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

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
                result = ContextCompat.checkSelfPermission(getActivity(), p);
                if (result != PackageManager.PERMISSION_GRANTED)
                {
                    listPermissionsNeeded.add(p);
                }
            }

            if (!listPermissionsNeeded.isEmpty())
                ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 10 );

            map.setMyLocationEnabled(true);

            map.setMaxZoomPreference(5f);
            map.setMinZoomPreference(3.4f);
            map.setOnMapClickListener(onMapClickListener);
        }
    };

    private GoogleMap.OnMapClickListener onMapClickListener = new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {
            buttonFinish.setVisibility(Button.VISIBLE);

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

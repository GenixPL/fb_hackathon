package nodatingapp.fb.someapp.Activities;

import nodatingapp.fb.someapp.DisplayEvents.DisplayEvent;
import nodatingapp.fb.someapp.DisplayEvents.EventsAdapter;
import nodatingapp.fb.someapp.Event.EventMap;
import nodatingapp.fb.someapp.Event.Models.Event;
import nodatingapp.fb.someapp.Helpers.HttpHandler;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nodatingapp.fb.someapp.R;
import nodatingapp.fb.someapp.User.User;

public class MapAcitivity extends AppCompatActivity implements OnMapReadyCallback {

    private OurLocationProvider ourLocationProvider;
    private GoogleMap mMap;
    private Spinner spinner;

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
        spinner = findViewById(R.id.spinner_fromMap);

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
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Add a marker in current user position
        double lat = ourLocationProvider.getCurrentUserLocation().getLatitude();
        double lon = ourLocationProvider.getCurrentUserLocation().getLongitude();
        LatLng currentPosition = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(currentPosition).title("Your current position"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, mapLevel));
        mMap.addCircle(new CircleOptions().center(new LatLng(lat, lon)).radius(500));

        //Users
        double lat2 = lat + 0.0001;
        double lon2 = lon + 0.0001;
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat2, lon2)).title("Łukasz")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        double lat3 = lat + 0.002;
        double lon3 = lon + 0.0001;
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat3, lon3)).title("Edvin")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        double lat4 = lat;
        double lon4 = lon - 0.005;
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat4, lon4)).title("Barbara")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        fetchEvents();
    }

    private void fetchEvents(){
        ArrayList<String> tags = new ArrayList<String>();
        tags.add(spinner.getSelectedItem().toString());

        ourLocationProvider.getFilteredEvents(tags, new HttpHandler.IOnRequestFinished() {
            @Override
            public void onRequestFinished(String output) {
                try {
                    JSONArray jsonObject = new JSONArray(output);

                    List<Event> eventList = new ArrayList<>();
                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject ev = jsonObject.getJSONObject(i);

                        Event event = new Event();
                        //event.setEventTime(ev.getString("date"));
                        event.setName(ev.getString("name"));
                        event.setLatitude(ev.getDouble("latitude"));
                        event.setLongitude(ev.getDouble("longitude"));
                        event.setPersonLimit(ev.getInt("limit"));
                        event.setPlace(ev.getString("placeName"));
                        event.setEventUnique(ev.getString("uniqueKey"));

                        JSONArray userJsonObjects = new JSONArray(ev.getString("participants"));

                        for (int j = 0; j < userJsonObjects.length(); j++) {
                            JSONObject jsonObjectUser = userJsonObjects.getJSONObject(j);
                            User usr = new User();
                            usr.setName(jsonObjectUser.getString("name"));
                            usr.setSurname(jsonObjectUser.getString("surname"));
                            usr.setEmail(jsonObjectUser.getString("email"));
                            usr.setRating(jsonObjectUser.getDouble("rating"));

                            Log.d("EventsFragment", "Debug this");
                            event.addParticipant(usr);
                        }
                        mMap.addMarker(new MarkerOptions().position(new LatLng(event.getLatitude(), event.getLongitude())).title("Barbara")
                                .icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                        eventList.add(event);
                        makeToast("Data fetched from server");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void goToCurrentEvents_But(View view) {
        startActivity(new Intent(this, DisplayEvent.class));
    }

    public void setAvailability_But(View view) {
        startActivity(new Intent(this, SetAvailabilityActivity.class));
    }
}

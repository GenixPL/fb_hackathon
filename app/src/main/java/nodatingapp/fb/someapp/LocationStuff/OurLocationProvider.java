package nodatingapp.fb.someapp.LocationStuff;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import nodatingapp.fb.someapp.Helpers.HttpHandler;
import nodatingapp.fb.someapp.Helpers.HttpJsonArrayHandler;

public class OurLocationProvider {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private Context context;
    private Location currentLocation;

    public OurLocationProvider(Context context) {
        this.context = context;


        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                currentLocation = location;
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            // Register the listener with the Location Manager to receive location updates
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    public void getFilteredEvents(List<String> tags, HttpHandler.IOnRequestFinished callback) {

        if (tags.contains("All")) {
            getAllEvents(callback);
            return;
        }

        Uri.Builder uriBuilder = new Uri.Builder().scheme("http")
                .authority("3d1342c1.ngrok.io")
                .appendPath("event").appendPath("getFiltered");

        final String mURL = uriBuilder.build().toString();

        JSONArray jsonArray = new JSONArray();

        for (String tag: tags) {
            try {
                jsonArray.put(new JSONObject().put("name", tag));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        HttpJsonArrayHandler handler = new HttpJsonArrayHandler(mURL, HttpHandler.Type.POST, callback);

        handler.setJsonObject(jsonArray);

        handler.execute(new String[0]);
    }

    private void getAllEvents(HttpHandler.IOnRequestFinished callback) {
        Uri.Builder uriBuilder = new Uri.Builder().scheme("http")
                .authority("3d1342c1.ngrok.io")
                .appendPath("event").appendPath("get");

        final String mURL = uriBuilder.build().toString();

        HttpJsonArrayHandler handler = new HttpJsonArrayHandler(mURL, HttpHandler.Type.GET, callback);

        handler.execute(new String[0]);
    }

    public Location getCurrentUserLocation() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            makeToast("permissions not granted");

        } else {
            currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (currentLocation == null) {
                currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if (currentLocation == null) {
                    currentLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                }
            }
        }

        return currentLocation;
    }

    private void makeToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}

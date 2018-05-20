package nodatingapp.fb.someapp.Event;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import nodatingapp.fb.someapp.Activities.PickDateActivity;
import nodatingapp.fb.someapp.Helpers.HttpHandler;
import nodatingapp.fb.someapp.Helpers.JSONCreator;
import nodatingapp.fb.someapp.LocationStuff.OurLocationProvider;
import nodatingapp.fb.someapp.R;

import static android.app.Activity.RESULT_OK;

public class NewEventFragment extends Fragment {

    private static final int NEW_EVENT_REQUEST_CODE = 0;
    private static final int NEW_EVENT_REQUEST_CODE_DATE = 1;

    private TextView textViewUniqueKey;
    private TextView textViewDate;
    private Button buttonConfirmation;
    private Button buttonShowMap;
    private ImageButton buttonLocateMe;
    private EditText inputPersonLimit;
    private Spinner dropdownCategories;
    private EditText inputName;
    private EditText inputPlace;

    private Button buttonAddDate;

    private String uniqueID;

    private Double latitude;
    private Double longitude;

    private String outputDate;

    public NewEventFragment() { }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        uniqueID = UUID.randomUUID().toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_new_event, container, false);

        textViewUniqueKey   = view.findViewById(R.id.textViewUniqueKey);
        buttonConfirmation  = view.findViewById(R.id.buttonConfirmation);
        inputPersonLimit    = view.findViewById(R.id.inputPersonLimit);
        dropdownCategories  = view.findViewById(R.id.dropdownCategories);
        inputName           = view.findViewById(R.id.inputName);
        inputPlace          = view.findViewById(R.id.inputPlace);
        buttonShowMap       = view.findViewById(R.id.buttonShowMap);
        buttonLocateMe      = view.findViewById(R.id.buttonLocateMe);
        buttonAddDate       = view.findViewById(R.id.buttonAddDate);
        textViewDate        = view.findViewById(R.id.textViewDate);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        textViewUniqueKey.setText(uniqueID);

        buttonConfirmation.setOnClickListener(onClickListener);
        buttonShowMap.setOnClickListener(onMapClickListener);
        buttonLocateMe.setOnClickListener(onLocateMeClickListener);
        buttonAddDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), PickDateActivity.class), NEW_EVENT_REQUEST_CODE_DATE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == NEW_EVENT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // get String data from Intent
                latitude = data.getDoubleExtra("latitude", 0f);
                longitude = data.getDoubleExtra("longitude", 0f);

                Log.d("NewEvent", "What it'' going on");
                Log.d("NewEvent", "Lat 2: " + latitude);
                Log.d("NewEvent", "Lng 2: " + longitude);

                setEventPlace(latitude, longitude);
            }
        }
        else if (requestCode == NEW_EVENT_REQUEST_CODE_DATE) {
            if (resultCode == RESULT_OK) {

                int year = data.getIntExtra("year", 0);
                int month = data.getIntExtra("month", 0);
                int day = data.getIntExtra("day", 0);
                int hour = data.getIntExtra("hour", 0);
                int minutes = data.getIntExtra("minutes", 0);

                DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:ss");
                try {
                    Date date = format.parse(day + "-" + month + "-" + year + " " + hour + ":" + minutes);

                    outputDate = new SimpleDateFormat("H:m, d MMM yyyy z").format(date);
                    Log.d("NewEvents", "Output date:" + outputDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                textViewDate.setText(outputDate);
            }
        }
    }

    private View.OnClickListener onLocateMeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            OurLocationProvider ourLocationProvider = new OurLocationProvider(getActivity());
            Location location = ourLocationProvider.getCurrentUserLocation();

            latitude = location.getLatitude();
            longitude = location.getLongitude();

            setEventPlace(location.getLatitude(), location.getLongitude());
        }
    };

    private View.OnClickListener onClickListener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            List<JSONObject> tagsArray = new ArrayList<JSONObject>();
            try {
                tagsArray.add(new JSONObject().put("name", dropdownCategories.getSelectedItem().toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray jsonArray = new JSONArray(tagsArray);

            Log.d("NewEvent", "Lat: " + latitude);
            Log.d("NewEvent", "Lng: " + longitude);

            JSONCreator jsonCreator = new JSONCreator();
            jsonCreator.addField("uniqueKey", textViewUniqueKey.getText());
            jsonCreator.addField("name", inputName.getText());
            jsonCreator.addField("placeName", inputPlace.getText());
            jsonCreator.addField("description", "test description");
            jsonCreator.addField("tags", jsonArray);
            jsonCreator.addField("latitude", latitude);
            jsonCreator.addField("longitude", longitude);
            jsonCreator.addField("radius", 212);
            jsonCreator.addField("limit", Integer.parseInt(inputPersonLimit.getText().toString()));
            jsonCreator.addField("date", outputDate);

            HttpHandler httpHandler = new HttpHandler("http://3d1342c1.ngrok.io/event/create", HttpHandler.Type.POST, new HttpHandler.IOnRequestFinished() {
                @Override
                public void onRequestFinished(String output) {
                    Toast.makeText(getActivity(), "You have successfully created the event", Toast.LENGTH_LONG).show();
                }
            });
            httpHandler.setJsonObject(jsonCreator.getFinalObject());

            httpHandler.execute();
        }
    };

    private View.OnClickListener onMapClickListener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), EventMap.class);
            startActivityForResult(intent, NEW_EVENT_REQUEST_CODE);
        }
    };

    public void setEventPlace(double lat, double lng) {
        Log.d("NewEvent", "Lat 2: " + lat);
        Log.d("NewEvent", "Lng 2: " + lng);

        Geocoder geocoder = new Geocoder(getActivity());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);

            inputPlace.setText(addresses.get(0).getAddressLine(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
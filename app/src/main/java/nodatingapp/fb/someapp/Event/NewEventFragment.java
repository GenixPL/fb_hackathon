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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import nodatingapp.fb.someapp.Helpers.HttpHandler;
import nodatingapp.fb.someapp.Helpers.JSONCreator;
import nodatingapp.fb.someapp.LocationStuff.OurLocationProvider;
import nodatingapp.fb.someapp.R;

import static android.app.Activity.RESULT_OK;

public class NewEventFragment extends Fragment {

    private static final int NEW_EVENT_REQUEST_CODE = 0;

    private TextView textViewUniqueKey;
    private Button buttonConfirmation;
    private Button buttonShowMap;
    private ImageButton buttonLocateMe;
    private EditText inputPersonLimit;
    private Spinner dropdownCategories;
    private EditText inputTime;
    private EditText inputName;
    private EditText inputPlace;

    private String uniqueID;

    private Double latitude;
    private Double longitude;

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
        inputTime           = view.findViewById(R.id.inputTime);
        inputName           = view.findViewById(R.id.inputName);
        inputPlace          = view.findViewById(R.id.inputPlace);
        buttonShowMap       = view.findViewById(R.id.buttonShowMap);
        buttonLocateMe      = view.findViewById(R.id.buttonLocateMe);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        textViewUniqueKey.setText(uniqueID);

        buttonConfirmation.setOnClickListener(onClickListener);
        buttonShowMap.setOnClickListener(onMapClickListener);
        buttonLocateMe.setOnClickListener(onLocateMeClickListener);
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

                setEventPlace(latitude, longitude);
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
                tagsArray.add(new JSONObject().put("name", "debug"));
                tagsArray.add(new JSONObject().put("name", "debug"));
                tagsArray.add(new JSONObject().put("name", "debug"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray jsonArray = new JSONArray(tagsArray);

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
            jsonCreator.addField("date", "12:34, 3 Dec 1997 PST");

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
        Geocoder geocoder = new Geocoder(getActivity());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);

            inputPlace.setText(addresses.get(0).getAddressLine(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
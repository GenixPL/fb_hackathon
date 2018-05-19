package nodatingapp.fb.someapp.Event;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import nodatingapp.fb.someapp.R;

import static android.app.Activity.RESULT_OK;

public class NewEventFragment extends Fragment {

    private static final int NEW_EVENT_REQUEST_CODE = 0;

    private TextView textViewUniqueKey;
    private Button buttonConfirmation;
    private Button buttonShowMap;
    private Button buttonLocateMe;
    private EditText inputPersonLimit;
    private Spinner dropdownCategories;
    private EditText inputTime;
    private EditText inputName;
    private EditText inputPlace;

    private String uniqueID;

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

                Geocoder geocoder = new Geocoder(getActivity());
                try {
                    List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);

                    inputPlace.setText(addresses.get(0).getAddressLine(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private View.OnClickListener onClickListener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener onMapClickListener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), EventMap.class);
            startActivityForResult(intent, NEW_EVENT_REQUEST_CODE);
        }
    };


}
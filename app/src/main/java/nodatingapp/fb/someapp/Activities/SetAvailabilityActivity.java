package nodatingapp.fb.someapp.Activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import nodatingapp.fb.someapp.Event.EventMap;
import nodatingapp.fb.someapp.R;

public class SetAvailabilityActivity extends AppCompatActivity {

    private TextView date_textView;
    private TextView place_textView;

    private static final int NEW_EVENT_REQUEST_CODE = 0;
    private static double placeLat;
    private static double placeLon;
    public static Date mapViewDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_availability);

        date_textView = findViewById(R.id.textView_date);
        place_textView = findViewById(R.id.textView_place);

        if(mapViewDate != null) {
            DateFormat formater = new SimpleDateFormat("MM-dd hh:mm");
            makeToast(mapViewDate.toString());
            date_textView.setText("Date: " + mapViewDate.getYear() + "-" + formater.format(mapViewDate).toString());
        }
        setEventPlace();
    }

    public void goToPickPlace_But(View view) {
        startActivityForResult(new Intent(this, EventMap.class), NEW_EVENT_REQUEST_CODE);
    }

    public void goToPickDate_But(View view) {
        startActivity(new Intent(this, PickDateActivity.class));
        this.finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == NEW_EVENT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // get String data from Intent
                placeLat = data.getDoubleExtra("latitude", 0f);
                placeLon = data.getDoubleExtra("longitude", 0f);

                setEventPlace();
            }
        }
    }

    private void setEventPlace(){
        if(placeLon == 0 || placeLat == 0){
            return;
        }

        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addresses = geocoder.getFromLocation(placeLat, placeLon, 1);

            place_textView.setText("Place: " + addresses.get(0).getAddressLine(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

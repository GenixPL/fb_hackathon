package nodatingapp.fb.someapp.Activities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nodatingapp.fb.someapp.Event.EventMap;
import nodatingapp.fb.someapp.Helpers.HttpHandler;
import nodatingapp.fb.someapp.LocationStuff.OurLocationProvider;
import nodatingapp.fb.someapp.R;

public class SetAvailabilityActivity extends AppCompatActivity {

    private TextView date_textView;
    private TextView place_textView;
    private Spinner spinner;

    private static final int NEW_EVENT_REQUEST_CODE = 0;
    private static final int NEW_EVENT_REQUEST_CODE_DATE = 1;
    private static double placeLat;
    private static double placeLon;
    private Date mapViewDate;

    private OurLocationProvider ourLocationProvider;
    private NotificationCompat.Builder notification;
    private static final int uniqueID = 69;

    private boolean flagForLoop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_availability);

        date_textView = findViewById(R.id.textView_date);
        place_textView = findViewById(R.id.textView_place);
        spinner = findViewById(R.id.spinner_fromSet);
        ourLocationProvider = new OurLocationProvider(this);

        setEventDate();
        setEventPlace();
    }

    public void goToPickPlace_But(View view) {
        startActivityForResult(new Intent(this, EventMap.class), NEW_EVENT_REQUEST_CODE);
    }

    public void goToPickDate_But(View view) {
        startActivityForResult(new Intent(this, PickDateActivity.class), NEW_EVENT_REQUEST_CODE_DATE);
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

                //setEventPlace();
            }
        } else if (requestCode == NEW_EVENT_REQUEST_CODE_DATE) {
            if (resultCode == RESULT_OK) {

                int year = data.getIntExtra("year", 0);
                int month = data.getIntExtra("month", 0);
                int day = data.getIntExtra("day", 0);
                int hour = data.getIntExtra("hour", 0);
                int minutes = data.getIntExtra("minutes", 0);
//                mapViewDate = new Date(year, month, day, hour, minutes);
                date_textView.setText("Date: " + year + "-" + (month +1)+ "-" + day + " " +
                        hour + ":" + minutes);
//                setEventDate();
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

    private void setEventDate(){
        if(mapViewDate != null) {
            date_textView.setText("Date: " + mapViewDate.getYear() + "-" + mapViewDate.getMonth()+"-"+mapViewDate.getDay() +" " +
            mapViewDate.getHours() + ":" + mapViewDate.getMinutes());
        }
    }

    private void makeToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void startNotifying_But(View view) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //TODO your background code

                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        break;
                    }

                    isEventAvailable();

                    if (flagForLoop) {
                        notification = new NotificationCompat.Builder(getApplicationContext(), "hmm");
                        notification.setAutoCancel(true);
                        notification.setSmallIcon(R.mipmap.ic_launcher);
                        notification.setTicker("Socialize: New events!");
                        notification.setWhen(System.currentTimeMillis());
                        notification.setContentTitle("Socialize");
                        notification.setContentText("New events are available nearby you!");

                        Intent intent = new Intent(getApplicationContext(), MapAcitivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        notification.setContentIntent(pendingIntent);

                        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        nm.notify(uniqueID, notification.build());

                        break;
                    }
                }
            }
        });

        this.finish();
    }

    private void isEventAvailable(){
        ArrayList<String> tags = new ArrayList<String>();
        tags.add(spinner.getSelectedItem().toString());

        ourLocationProvider.getFilteredEvents(tags, new HttpHandler.IOnRequestFinished() {
            @Override
            public void onRequestFinished(String output) {
                makeToast(output);
                flagForLoop = true;
            }
        });
    }
}

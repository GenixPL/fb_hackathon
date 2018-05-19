package nodatingapp.fb.someapp.DisplayEvents;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import nodatingapp.fb.someapp.R;

public class DisplayEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_activity_event, new EventsFragment()).commit();
    }

}

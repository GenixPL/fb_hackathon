package nodatingapp.fb.someapp.Event;

import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import nodatingapp.fb.someapp.Event.Adapters.EventPagesAdapter;
import nodatingapp.fb.someapp.Event.Models.Event;
import nodatingapp.fb.someapp.R;

public class EventActivity extends AppCompatActivity {

    public static Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Bundle bundle = getIntent().getExtras();
        event = bundle == null ? null : (Event)bundle.getSerializable("Event");

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_new_event_content, new EventFragment()).commit();
    }
}

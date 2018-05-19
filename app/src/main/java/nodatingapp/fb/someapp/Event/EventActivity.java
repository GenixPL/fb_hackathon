package nodatingapp.fb.someapp.Event;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import nodatingapp.fb.someapp.Event.Adapters.EventPagesAdapter;
import nodatingapp.fb.someapp.R;

public class EventActivity extends AppCompatActivity {

    private EventPagesAdapter eventPagesAdapter;

    private ViewPager mViewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        eventPagesAdapter = new EventPagesAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(eventPagesAdapter);

        tabLayout = findViewById(R.id.tabLayoutViewPager);
        tabLayout.setupWithViewPager(mViewPager);
    }
}

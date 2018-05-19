package nodatingapp.fb.someapp.Event;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nodatingapp.fb.someapp.Event.Adapters.EventPagesAdapter;
import nodatingapp.fb.someapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    private EventPagesAdapter eventPagesAdapter;

    private ViewPager mViewPager;
    private TabLayout tabLayout;

    public EventFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        eventPagesAdapter = new EventPagesAdapter(getActivity().getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = view.findViewById(R.id.container);
        mViewPager.setAdapter(eventPagesAdapter);

        tabLayout = view.findViewById(R.id.tabLayoutViewPager);
        tabLayout.setupWithViewPager(mViewPager);
    }
}

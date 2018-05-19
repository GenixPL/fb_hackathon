package nodatingapp.fb.someapp.Event.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import nodatingapp.fb.someapp.Event.EventActivity;
import nodatingapp.fb.someapp.Event.EventInfo;
import nodatingapp.fb.someapp.Event.NewEventFragment;
import nodatingapp.fb.someapp.Event.ParticipantsFragment;

public class EventPagesAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Info", "Participants" };

    public EventPagesAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        switch (position) {
            case 0: return EventActivity.event == null ? new NewEventFragment() : (new EventInfo());
            case 1: return new ParticipantsFragment();
        }

        return new NewEventFragment();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return tabTitles[position];
    }
}
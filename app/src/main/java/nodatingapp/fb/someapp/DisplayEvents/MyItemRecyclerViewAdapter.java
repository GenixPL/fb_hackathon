package nodatingapp.fb.someapp.DisplayEvents;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;

import nodatingapp.fb.someapp.DisplayEvents.EventsFragment.OnListFragmentInteractionListener;
import nodatingapp.fb.someapp.Event.Models.Event;
import nodatingapp.fb.someapp.R;

import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Event> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<Event> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View layoutMain;
        private TextView textViewName;
        private MapView mapViewLocationInfo;
        private TextView textViewOrganizerName;
        private Button buttonInfo;

        public ViewHolder(View view) {
            super(view);
            layoutMain = (RelativeLayout) view.findViewById(R.id.layoutMain);
            textViewName = view.findViewById(R.id.textViewName);
            mapViewLocationInfo = view.findViewById(R.id.mapViewLocationInfo);
            textViewOrganizerName = view.findViewById(R.id.textViewOrganizerName);
            buttonInfo = view.findViewById(R.id.buttonInfo);
        }
    }
}

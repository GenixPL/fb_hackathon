package nodatingapp.fb.someapp.DisplayEvents;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import nodatingapp.fb.someapp.DisplayEvents.EventsFragment.OnListFragmentInteractionListener;
import nodatingapp.fb.someapp.Event.EventMap;
import nodatingapp.fb.someapp.Event.Models.Event;
import nodatingapp.fb.someapp.R;

import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private final List<Event> mValues;
    private final OnListFragmentInteractionListener mListener;

    private Context context;

    public EventsAdapter(Context context, List<Event> items, OnListFragmentInteractionListener listener) {
        this.mValues = items;
        this.mListener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.textViewName.setText(mValues.get(position).getName());
        holder.textViewOrganizerName.setText("Edvin");

        Glide.with(this.context).load("https://maps.googleapis.com/maps/api/staticmap?center=" + mValues.get(position).getLatitude() + "," + mValues.get(position).getLongitude() + "&zoom=5&size=600x300&maptype=roadmap&markers=color:red%7Clabel:S%7C" + mValues.get(position).getLatitude() + "," + mValues.get(position).getLongitude() + "&key=AIzaSyBEg0bNOmQ3x-i5Y9sv1Oc799uRM9lhe84").into(holder.imageViewLocationInfo);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View layoutMain;
        private TextView textViewName;
        private ImageView imageViewLocationInfo;
        private TextView textViewOrganizerName;
        private Button buttonInfo;

        public ViewHolder(View view) {
            super(view);
            layoutMain = view.findViewById(R.id.layoutMain);
            textViewName = view.findViewById(R.id.textViewName);
            imageViewLocationInfo = view.findViewById(R.id.imageViewLocationInfo);
            textViewOrganizerName = view.findViewById(R.id.textViewOrganizerName);
            buttonInfo = view.findViewById(R.id.buttonInfo);
        }
    }
}

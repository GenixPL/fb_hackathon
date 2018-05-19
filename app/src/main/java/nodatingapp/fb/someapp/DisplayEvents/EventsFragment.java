package nodatingapp.fb.someapp.DisplayEvents;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nodatingapp.fb.someapp.Event.Models.Event;
import nodatingapp.fb.someapp.Helpers.HttpHandler;
import nodatingapp.fb.someapp.R;

public class EventsFragment extends Fragment {

    private int mColumnCount = 1;

    private OnListFragmentInteractionListener mListener;

    public EventsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            HttpHandler httpHandler = new HttpHandler("http://3d1342c1.ngrok.io/event/get", HttpHandler.Type.GET, new HttpHandler.IOnRequestFinished() {
                @Override
                public void onRequestFinished(String output) {
                    Log.d("EventsFragment", "Output: " + output);

                    //recyclerView.setAdapter(new MyItemRecyclerViewAdapter(Event.ITEMS, mListener));
                }
            });
            httpHandler.makeServiceCall();
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Event item);
    }
}

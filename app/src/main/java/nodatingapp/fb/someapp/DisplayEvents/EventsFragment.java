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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nodatingapp.fb.someapp.Event.Models.Event;
import nodatingapp.fb.someapp.Helpers.HttpHandler;
import nodatingapp.fb.someapp.R;
import nodatingapp.fb.someapp.User.User;

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
            final RecyclerView recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            HttpHandler httpHandler = new HttpHandler("http://3d1342c1.ngrok.io/event/get", HttpHandler.Type.GET, new HttpHandler.IOnRequestFinished() {
                @Override
                public void onRequestFinished(String output) {
                    Log.d("EventsFragment", "Output: " + output);

                    try {
                        JSONArray jsonObject = new JSONArray(output);

                        List<Event> eventList = new ArrayList<>();
                        for(int i = 0; i < jsonObject.length(); i++) {
                            JSONObject ev = jsonObject.getJSONObject(i);

                            Event event = new Event();
                            //event.setEventTime(ev.getString("date"));
                            event.setName(ev.getString("name"));
                            event.setLatitude(ev.getDouble("latitude"));
                            event.setLatitude(ev.getDouble("longitude"));
                            event.setPersonLimit(ev.getInt("limit"));
                            event.setPlace(ev.getString("placeName"));
                            event.setEventUnique(ev.getString("uniqueKey"));

                            JSONArray userJsonObjects = new JSONArray(ev.getString("participants"));

                            for(int j = 0; j < userJsonObjects.length(); j++) {
                                JSONObject jsonObjectUser = userJsonObjects.getJSONObject(j);
                                User usr = new User();
                                usr.setName(jsonObjectUser.getString("name"));
                                usr.setSurname(jsonObjectUser.getString("surname"));
                                usr.setEmail(jsonObjectUser.getString("email"));
                                usr.setRating(jsonObjectUser.getDouble("rating"));

                                Log.d("EventsFragment", "Debug this");
                                event.addParticipant(usr);
                            }

                            eventList.add(event);

                            recyclerView.setAdapter(new EventsAdapter(getActivity(), eventList, mListener));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            httpHandler.execute();
        }
        return view;
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

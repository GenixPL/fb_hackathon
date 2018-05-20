package nodatingapp.fb.someapp.DisplayEvents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nodatingapp.fb.someapp.Event.EventActivity;
import nodatingapp.fb.someapp.Event.Models.Event;
import nodatingapp.fb.someapp.Helpers.HttpHandler;
import nodatingapp.fb.someapp.R;
import nodatingapp.fb.someapp.User.User;

public class EventsFragment extends Fragment {

    private Button buttonNewEvent;
    private Spinner spinner;
    private RecyclerView recyclerView;
    private List<Event> eventList;

    private OnListFragmentInteractionListener mListener;

    public EventsFragment() {
    }

    private void initSpinnerListener(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                recyclerView.removeAllViews();
                doFetchingStuff();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void doFetchingStuff(){
        HttpHandler httpHandler = new HttpHandler("http://3d1342c1.ngrok.io/event/get", HttpHandler.Type.GET, new HttpHandler.IOnRequestFinished() {
            @Override
            public void onRequestFinished(String output) {
                Log.d("EventsFragment", "Output: " + output);

                try {
                    JSONArray jsonObject = new JSONArray(output);

                    eventList = new ArrayList<>();
                    for(int i = 0; i < jsonObject.length(); i++) {
                        JSONObject ev = jsonObject.getJSONObject(i);

                        Event event = new Event();
                        //event.setEventTime(ev.getString("date"));
                        event.setName(ev.getString("name"));
                        event.setLatitude(ev.getDouble("latitude"));
                        event.setLongitude(ev.getDouble("longitude"));
                        event.setPersonLimit(ev.getInt("limit"));
                        event.setPlace(ev.getString("placeName"));
                        event.setEventTime(ev.getString("date"));
                        event.setEventUnique(ev.getString("uniqueKey"));
                        event.setCategory(ev.getJSONArray("tags").getJSONObject(0).getString("name"));

                        JSONObject creatorJson = ev.getJSONObject("organiser");

                        User u = new User();
                        u.setName(creatorJson.getString("name"));
                        u.setSurname(creatorJson.getString("surname"));
                        u.setEmail(creatorJson.getString("email"));
                        u.setProfilePicture(creatorJson.getString("profilePicture"));
                        u.setRating(creatorJson.getDouble("rating"));
                        event.setCreator(u);

                        JSONArray userJsonObjects = new JSONArray(ev.getString("participants"));

                        for(int j = 0; j < userJsonObjects.length(); j++) {
                            JSONObject jsonObjectUser = userJsonObjects.getJSONObject(j);
                            User usr = new User();
                            usr.setName(jsonObjectUser.getString("name"));
                            usr.setSurname(jsonObjectUser.getString("surname"));
                            usr.setEmail(jsonObjectUser.getString("email"));
                            usr.setRating(jsonObjectUser.getDouble("rating"));
                            usr.setProfilePicture(creatorJson.getString("profilePicture"));

                            Log.d("EventsFragment", "Debug this");
                            event.addParticipant(usr);
                        }

                        if(event.getCategory() != null) {
                            if (event.getCategory().equals(spinner.getSelectedItem().toString()) || spinner.getSelectedItem().equals("All")) {
                                eventList.add(event);
                            }
                        }

                        recyclerView.setAdapter(new EventsAdapter(getActivity(), eventList, mListener));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        httpHandler.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_list, container, false);

        spinner = view.findViewById(R.id.spinner_fromList);
        initSpinnerListener();

        recyclerView = view.findViewById(R.id.recyclerViewEventsFeed);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        doFetchingStuff();

        buttonNewEvent = view.findViewById(R.id.buttonNewEvent);
        buttonNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EventActivity.class);

                getActivity().startActivity(intent);
            }
        });
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

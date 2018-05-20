package nodatingapp.fb.someapp.Event;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import nodatingapp.fb.someapp.Event.Models.Event;
import nodatingapp.fb.someapp.Helpers.Authentication;
import nodatingapp.fb.someapp.Helpers.HttpHandler;
import nodatingapp.fb.someapp.Helpers.JSONCreator;
import nodatingapp.fb.someapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventInfo extends android.support.v4.app.Fragment {

    private TextView textViewName;
    private ImageView imageViewLocationInfo;
    private RatingBar ratingBarUser;
    private TextView textViewOrganizerName;
    private ImageView imageViewUser;
    private TextView textViewPlaceName;
    private LinearLayout linearLayoutTags;
    private Button buttonTags;
    private Button buttonEnroll;
    private TextView textViewDate;

    public EventInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_info, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle bundle) {
        textViewName = view.findViewById(R.id.textViewName);
        imageViewLocationInfo = view.findViewById(R.id.imageViewLocationInfo);
        ratingBarUser = view.findViewById(R.id.ratingBarUser);
        textViewOrganizerName = view.findViewById(R.id.textViewOrganizerName);
        imageViewUser = view.findViewById(R.id.imageViewUser);
        textViewPlaceName = view.findViewById(R.id.textViewPlaceName);
        linearLayoutTags = view.findViewById(R.id.linearLayoutTags);
        buttonEnroll = view.findViewById(R.id.buttonEnroll);
        textViewDate = view.findViewById(R.id.textViewDate);
        buttonTags = view.findViewById(R.id.buttonTags);

        textViewName.setText(EventActivity.event.getName());
        Glide.with(view.getContext()).load("https://maps.googleapis.com/maps/api/staticmap?center=" + EventActivity.event.getLatitude() + "," + EventActivity.event.getLongitude() + "&zoom=16&size=600x300&maptype=roadmap&markers=color:red%7Clabel:S%7C" + EventActivity.event.getLatitude() + "," + EventActivity.event.getLongitude() + "&key=AIzaSyBEg0bNOmQ3x-i5Y9sv1Oc799uRM9lhe84").into(imageViewLocationInfo);
        ratingBarUser.setRating(EventActivity.event.getCreator().getRating().floatValue());
        textViewOrganizerName.setText(EventActivity.event.getCreator().getName());
        buttonTags.setText(EventActivity.event.getCategory());


        Glide.with(view.getContext()).load(EventActivity.event.getCreator().getProfilePicture()).into(imageViewUser);
        textViewPlaceName.setText(EventActivity.event.getPlace());
        textViewDate.setText(EventActivity.event.getEventTime().toString());

        if ((EventActivity.event.getPersonLimit() - EventActivity.event.getParticipants().size()) > 0)
            buttonEnroll.setText("Participate - " + (EventActivity.event.getPersonLimit() - EventActivity.event.getParticipants().size()) + " places left");
        else {
            buttonEnroll.setText("No places left");
            buttonEnroll.setEnabled(false);
        }


        buttonEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONArray jsonArray = new JSONArray();
                for(int i = 0; i < Authentication.getCurrentUser().getUserCategories().size(); i++) {
                    try {
                        jsonArray.put(new JSONObject().put("name", Authentication.getCurrentUser().getUserCategories().get(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                JSONCreator jsonCreator = new JSONCreator();
                jsonCreator.addField("name", Authentication.getCurrentUser().getName());
                jsonCreator.addField("surname", Authentication.getCurrentUser().getSurname());
                jsonCreator.addField("email", Authentication.getCurrentUser().getEmail());
                jsonCreator.addField("tags", jsonArray);
                jsonCreator.addField("rating", Authentication.getCurrentUser().getRating());

                HttpHandler httpHandler = new HttpHandler("http://3d1342c1.ngrok.io/event/addParticipant?uniqueKey=" + EventActivity.event.getEventUnique(), HttpHandler.Type.POST, new HttpHandler.IOnRequestFinished() {
                    @Override
                    public void onRequestFinished(String output) {

                        Toast.makeText(view.getContext(), "You have successfully registered for the event", Toast.LENGTH_LONG).show();
                        getActivity().finish();
                    }
                });
                httpHandler.setJsonObject(jsonCreator.getFinalObject());

                httpHandler.execute();
            }
        });
    }
}

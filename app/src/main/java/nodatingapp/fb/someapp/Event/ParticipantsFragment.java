package nodatingapp.fb.someapp.Event;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nodatingapp.fb.someapp.Event.Adapters.ParticipantsListAdapter;
import nodatingapp.fb.someapp.Event.Models.Event;
import nodatingapp.fb.someapp.Event.Models.Participant;
import nodatingapp.fb.someapp.R;
import nodatingapp.fb.someapp.User.User;

public class ParticipantsFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;

    public ParticipantsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_participants, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new ParticipantsListAdapter(view.getContext(), EventActivity.event != null ? EventActivity.event.getParticipants() : new ArrayList<User>(), mListener));
        }
        return view;
    }

    public void setOnParticipantItemSelection(OnListFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(User item);
    }
}

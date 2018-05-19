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
import nodatingapp.fb.someapp.Event.Models.Participant;
import nodatingapp.fb.someapp.R;

public class ParticipantsFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;

    private List<Participant> participantList;

    public ParticipantsFragment() {
        participantList = new ArrayList<>();

        participantList.add(new Participant("https://scontent-frx5-1.xx.fbcdn.net/v/t1.0-9/20800173_1442162092531922_2178335607955134230_n.jpg?_nc_cat=0&oh=575aae335e2c5150bce183baf492e20c&oe=5B781C15",
                                            "Edvin",
                                                "Hello, I would like to come along to the football match",
                                                        3.4));

        participantList.add(new Participant("https://scontent-frx5-1.xx.fbcdn.net/v/t1.0-9/20800173_1442162092531922_2178335607955134230_n.jpg?_nc_cat=0&oh=575aae335e2c5150bce183baf492e20c&oe=5B781C15",
                "Filip",
                "I would like to come too.",
                4.2));

        participantList.add(new Participant("https://scontent-frx5-1.xx.fbcdn.net/v/t1.0-9/20800173_1442162092531922_2178335607955134230_n.jpg?_nc_cat=0&oh=575aae335e2c5150bce183baf492e20c&oe=5B781C15",
                "Lukasz",
                "I'm a good player",
                4.5));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_participants, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new ParticipantsListAdapter(view.getContext(), participantList, mListener));
        }
        return view;
    }

    public void setOnParticipantItemSelection(OnListFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Participant item);
    }
}

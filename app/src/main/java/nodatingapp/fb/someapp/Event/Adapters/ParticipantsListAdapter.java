package nodatingapp.fb.someapp.Event.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import nodatingapp.fb.someapp.Event.Models.Participant;
import nodatingapp.fb.someapp.Event.ParticipantsFragment.OnListFragmentInteractionListener;
import nodatingapp.fb.someapp.R;

import java.util.List;

public class ParticipantsListAdapter extends RecyclerView.Adapter<ParticipantsListAdapter.ViewHolder> {

    private final List<Participant> mValues;
    private final OnListFragmentInteractionListener mListener;

    private Context context;

    public ParticipantsListAdapter(Context context, List<Participant> items, OnListFragmentInteractionListener listener) {
        this.mValues    = items;
        this.mListener  = listener;
        this.context    = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_participant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Participant participant = mValues.get(position);

        Log.d("ParticipantAdapter", "Url: " + participant.getPictureUrl());
        Glide.with(this.context).load(participant.getPictureUrl()).into(holder.imageViewProfile);
        holder.ratingBarUser.setRating(participant.getRating().floatValue());
        holder.textViewUsername.setText(participant.getUserName());
        holder.textViewComment.setText(participant.getDetails());

        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(participant);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mainView;
        public final TextView textViewUsername;
        public final TextView textViewComment;
        public final ImageView imageViewProfile;
        public final RatingBar ratingBarUser;

        public ViewHolder(View view) {
            super(view);

            mainView            = view.findViewById(R.id.mainView);
            textViewUsername    = view.findViewById(R.id.textViewUsername);
            textViewComment     = view.findViewById(R.id.textViewComment);
            imageViewProfile    = view.findViewById(R.id.imageViewProfile);
            ratingBarUser       = view.findViewById(R.id.ratingBarUser);
        }
    }
}

package nodatingapp.fb.someapp.Event;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.UUID;

import nodatingapp.fb.someapp.R;

public class NewEventFragment extends Fragment {

    private TextView textViewUniqueKey;
    private Button buttonConfirmation;
    private EditText inputPersonLimit;
    private Spinner dropdownCategories;
    private EditText inputTime;
    private EditText inputName;
    private EditText inputPlace;

    private String uniqueID;

    public NewEventFragment() { }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        uniqueID = UUID.randomUUID().toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        textViewUniqueKey   = view.findViewById(R.id.textViewUniqueKey);
        buttonConfirmation  = view.findViewById(R.id.buttonConfirmation);
        inputPersonLimit    = view.findViewById(R.id.inputPersonLimit);
        dropdownCategories  = view.findViewById(R.id.dropdownCategories);
        inputTime           = view.findViewById(R.id.inputTime);
        inputName           = view.findViewById(R.id.inputName);
        inputPlace          = view.findViewById(R.id.inputPlace);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        textViewUniqueKey.setText(uniqueID);

        buttonConfirmation.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
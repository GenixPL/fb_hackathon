package nodatingapp.fb.someapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import nodatingapp.fb.someapp.R;

public class PickDateActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private EditText hour_editText;
    private EditText minutes_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_pick_date);

        datePicker = findViewById(R.id.datePicker);
        hour_editText = findViewById(R.id.editText_hour);
        minutes_editText = findViewById(R.id.editText_minutes);

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        datePicker.init(mYear, mMonth, mDay, null);
    }

    //unsafe - no input control
    public void saveDateAndGoBack_But(View view) {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        int hour = Integer.parseInt(hour_editText.getText().toString());
        int minutes = Integer.parseInt(minutes_editText.getText().toString());

        Intent intent = new Intent();
        intent.putExtra("year", year);
        intent.putExtra("month", month);
        intent.putExtra("day", day);
        intent.putExtra("hour", hour);
        intent.putExtra("minutes", minutes);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void makeToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}

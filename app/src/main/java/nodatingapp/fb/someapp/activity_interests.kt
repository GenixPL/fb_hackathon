package nodatingapp.fb.someapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import kotlinx.android.synthetic.main.activity_interests.buttonFootball


class activity_interests : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interests)
    }
    fun IMinterested ( view: View) {
        val interestedString = buttonFootball.text.toString()
        if(interestedString.equals("@string/not_interested")){
                    interestedString="@string/interested";}
        else {
            interestedString="@string/not_interested"
        }
        buttonFootball.text = interestedString;
    }
}

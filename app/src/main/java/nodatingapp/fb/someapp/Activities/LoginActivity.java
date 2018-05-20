package nodatingapp.fb.someapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import nodatingapp.fb.someapp.Helpers.Authentication;
import nodatingapp.fb.someapp.R;
import nodatingapp.fb.someapp.User.User;

public class LoginActivity extends AppCompatActivity {

    private static final String EMAIL = "email";

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Authentication.loadUserData(this);

        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

                Log.d("LoginActivity", "Is logged in: " + isLoggedIn);

                GraphRequest request = GraphRequest.newMeRequest(accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("Main", response.toString());

                                User user = new User();
                                try {
                                    user.setEmail(object.getString("email"));
                                    user.setName(object.getString("name"));
                                    user.setRating(5.0);
                                    user.setProfilePicture(object.getJSONObject("picture").getJSONObject("data").getString("url"));
                                    user.setSurname(object.getString("last_name"));

                                    Authentication.setCurrentUser(user);
                                    Authentication.saveUserData(LoginActivity.this);

                                    Log.d("NewEvent", "Name = " + Authentication.getCurrentUser().getName());
                                    Log.d("NewEvent", "Surname = " + Authentication.getCurrentUser().getSurname());
                                    Log.d("NewEvent", "Email = " + Authentication.getCurrentUser().getEmail());
                                    Log.d("NewEvent", "Rating = " + Authentication.getCurrentUser().getRating());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,last_name,picture.type(large)");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("LoginActivity", "Exception: " + exception);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void goToMap_But(View view) {
        Intent mapActivity = new Intent(this, MapAcitivity.class);
        startActivity(mapActivity);
    }

    private void makeToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

package nodatingapp.fb.someapp.User;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User implements Serializable
{

    private String name = "Edvinko";
    private String surname = "Edvin";
    private String email = "mako_edvin@yahoo.com";
    private String profilePicture = "https://scontent-frx5-1.xx.fbcdn.net/v/t1.0-9/20800173_1442162092531922_2178335607955134230_n.jpg?_nc_cat=0&oh=575aae335e2c5150bce183baf492e20c&oe=5B781C15";
    private Double rating = 2.3;
    private List<String> userCategories;

    public User() {
        userCategories = new ArrayList<>();

        userCategories.add("wakawikiweke");
        userCategories.add("wakawikiweke 2");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<String> getUserCategories() {
        return userCategories;
    }

    public void setUserCategories(List<String> userCategories) {
        this.userCategories = userCategories;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}

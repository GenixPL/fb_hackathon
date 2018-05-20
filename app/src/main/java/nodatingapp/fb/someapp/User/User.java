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
}

package nodatingapp.fb.someapp.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import nodatingapp.fb.someapp.User.User;

public class Authentication {
    private static String fileName = "user_settings.txt";

    public static String sSavedUserData = "SavedUserData";
    public static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User current_user) {
        Authentication.currentUser = current_user;
    }

    public static void loadUserData(Context context) {
        SharedPreferences editor = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        String userJson = editor.getString(sSavedUserData, null);
        Type type = new TypeToken<User>() {}.getType();

        currentUser = new Gson().fromJson(userJson, type);
    }

    public static void saveUserData(Context context) {

        SharedPreferences.Editor editor = context.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();

        editor.putString(sSavedUserData, new Gson().toJson(currentUser));
        editor.apply();
    }
}

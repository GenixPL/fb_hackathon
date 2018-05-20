package nodatingapp.fb.someapp.Helpers;

import nodatingapp.fb.someapp.User.User;

public class Authentication {

    public static User getCurrentUser() {
        return new User();
    }

    public static void setCurrentUser(User current_user) {
        Authentication.currentUser = current_user;
    }

    public static User currentUser;

}

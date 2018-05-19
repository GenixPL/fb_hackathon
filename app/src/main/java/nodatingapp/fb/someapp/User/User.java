package nodatingapp.fb.someapp.User;

import java.util.List;

public class User {

    private String name;
    private Double rating;
    private List<String> userCategories;

    public User() {}

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
}

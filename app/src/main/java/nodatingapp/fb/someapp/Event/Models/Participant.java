package nodatingapp.fb.someapp.Event.Models;

public class Participant {

    private String pictureUrl;
    private String userName;
    private String details;
    private Double rating;

    public Participant(String pictureUrl, String userName, String details, Double rating) {
        this.pictureUrl = pictureUrl;
        this.userName   = userName;
        this.details    = details;
        this.rating     = rating;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

}
package nodatingapp.fb.someapp.Event.Models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nodatingapp.fb.someapp.User.User;

public class Event implements Serializable
{
    private String place;
    private String name;
    private Date eventTime;
    private String category;
    private Integer personLimit;
    private String eventUnique;
    private Double latitude;
    private Double longitude;
    private List<User> participants;
    private User creator;

    public Event() {
        participants = new ArrayList<>();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("H:m, d MMM yyyy z");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(eventTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.eventTime = convertedDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPersonLimit() {
        return personLimit;
    }

    public void setPersonLimit(Integer personLimit) {
        this.personLimit = personLimit;
    }

    public String getEventUnique() {
        return eventUnique;
    }

    public void setEventUnique(String eventUnique) {
        this.eventUnique = eventUnique;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void addParticipant(User user) {
        this.participants.add(user);
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}

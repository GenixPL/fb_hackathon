package nodatingapp.fb.someapp.Event.Models;

import java.util.Date;
import java.util.List;

public class Event
{
    private String place;
    private String name;
    private Date eventTime;
    private String category;
    private Integer personLimit;
    private String eventUnique;
    private List<Participant> participants;

    public Event() {}

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

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
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
}

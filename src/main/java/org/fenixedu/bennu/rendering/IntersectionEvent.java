package org.fenixedu.bennu.rendering;

/**
 * Created by borgez on 31-03-2015.
 */
public class IntersectionEvent {
    private String location;
    private String position;
    private int priority;


    public IntersectionEvent(String location, String position, int priority) {
        this.location = location;
        this.position = position;
        this.priority = priority;
    }

    public int getPriority() {

        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

package org.fenixedu.bennu.rendering;

/**
 * A instance of this class is sent to each handler when a intersection point is rendered.
 *
 * @author Arutr Ventura (artur.ventura@tecnico.pt)
 */
public class IntersectionEvent {
    private String location;
    private String position;
    private int priority;


    protected IntersectionEvent(String location, String position, int priority) {
        this.location = location;
        this.position = position;
        this.priority = priority;
    }

    /**
     * Returns the priority
     *
     * @return the priority
     */
    public int getPriority() {

        return priority;
    }

    /**
     * Sets the priority
     *
     * @param priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Returns the position
     *
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the priority
     *
     * @param position the position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Returns the location
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }
}

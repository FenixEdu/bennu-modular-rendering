/**
 * Copyright © 2016 Instituto Superior Técnico
 *
 * This file is part of Bennu Modular Rendering.
 *
 * Bennu Modular Rendering is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Bennu Modular Rendering is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Bennu Modular Rendering.  If not, see <http://www.gnu.org/licenses/>.
 */
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

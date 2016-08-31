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

import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.fenixedu.bennu.rendering.view.StringView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is the main interface for doing template rendering. Each template
 * declares places that wishes to possible to add more stuff. Each intersection
 * point is composed of location and a optional position. The location is
 * suppose to be constant within the same template, and the position declares in
 * what places can be added more html code (e.g. location="cms.list.sites"
 * position="management.buttons"). Handlers can declare a priority that allows
 * them to be place in some order.
 *
 * @author Arutr Ventura (artur.ventura@tecnico.pt)
 */
public class Intersection {

    private static final Map<String, List<Intersection>> INTERSECTIONS = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(Intersection.class);

    private int priority = Integer.MAX_VALUE;
    private String location;
    private String position;

    private IntersectionHandler callback;

    private Intersection(String location, String position, int priority, IntersectionHandler callback) {
        this.priority = priority;
        this.location = location;
        this.position = position;
        this.callback = callback;
    }

    /**
     * This function will call all intersection handlers for a given location
     * and position, and pass them a set of arguments. Arguments allow
     * intersection points to have some context.
     *
     * @param writer
     *            the writer to where the result will be flushed
     * @param location
     *            the intersection point location name
     * @param position
     *            the intersection point position name
     * @param args
     *            a map of arguments.
     */
    public static void generate(final String location, final String position, Map<String, Object> args, final Writer writer) {
        String pos;
        if (position == null) {
            pos = "";
        } else {
            pos = position;
        }

        Map<String, Object> possibleArgs;
        if (args == null) {
            possibleArgs = new HashMap<>();
        } else {
            possibleArgs = args;
        }
        Map<String, Object> arguments = Collections.unmodifiableMap(possibleArgs);

        // First order by priority.
        Comparator<Intersection> priority = Comparator.comparingInt(x -> x.priority);

        // if two locations are equal, it should retain a stable order,
        // therefore ordering by location
        final Comparator<Intersection> comp =
                priority.thenComparing(x -> x.location + ":" + x.position, String.CASE_INSENSITIVE_ORDER);

        Optional.ofNullable(INTERSECTIONS.get(getKey(location, pos))).ifPresent(
                handlers -> handlers.stream().sorted(comp).map((x) -> {
                    try {
                        return x.callback.apply(new IntersectionEvent(location, pos, x.priority), arguments);
                    } catch (Throwable e) {
                        LOGGER.error("Error while processing partial", e);
                    }
                    return new StringView("");
                }).forEach(x -> {
                    try {
                        if (x != null) {
                            x.render(arguments, writer);
                        }
                    } catch (Exception e) {
                        LOGGER.error("Error while writing partial to writer", e);
                    }
                }));
    }

    private static String getKey(String location, String pos) {
        return location + "$$" + pos;
    }

    /**
     * This method allows to register a handler. Each handler must be registed
     * at a location and a optional position and priority. When invoking the
     * generation, priorities will be sorted from low to highest, meaning the
     * lowest priority will be executed first. The callback receives an event
     * and a set of arguments.
     *
     * @param location
     *            the intersection point location name
     * @param position
     *            the intersection point position name
     * @param priority
     *            the priority for this handler
     * @param callback
     *            the actualy callback
     */
    public static RegistrationHandler at(String location, String position, Integer priority, IntersectionHandler callback) {
        if (position == null) {
            position = "";
        }

        int pri;
        if (priority == null) {
            pri = Integer.MAX_VALUE;
        } else {
            pri = priority;
        }

        List<Intersection> callbacks = INTERSECTIONS.get(getKey(location, position));

        if (callbacks == null) {
            callbacks = new ArrayList<>();
            INTERSECTIONS.put(getKey(location, position), callbacks);
        }

        Intersection i = new Intersection(location, position, pri, callback);
        callbacks.add(i);
        return i.makeRegistrationHandler();
    }

    /**
     * This method allows to register a handler. Similar to
     * {@link org.fenixedu.bennu.rendering.Intersection#at(String, String, Integer, IntersectionHandler)} but default priority.
     *
     * @param location
     *            the intersection point location name
     * @param position
     *            the intersection point position name
     * @param callback
     *            the actualy callback
     */
    public static RegistrationHandler at(String location, String position, IntersectionHandler callback) {
        return at(location, position, Integer.MAX_VALUE, callback);
    }

    /**
     * This method allows to register a handler. Similar to
     * {@link org.fenixedu.bennu.rendering.Intersection#at(String, String, Integer, IntersectionHandler)} but default priority and
     * empty position.
     *
     * @param location
     *            the intersection point location name
     * @param callback
     *            the actualy callback
     */
    public static RegistrationHandler at(String location, IntersectionHandler callback) {
        return at(location, "", Integer.MAX_VALUE, callback);
    }

    private RegistrationHandler makeRegistrationHandler() {
        return new RegistrationHandler();
    }

    /**
     * This class serves a way to remove pragmatically remove handlers after
     * being declared.
     */
    public class RegistrationHandler {

        /**
         * This method removes the intersection point handler, that was
         * registered when this handler was created.
         */
        public void unregister() {
            List<Intersection> intersections = INTERSECTIONS.get(getKey(Intersection.this.location, Intersection.this.position));
            intersections.remove(Intersection.this);
        }
    }

    protected static void clear() {
        INTERSECTIONS.clear();
    }
}

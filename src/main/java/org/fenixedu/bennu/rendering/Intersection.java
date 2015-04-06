package org.fenixedu.bennu.rendering;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * This class is the main interface for doing template rendering. Each template declares places that wishes to possible
 * to add more stuff. Each intersection point is composed of location and a optional position. The location is supose to
 * be constant within the same template, and the position declares in what places can be added more html code (e.g.
 * location="cms.list.sites" position="management.buttons"). Handlers can declare a priority that allows them to be
 * place in some order.
 *
 * @author Arutr Ventura (artur.ventura@tecnico.pt)
 */
public class Intersection {

    private static final Map<String, List<Intersection>> INTERSECTORS = new HashMap<>();

    private int priority = Integer.MAX_VALUE;
    private String location;
    private String position;

    private BiFunction<IntersectionEvent, Map<String, Object>, String> callback;


    private Intersection(String location, String position, int priority, BiFunction<IntersectionEvent, Map<String, Object>, String> callback) {
        this.priority = priority;
        this.location = location;
        this.position = position;
        this.callback = callback;
    }

    /**
     * This function will call all intersection handlers for a given location and position, and pass them a set of arguments.
     * Arguments allow intersection points to have some context.
     *
     * @param location the intersection point location name
     * @param position the intersection point position name
     * @param args a map of arguments.
     * @return
     */
    public static String generate(final String location, final String position, Map<String, Object> args) {
        String pos;
        if (position == null){
            pos = "";
        }else{
            pos = position;
        }

        Map<String, Object> possibleArgs;
        if (args == null){
            possibleArgs = new HashMap<>();
        }else{
            possibleArgs = args;
        }
        
        return INTERSECTORS.get(location + "$$" + pos).stream().sorted(Comparator.comparingInt(x -> x.priority)).map((x) -> {
            try {
                return x.callback.apply(new IntersectionEvent(location, pos, x.priority), Collections.unmodifiableMap(possibleArgs));
            } catch (Throwable e) {

            }
            return "";
        }).collect(Collectors.joining(""));
    }


    /**
     * This method allows to register a handler. Each handler must be registed at a location and a optional position and
     * priority. When invoking the generation, priorities will be sorted from low to highest, meaning the lowest priority
     * will be executed first. The callback receives an event and a set of arguments.
     *
     * @param location the intersection point location name
     * @param position the intersection point position name
     * @param priority the priority for this handler
     * @param callback the actualy callback
     */
    public static void at(String location, String position, Integer priority, BiFunction<IntersectionEvent, Map<String, Object>, String> callback) {
        List<Intersection> callbacks = INTERSECTORS.get(location + "$$" + position);

        if (position == null) {
            position = "";
        }

        if (callbacks == null) {
            callbacks = new ArrayList<>();
            INTERSECTORS.put(location + "$$" + position, callbacks);
        }

        int pri;
        if (priority == null){
            pri = Integer.MAX_VALUE;
        }else{
            pri = priority;
        }

        callbacks.add(new Intersection(location, position, pri, callback));
    }

    /**
     * This method allows to register a handler. Similar to {@link org.fenixedu.bennu.rendering.Intersection#at(String, String, Integer, java.util.function.BiFunction)}
     * but default priority.
     *
     * @param location the intersection point location name
     * @param position the intersection point position name
     * @param callback the actualy callback
     */
    public static void at(String location, String position, BiFunction<IntersectionEvent, Map<String, Object>, String> callback) {
        at(location, position, Integer.MAX_VALUE, callback);
    }

    /**
     * This method allows to register a handler. Similar to {@link org.fenixedu.bennu.rendering.Intersection#at(String, String, Integer, java.util.function.BiFunction)}
     * but default priority and empty position.
     *
     * @param location the intersection point location name
     * @param callback the actualy callback
     */
    public static void at(String location, BiFunction<IntersectionEvent, Map<String, Object>, String> callback) {
        at(location, "", Integer.MAX_VALUE, callback);
    }
}






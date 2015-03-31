package org.fenixedu.bennu.rendering;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Created by borgez on 31-03-2015.
 */
public class Intersection {

    private static final Map<String, List<Intersection>> INTERSECTORS = new HashMap<>();

    private int priority = Integer.MAX_VALUE;
    private String location;
    private String position;

    private BiFunction<IntersectionEvent, Map<String, Object>, String> callback;

    public Intersection(String location, String position, int priority, BiFunction<IntersectionEvent, Map<String, Object>, String> callback) {
        this.priority = priority;
        this.location = location;
        this.position = position;
        this.callback = callback;
    }

    public static String generate(final String location, final String position, Map<String, Object> args) {
        return INTERSECTORS.get(location + "$$" + position).stream().sorted(Comparator.comparingInt(x -> x.priority)).map((x) -> {
            try {
                return x.callback.apply(new IntersectionEvent(location, position, x.priority), Collections.unmodifiableMap(args));
            } catch (Throwable e) {

            }
            return "";
        }).collect(Collectors.joining(""));
    }

    public static void at(String location, String position, int priority, BiFunction<IntersectionEvent, Map<String, Object>, String> callback) {
        List<Intersection> callbacks = INTERSECTORS.get(location + "$$" + position);

        if (position == null) {
            position = "";
        }

        if (callbacks == null) {
            callbacks = new ArrayList<>();
            INTERSECTORS.put(location + "$$" + position, callbacks);
        }

        callbacks.add(new Intersection(location, position, priority, callback));
    }

    public static void at(String intersect, String position, BiFunction<IntersectionEvent, Map<String, Object>, String> callback) {
        at(intersect, position, Integer.MAX_VALUE, callback);
    }

    public static void at(String intersect, BiFunction<IntersectionEvent, Map<String, Object>, String> callback) {
        at(intersect, "", Integer.MAX_VALUE, callback);
    }
}






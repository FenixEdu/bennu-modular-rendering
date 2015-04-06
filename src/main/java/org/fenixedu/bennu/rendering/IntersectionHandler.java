package org.fenixedu.bennu.rendering;

import org.fenixedu.bennu.rendering.view.View;

import java.util.Map;


@FunctionalInterface
public interface IntersectionHandler {
    public View apply(IntersectionEvent event, Map<String, Object> args);
}

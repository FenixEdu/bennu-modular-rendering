package org.fenixedu.bennu.rendering;

import java.util.Map;

import org.fenixedu.bennu.rendering.view.View;

@FunctionalInterface
public interface IntersectionHandler {
    public View apply(IntersectionEvent event, Map<String, Object> args);
}

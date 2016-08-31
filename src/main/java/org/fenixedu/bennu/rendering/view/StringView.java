package org.fenixedu.bennu.rendering.view;

import java.io.Writer;
import java.util.Map;

import org.fenixedu.bennu.rendering.IntersectionEvent;
import org.fenixedu.bennu.rendering.IntersectionHandler;

/**
 * This view is a simple implementation that allows a simple string to be returned
 *
 */
public class StringView implements View, IntersectionHandler {

    private final String partial;

    public StringView(String content) {
        this.partial = content;
    }

    @Override
    public void render(Map<String, Object> args, Writer writer) throws Exception {
        writer.append(partial);
    }

    @Override
    public View apply(IntersectionEvent intersectionEvent, Map<String, Object> stringObjectMap) {
        return this;
    }
}

package org.fenixedu.bennu.rendering.view;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

@FunctionalInterface
public interface View {
    public void render(Map<String, Object> args, Writer writer) throws Exception;
}

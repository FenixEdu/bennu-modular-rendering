package org.fenixedu.bennu.rendering.view.pebble;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.LoaderException;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.loader.StringLoader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import org.fenixedu.bennu.rendering.IntersectionEvent;
import org.fenixedu.bennu.rendering.IntersectionHandler;
import org.fenixedu.bennu.rendering.view.View;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PebbleView implements View, IntersectionHandler {

    private static final PebbleEngine FROM_FILES = new PebbleEngine(new ClasspathLoader() {
        @Override
        public Reader getReader(String templateName) throws LoaderException {
            InputStream stream = PebbleView.class.getResourceAsStream(templateName);
            if (stream != null) {
                return new InputStreamReader(stream);
            } else {
                throw new LoaderException(new FileNotFoundException(), "Template file '" + templateName + "' was not found");
            }
        }
    });

    private static final PebbleEngine WITH_CONTENT = new PebbleEngine(new StringLoader());

    private PebbleTemplate template;
    private Map<String, Object> extraArguments = Collections.emptyMap();

    private PebbleView(PebbleTemplate template) {
        this.template = template;
    }

    public static PebbleView create(PebbleTemplate template) {
        PebbleView pv = new PebbleView(template);
        return pv;
    }

    public static PebbleView create(PebbleTemplate template, Map<String, Object> extraArgs) {
        PebbleView pv = new PebbleView(template);
        pv.extraArguments = extraArgs;
        return pv;
    }

    public static PebbleView fromFile(String filePath) throws PebbleException {
        return new PebbleView(FROM_FILES.getTemplate(filePath));
    }

    public static PebbleView fromFile(String filePath, Map<String, Object> extraArgs) throws PebbleException {
        PebbleView pv = new PebbleView(FROM_FILES.getTemplate(filePath));
        pv.extraArguments = extraArgs;
        return pv;
    }

    public static PebbleView with(String content) throws PebbleException {
        return new PebbleView(WITH_CONTENT.getTemplate(content));
    }

    public static PebbleView with(String content, Map<String, Object> extraArgs) throws PebbleException {
        PebbleView pv = new PebbleView(WITH_CONTENT.getTemplate(content));
        pv.extraArguments = extraArgs;
        return pv;
    }

    public static PebbleView withEngine(String templateName, PebbleEngine engine) throws PebbleException {
        return new PebbleView(engine.getTemplate(templateName));
    }

    public static PebbleView withEngine(String templateName, PebbleEngine engine, Map<String, Object> extraArgs) throws PebbleException {
        PebbleView pv = new PebbleView(engine.getTemplate(templateName));
        pv.extraArguments = extraArgs;
        return pv;
    }

    @Override
    public View apply(IntersectionEvent event, Map<String, Object> args) {
        return this;
    }

    @Override
    public void render(Map<String, Object> args, Writer writer) throws Exception {
        Map<String, Object> actual = new HashMap<>(args);
        actual.putAll(extraArguments);
        template.evaluate(writer, args);
    }
}

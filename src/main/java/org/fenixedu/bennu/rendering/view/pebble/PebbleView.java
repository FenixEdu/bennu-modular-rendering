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
package org.fenixedu.bennu.rendering.view.pebble;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.fenixedu.bennu.rendering.IntersectionEvent;
import org.fenixedu.bennu.rendering.IntersectionHandler;
import org.fenixedu.bennu.rendering.view.View;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.LoaderException;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.loader.StringLoader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

public class PebbleView implements View, IntersectionHandler {

    private static final PebbleEngine FROM_FILES = new PebbleEngine.Builder().loader(new ClasspathLoader() {
        @Override
        public Reader getReader(String templateName) throws LoaderException {
            InputStream stream = PebbleView.class.getClassLoader().getResourceAsStream(templateName);
            if (stream != null) {
                return new InputStreamReader(stream);
            } else {
                throw new LoaderException(new FileNotFoundException(), "Template file '" + templateName + "' was not found");
            }
        }
    }).build();

    private static final PebbleEngine WITH_CONTENT = new PebbleEngine.Builder().loader(new StringLoader()).build();

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

    public static PebbleView withEngine(String templateName, PebbleEngine engine, Map<String, Object> extraArgs)
            throws PebbleException {
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
        template.evaluate(writer, actual);
    }
}

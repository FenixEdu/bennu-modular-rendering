package org.fenixedu.bennu.rendering;

import com.mitchellbosecke.pebble.error.PebbleException;
import org.fenixedu.bennu.rendering.view.StringView;
import org.fenixedu.bennu.rendering.view.pebble.PebbleView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

@HandlesTypes({BennuIntersection.class})
public class BennuIntersectionContextListner implements ServletContainerInitializer {
    private final static Logger LOGGER = LoggerFactory.getLogger(BennuIntersectionContextListner.class);

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        boolean hasPebble = isPebbleAvailable();

        for (Class s : set) {
            BennuIntersection b = (BennuIntersection) s.getAnnotation(BennuIntersection.class);
            if (IntersectionHandler.class.isAssignableFrom(s)) {
                try {
                    IntersectionHandler handler = (IntersectionHandler) s.newInstance();
                    Intersection.at(b.location(), b.position(), handler);
                } catch (Exception e) {
                    LOGGER.error("Error making an instance of IntersectionHandler", e);
                }
            } else {
                if (hasPebble) {
                    try {
                        Intersection.at(b.location(), b.position(), b.priority(), PebbleView.fromFile(b.file()));
                    } catch (PebbleException e) {
                        LOGGER.error("Error while loading pebble file '" + b.file() + "' for intersection", e);
                    }
                } else {

                    InputStream is = s.getResourceAsStream(b.file());
                    if (is != null) {
                        try {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                            StringBuilder out = new StringBuilder();
                            String content;
                            while ((content = reader.readLine()) != null) {
                                out.append(content);
                            }
                            content = out.toString();
                            Intersection.at(b.location(), b.position(), b.priority(), new StringView(content));
                        } catch (Exception e) {
                            LOGGER.error("Error while loading classpath file '" + b.file() + "' for intersection", e);
                        }

                    } else {
                        LOGGER.error("Error while loading classpath file '" + b.file() + "' for intersection");
                    }
                }

            }
        }
    }


    private boolean isPebbleAvailable() {
        try {
            Class.forName("com.mitchellbosecke.pebble.PebbleEngine");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}

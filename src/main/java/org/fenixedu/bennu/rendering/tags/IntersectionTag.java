package org.fenixedu.bennu.rendering.tags;

import org.fenixedu.bennu.rendering.Intersection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.HashMap;
import java.util.Map;

/**
 * This tag can be use to define a intersection point within a JSP file. If you need to add some arguments to the map
 * you should use the tag {@link org.fenixedu.bennu.rendering.tags.ArgTag} in the body of this tag.
 *
 * @author Artur Ventura (artur.ventura@tecnico.pt)
 */
public class IntersectionTag extends BodyTagSupport {
    private static final long serialVersionUID = 123L;
    private static final Logger LOGGER = LoggerFactory.getLogger(IntersectionTag.class);
    private String location;
    private String position;
    private Map<String, Object> args = new HashMap<>();

    /**
     * Returns the intersection point position.
     *
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the intersection point position
     *
     * @param position the position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Returns the intersection point position
     *
     * @return the position
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the intersection point location. Locations should be the same within the same JSP. Use the position
     * to define multiple position within the same JSP.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int doStartTag() throws JspException {
        return super.doStartTag();
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            Map<String, Object> argsmap = getArgs();
            argsmap.put("request", pageContext.getRequest());
            argsmap.put("response", pageContext.getResponse());
            Intersection.generate(getLocation(), getPosition(), argsmap, pageContext.getOut());
        } catch (Throwable e1) {
            LOGGER.error("Error while flushing intersection to JSP", e1);

        }
        return super.doEndTag();
    }

    /**
     * Returns the argument map for this tag.
     *
     * @return the argument map
     */
    public Map<String, Object> getArgs() {
        return args;
    }
}
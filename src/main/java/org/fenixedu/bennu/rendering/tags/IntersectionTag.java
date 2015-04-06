package org.fenixedu.bennu.rendering.tags;

import org.fenixedu.bennu.rendering.Intersection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.HashMap;
import java.util.Map;


public class IntersectionTag extends BodyTagSupport {
    private static final long serialVersionUID = 123L;
    private static final Logger LOGGER = LoggerFactory.getLogger(IntersectionTag.class);
    private String location;
    private String position;
    private Map<String, Object> args = new HashMap<>();

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLocation() {
        return location;
    }

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
            pageContext.getOut().print(Intersection.generate(getLocation(), getPosition(), argsmap));
        } catch (Throwable e1) {
            LOGGER.error("Error while flushing intersection to JSP", e1);

        }
        return super.doEndTag();
    }

    public Map<String, Object> getArgs() {
        return args;
    }
}
package org.fenixedu.bennu.rendering.tags;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * This tag is used to add arguments to the intersection point. This should be used in conjunction with a
 * intersection point.
 *
 * @author Artur Ventura (artur.ventura@tecnico.pt)
 */
public class ArgTag extends SimpleTagSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArgTag.class);
    private String key;
    private Object value;

    @Override
    public void doTag() throws JspException, IOException {
        JspTag parent = getParent();
        if (parent instanceof IntersectionTag) {
            IntersectionTag tag = (IntersectionTag) parent;
            tag.getArgs().put(getKey(), getValue());
        } else {
            LOGGER.warn("Parent of intersection argument tag is not a intersection tag");
        }
    }

    /**
     * Returns the key for this argument
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key for this argument
     *
     * @param key the key
     */
    public void setKey(String key) {
        this.key = key;
    }


    /**
     * Returns the value for this argument
     *
     * @return the value
     */
    public Object getValue() {
        return value;
    }


    /**
     * Sets the key for this argument
     *
     * @param value the argument
     */
    public void setValue(Object value) {
        this.value = value;
    }
}

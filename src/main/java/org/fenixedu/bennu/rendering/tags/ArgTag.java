package org.fenixedu.bennu.rendering.tags;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by borgez on 31-03-2015.
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

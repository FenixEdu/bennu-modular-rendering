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
package org.fenixedu.bennu.rendering.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

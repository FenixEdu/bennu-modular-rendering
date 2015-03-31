package org.fenixedu.bennu.rendering.tags;

/**
 * Created by borgez on 31-03-2015.
 */
/**
 * Copyright © 2002 Instituto Superior Técnico
 *
 * This file is part of FenixEdu Academic.
 *
 * FenixEdu Academic is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FenixEdu Academic is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FenixEdu Academic.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 *
 */


import org.fenixedu.bennu.rendering.Intersection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.HashMap;
import java.util.Map;


public class IntersectionTag extends BodyTagSupport {
    /**
     *
     */
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
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
package org.fenixedu.bennu.rendering.view;

import java.io.Writer;
import java.util.Map;

import org.fenixedu.bennu.rendering.IntersectionEvent;
import org.fenixedu.bennu.rendering.IntersectionHandler;

/**
 * This view is a simple implementation that allows a simple string to be returned
 *
 */
public class StringView implements View, IntersectionHandler {

    private final String partial;

    public StringView(String content) {
        this.partial = content;
    }

    @Override
    public void render(Map<String, Object> args, Writer writer) throws Exception {
        writer.append(partial);
    }

    @Override
    public View apply(IntersectionEvent intersectionEvent, Map<String, Object> stringObjectMap) {
        return this;
    }
}

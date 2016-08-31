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
package org.fenixedu.bennu.rendering;

import org.fenixedu.bennu.rendering.view.StringView;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;

/**
 * Created by borgez on 07-04-2015.
 */
@RunWith(JUnit4.class)
public class StringViewTestCase {

    @Test
    public void testEmptyStringView() throws Exception {
        Writer writer = new StringWriter();
        StringView emptryStringView = new StringView(new String());
        emptryStringView.render(Collections.emptyMap(), writer);
        Assert.assertTrue("string should be empty", writer.toString().isEmpty());
    }

    @Test
    public void testNonEmptyStringView() throws Exception {
        Writer writer = new StringWriter();
        final String content = "magic testing string";
        StringView stringView = new StringView(content);
        stringView.render(Collections.emptyMap(), writer);
        Assert.assertTrue("string content is not the same", content.equals(writer.toString()));
    }

}

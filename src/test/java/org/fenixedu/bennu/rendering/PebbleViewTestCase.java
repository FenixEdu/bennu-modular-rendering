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

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import org.fenixedu.bennu.rendering.view.pebble.PebbleView;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;

/**
 * Created by borgez on 07-04-2015.
 */
@RunWith(JUnit4.class)
public class PebbleViewTestCase {

    @Test
    public void testEmptyStaticContent() throws Exception {
        Writer writer = new StringWriter();
        final String content = new String();
        PebbleView view = PebbleView.with(content);
        view.render(Collections.emptyMap(), writer);
        String result = writer.toString();
        Assert.assertTrue("expected empty content but '" + result + "' was received", Strings.isNullOrEmpty(result));
    }

    @Test
    public void testStaticContent() throws Exception {
        Writer writer = new StringWriter();
        final String content = "TEST";
        PebbleView view = PebbleView.with(content);
        view.render(Collections.emptyMap(), writer);
        String result = writer.toString();
        Assert.assertTrue("expected content was '" + content + "' but '" + result + "' was received", content.equals(result));
    }

    @Test
    public void testStaticContentRenderArguments() throws Exception {
        Writer writer = new StringWriter();
        Map<String, Object> var1 = ImmutableMap.of("name", "default-name");
        Map<String, Object> var2 = ImmutableMap.of("age", 243);
        Map<String, Object> args = ImmutableMap.of("var1", var1, "var2", var2);

        final String expectedContent = String.format("user: %s, age: %d", var1.get("name"), var2.get("age"));

        final String content = "user: {{var1.name}}, age: {{var2.age}}";

        PebbleView view = PebbleView.with(content);

        view.render(args, writer);

        String result = writer.toString();

        Assert.assertTrue("expected content was '" + expectedContent + "' but '" + result + "' was received", expectedContent.equals(result));
    }

    @Test
    public void testStaticContentExtraArguments() throws Exception {
        Writer writer = new StringWriter();
        Map<String, Object> var1 = ImmutableMap.of("name", "default-name");
        Map<String, Object> var2 = ImmutableMap.of("age", 243);
        Map<String, Object> args = ImmutableMap.of("var1", var1, "var2", var2);

        final String expectedContent = String.format("user: %s, age: %d", var1.get("name"), var2.get("age"));

        final String content = "user: {{var1.name}}, age: {{var2.age}}";

        PebbleView view = PebbleView.with(content, args);

        view.render(Collections.emptyMap(), writer);

        String result = writer.toString();

        Assert.assertTrue("expected content was '" + expectedContent + "' but '" + result + "' was received", expectedContent.equals(result));
    }

    @Test
    public void testStaticFileContent() throws Exception {
        Writer writer = new StringWriter();
        final String content = "<button class=\"btn btn-default\">Button</button>";
        PebbleView view = PebbleView.fromFile("/test/staticButton.html");
        view.render(Collections.emptyMap(), writer);
        String result = writer.toString();
        Assert.assertTrue("expected content was '" + content + "' but '" + result + "' was received", content.equals(result));
    }

    @Test
    public void testDynamicFileContentRenderArguments() throws Exception {
        Writer writer = new StringWriter();
        Map<String, Object> var1 = ImmutableMap.of("name", "default-name");
        Map<String, Object> var2 = ImmutableMap.of("age", 243);
        Map<String, Object> args = ImmutableMap.of("var1", var1, "var2", var2);

        final String expectedContent = String.format("user: %s, age: %d", var1.get("name"), var2.get("age"));

        PebbleView view = PebbleView.fromFile("/test/dynamicUser.html");

        view.render(args, writer);

        String result = writer.toString();

        Assert.assertTrue("expected content was '" + expectedContent + "' but '" + result + "' was received", expectedContent.equals(result));
    }


    @Test
    public void testDynamicFileContentExtraArguments() throws Exception {
        Writer writer = new StringWriter();
        Map<String, Object> var1 = ImmutableMap.of("name", "default-name");
        Map<String, Object> var2 = ImmutableMap.of("age", 243);
        Map<String, Object> args = ImmutableMap.of("var1", var1, "var2", var2);

        final String expectedContent = String.format("user: %s, age: %d", var1.get("name"), var2.get("age"));

        PebbleView view = PebbleView.fromFile("/test/dynamicUser.html", args);

        view.render(Collections.emptyMap(), writer);

        String result = writer.toString();

        Assert.assertTrue("expected content was '" + expectedContent + "' but '" + result + "' was received", expectedContent.equals(result));
    }

}

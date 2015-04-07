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

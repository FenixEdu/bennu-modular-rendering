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

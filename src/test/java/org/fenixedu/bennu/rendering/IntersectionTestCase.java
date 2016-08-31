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
import org.fenixedu.bennu.rendering.view.StringView;
import org.junit.Assert;
import org.junit.Before;
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
public class IntersectionTestCase {

    @Before
    public void cleanup() {
        Intersection.clear();
    }

    @Test
    public void testEmptyIntersections() throws Exception {
        Writer writer = new StringWriter();
        Intersection.generate("foo.bar", "button", Collections.emptyMap(), writer);
        writer.close();
        Assert.assertTrue("invalid intersection generated content", writer.toString().isEmpty());
    }

    @Test
    public void testEmptyIntersectionsAfterRegistration() throws Exception {
        Writer writer = new StringWriter();
        Intersection.at("foo.bar", (event, args) -> new StringView("test"));
        Intersection.at("foo.bar", "xpto", (event, args) -> new StringView("test"));
        Intersection.generate("foo.bar", "button", Collections.emptyMap(), writer);
        writer.close();
        Assert.assertTrue("invalid intersection generated content", writer.toString().isEmpty());
    }

    @Test
    public void testRegisterIntersectionByLocation() throws Exception {
        final String content = "TEST";
        final Map<String, Object> defaultMap = ImmutableMap.of("k1", "v1", "k2", 2, "k3", new Boolean(false));
        Writer writer = new StringWriter();
        Intersection.at("foo.bar", (IntersectionEvent event, Map<String, Object> args) -> {
            Assert.assertTrue("arguments are not being passed properly to the handler",
                    args.keySet().containsAll(defaultMap.keySet()));

            Assert.assertTrue("arguments are not being passed properly to the handler",
                    args.values().containsAll(defaultMap.values()));

            return new StringView(content);
        });
        Intersection.generate("foo.bar", "", defaultMap, writer);
        writer.close();
        Assert.assertTrue("invalid intersection content", content.equals(writer.toString()));
    }


    @Test
    public void testRegisterIntersectionByPosition() throws Exception {
        final String content = "TEST";
        final Map<String, Object> defaultMap = ImmutableMap.of("k1", "v1", "k2", 2, "k3", new Boolean(false));
        Writer writer = new StringWriter();
        Intersection.at("foo.bar", "position", (IntersectionEvent event, Map<String, Object> args) -> {
            Assert.assertTrue("arguments are not being passed properly to the handler",
                    args.keySet().containsAll(defaultMap.keySet()));

            Assert.assertTrue("arguments are not being passed properly to the handler",
                    args.values().containsAll(defaultMap.values()));

            return new StringView(content);
        });
        Intersection.generate("foo.bar", "position", defaultMap, writer);
        writer.close();
        Assert.assertTrue("invalid intersection content", content.equals(writer.toString()));
    }

    @Test
    public void testRegisterIntersectionByPriority() throws Exception {
        final String content1 = "TEST1";
        final String content2 = "TEST2";
        final String content3 = "TEST3";
        final String content4 = "TEST4";
        final Map<String, Object> defaultMap = ImmutableMap.of("k1", "v1", "k2", 2, "k3", new Boolean(false));
        Writer writer = new StringWriter();

        Intersection.at("foo.bar", "position", 1, (event, args) -> new StringView(content1));
        Intersection.at("foo.bar", "position", 2, (event, args) -> new StringView(content2));
        Intersection.at("foo.bar", "position", (event, args) -> new StringView(content4));
        Intersection.at("foo.bar", "position", 3, (event, args) -> new StringView(content3));
        Intersection.generate("foo.bar", "position", defaultMap, writer);
        writer.close();

        String result = writer.toString();

        Assert.assertTrue("invalid intersection content", result.contains(content1) && result.contains(content2)
                && result.contains(content3) && result.contains(content4));

        Assert.assertTrue("result '" + result + "' should start by " + content1, result.startsWith(content1));

        Assert.assertTrue("result '" + result + "' should start by " + content1 + content2, result.startsWith(content1 + content2));

        Assert.assertTrue("result '" + result + "' should start by " + content1 + content2 + content3, result.startsWith(content1 + content2 + content3));

        Assert.assertTrue("result '" + result + "' should start by " + content4, result.endsWith(content4));

    }

    @Test
    public void testRegisterHandlerExistence() throws Exception {
        Intersection.RegistrationHandler handler = Intersection.at("foo.bar", "position", new StringView("xpto"));
        Assert.assertTrue("intersection registration must return a registration handler", handler != null);
    }

    @Test
    public void testRegisterHandlerDuplicates() throws Exception {
        Intersection.RegistrationHandler handler1 = Intersection.at("foo.bar", "position", new StringView("xpto"));
        Intersection.RegistrationHandler handler2 = Intersection.at("foo.bar", "position", new StringView("xpto"));
        Assert.assertTrue("different intersections must have different handlers", handler1 != handler2);
    }

    public void registrationHandlerUnregister() throws Exception {
        final StringWriter initialWriter = new StringWriter();
        Intersection.RegistrationHandler handler = Intersection.at("foo.bar", "position", new StringView("xpto"));
        Intersection.generate("foo.bar", "position", Collections.emptyMap(), initialWriter);

        Assert.assertTrue("StringView should output the content of the string", "xpto".equals(initialWriter.toString()));
        final StringWriter finalWriter = new StringWriter();
        handler.unregister();
        Intersection.generate("foo.bar", "position", Collections.emptyMap(), finalWriter);
        Assert.assertTrue("after unregister an intersection, it can't continue doing interceptions", Strings.isNullOrEmpty(initialWriter.toString()));
    }


    public void registrationHandlerUnregisterOne() throws Exception {
        final StringWriter initialWriter = new StringWriter();
        final String initialExpectedResult = "xpto0xpto1xpto2";
        final String finalExpectedResult = "xpto1xpto2";
        Intersection.RegistrationHandler handler = Intersection.at("foo.bar", "position", 0, new StringView("xpto0"));
        Intersection.at("foo.bar", "position", 1, new StringView("xpto1"));
        Intersection.at("foo.bar", "position", 2, new StringView("xpto2"));
        Intersection.generate("foo.bar", "position", Collections.emptyMap(), initialWriter);

        Assert.assertTrue("StringView should output the content of the string", initialExpectedResult.equals(initialWriter.toString()));
        final StringWriter finalWriter = new StringWriter();
        handler.unregister();
        Intersection.generate("foo.bar", "position", Collections.emptyMap(), finalWriter);
        Assert.assertTrue("after unregister an intersection, it can't continue doing interceptions", finalExpectedResult.equals(finalWriter.toString()));
    }

}

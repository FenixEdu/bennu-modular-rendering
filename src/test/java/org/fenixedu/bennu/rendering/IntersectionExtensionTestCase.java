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

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.fenixedu.bennu.rendering.view.StringView;
import org.fenixedu.bennu.rendering.view.pebble.IntersectionExtension;
import org.fenixedu.bennu.rendering.view.pebble.PebbleView;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.loader.StringLoader;

@RunWith(JUnit4.class)
public class IntersectionExtensionTestCase {

	PebbleEngine engine;

	@Before
	public void setUp() {
		engine = new PebbleEngine(new StringLoader());
		engine.addExtension(new IntersectionExtension());
	}

	@Test
	public void testSimpleIntersection() throws Exception {
		String test = "a{% intersect \"test\" %}{% endintersect %}c";

		String add = "b";

		String result = "abc";

		Intersection.at("test", new StringView(add));

		verify(test, result);
	}

	@Test
	public void testMoreIntersections() throws Exception {
		String test = "a{% intersect \"test\" %}{% endintersect %}d";

		String add = "b";
		String add2 = "c";
		String result = "abcd";

		Intersection.at("test", null, 1, new StringView(add));
		Intersection.at("test", null, 2, new StringView(add2));

		verify(test, result);
	}

	@Test
	public void testArgs() throws Exception {
		String test = "foo {% intersect \"test\" %}{% arg \"first\" \"hello\" %}{% endintersect %} baz";

		String add = "b";
		String add2 = "c";
		String result = "foo bar(hello) baz";

		String template = "bar({{ first }})";

		Intersection.at("test", null, 1,
				PebbleView.create(engine.getTemplate(template)));

		verify(test, result);
	}

	@Test
	public void testExec() throws Exception {
		String test = "foo {% intersect \"test\" %}{% for i in list %}{% arg i i %}{% endfor %}{% endintersect %} baz";

		String add = "b";
		String add2 = "c";
		String result = "foo bar(1,2,3,4,5) baz";

		String template = "bar({{ \"1\" }},{{ \"2\" }},{{ \"3\" }},{{ \"4\" }},{{ \"5\" }})";

		Intersection.at("test", null, 1,
				PebbleView.create(engine.getTemplate(template)));

		Writer writer = new StringWriter();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("list", new int[]{1,2,3,4,5});
		engine.getTemplate(test).evaluate(writer, map);
		writer.close();
		String got = writer.toString();
		Assert.assertTrue("invalid intersection generated content '" + got
				+ "', '" + result + "'", got.equals(result));
	}

	@Test
	public void testMultipleArgs() throws Exception {
		String test = "foo {% intersect \"test\" %}{% arg \"first\" \"hello\" %}{% arg \"second\" \"world\" %}{% arg \"third\" \"person\" %}{% endintersect %} baz";

		String add = "b";
		String add2 = "c";
		String result = "foo bar(hello,world,person) baz";

		String template = "bar({{ first }},{{ second}},{{ third}})";

		Intersection.at("test", null, 1,
				PebbleView.create(engine.getTemplate(template)));

		verify(test, result);
	}

	@Test
	public void testIgnoreInner() throws Exception {
		String test = "foo {% intersect \"test\" %}<dkjslkdjssadsad{% arg \"first\" \"hello\" %}asdkkajdlksad{% arg \"second\" \"world\" %}aksdjsladjalksd{% arg \"third\" \"person\" %}kalsdjslakdjsak{% endintersect %} baz";

		String add = "b";
		String add2 = "c";
		String result = "foo bar(hello,world,person) baz";

		String template = "bar({{ first }},{{ second }},{{ third}})";

		Intersection.at("test", null, 1,
				PebbleView.create(engine.getTemplate(template)));

		verify(test, result);
	}

	private void verify(String test, String result) throws PebbleException,
			IOException {
		Writer writer = new StringWriter();
		engine.getTemplate(test).evaluate(writer);
		writer.close();
		String got = writer.toString();
		Assert.assertTrue("invalid intersection generated content '" + got
				+ "', '" + result + "'", got.equals(result));
	}

	@After
	public void cleanup() {
		Intersection.clear();
	}
}

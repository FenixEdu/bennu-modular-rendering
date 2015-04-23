package org.fenixedu.bennu.rendering.view.pebble;

import com.google.common.collect.Lists;
import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.tokenParser.TokenParser;

import org.fenixedu.bennu.rendering.Intersection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nurv on 06/04/15.
 */
public class IntersectionExtension extends AbstractExtension{
	
	@Override
	public List<TokenParser> getTokenParsers() {
		return Lists.newArrayList(new IntersectTokenParser(), new ArgTokenParser());
	}
	
}

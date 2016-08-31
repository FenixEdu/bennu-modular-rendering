package org.fenixedu.bennu.rendering.view.pebble;

import java.util.List;

import com.google.common.collect.Lists;
import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.tokenParser.TokenParser;

/**
 * Created by nurv on 06/04/15.
 */
public class IntersectionExtension extends AbstractExtension {

    @Override
    public List<TokenParser> getTokenParsers() {
        return Lists.newArrayList(new IntersectTokenParser(), new ArgTokenParser());
    }

}

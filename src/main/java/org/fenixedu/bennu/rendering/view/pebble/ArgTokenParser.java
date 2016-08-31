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
package org.fenixedu.bennu.rendering.view.pebble;

import com.mitchellbosecke.pebble.error.ParserException;
import com.mitchellbosecke.pebble.lexer.Token;
import com.mitchellbosecke.pebble.lexer.TokenStream;
import com.mitchellbosecke.pebble.node.RenderableNode;
import com.mitchellbosecke.pebble.node.expression.Expression;
import com.mitchellbosecke.pebble.tokenParser.AbstractTokenParser;

public class ArgTokenParser extends AbstractTokenParser {

    @Override
    public String getTag() {
        return "arg";
    }

    @Override
    public RenderableNode parse(Token token) throws ParserException {
        TokenStream stream = this.parser.getStream();
        int lineNumber = token.getLineNumber();

        stream.next();

        Expression<?> key = this.parser.getExpressionParser().parseExpression();

        Expression<?> value = this.parser.getExpressionParser().parseExpression();

        stream.expect(Token.Type.EXECUTE_END);

        return new ArgNode(lineNumber, key, value);
    }

}

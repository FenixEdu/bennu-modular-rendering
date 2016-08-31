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
import com.mitchellbosecke.pebble.node.BodyNode;
import com.mitchellbosecke.pebble.node.RenderableNode;
import com.mitchellbosecke.pebble.node.expression.Expression;
import com.mitchellbosecke.pebble.parser.StoppingCondition;
import com.mitchellbosecke.pebble.tokenParser.AbstractTokenParser;

class IntersectTokenParser extends AbstractTokenParser {

    @Override
    public String getTag() {
        // TODO Auto-generated method stub
        return "intersect";
    }

    @Override
    public RenderableNode parse(Token token) throws ParserException {
        TokenStream stream = this.parser.getStream();
        int lineNumber = token.getLineNumber();

        stream.next();

        Expression<?> location = this.parser.getExpressionParser().parseExpression();

        Expression<?> position = null;
        Expression<?> priority = null;

        // skip over the 'location' expr to the name token
        Token positionToken = stream.current();

        // expect a name or string for the new block
        if (!positionToken.test(Token.Type.EXECUTE_END)) {
            position = this.parser.getExpressionParser().parseExpression();

            Token priorityToken = stream.current();

            if (!priorityToken.test(Token.Type.EXECUTE_END)) {
                priority = this.parser.getExpressionParser().parseExpression();
            }
        }

        stream.expect(Token.Type.EXECUTE_END);

        BodyNode intersectBody = this.parser.subparse(new StoppingCondition() {

            @Override
            public boolean evaluate(Token token) {
                return token.test(Token.Type.NAME, "endintersect");
            }
        });

        stream.next();

        stream.expect(Token.Type.EXECUTE_END);
        return new IntersectNode(lineNumber, location, position, priority, intersectBody);
    }

}

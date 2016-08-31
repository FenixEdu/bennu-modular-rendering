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

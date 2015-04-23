package org.fenixedu.bennu.rendering.view.pebble;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
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
		
		Expression<?> location = this.parser.getExpressionParser()
				.parseExpression();

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
		return new IntersectNode(lineNumber, location, position, priority,
				intersectBody);
	}

}

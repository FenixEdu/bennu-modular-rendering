package org.fenixedu.bennu.rendering.view.pebble;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.extension.NodeVisitor;
import com.mitchellbosecke.pebble.node.AbstractRenderableNode;
import com.mitchellbosecke.pebble.node.expression.Expression;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplateImpl;

class ArgNode extends AbstractRenderableNode {

	private Expression<?> key;
	private Expression<?> value;

	protected ArgNode(int lineNumber, Expression<?> key, Expression<?> value) {
		super(lineNumber);
		this.key = key;
		this.value = value;
	}

	@Override
	public void render(PebbleTemplateImpl self, Writer writer,
			EvaluationContext context) throws PebbleException, IOException {
		Object obj = context.get("intersect");
		if (obj != null) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) obj;

			Object obj2 = map.get("args");

			if (obj2 != null) {
				@SuppressWarnings("unchecked")
				Map<String, Object> args = (Map<String, Object>) obj2;

				Object keyObj = this.key.evaluate(self, context);
				if (keyObj == null) {
					throw new PebbleException(new NullPointerException(),
							"Missing key on Intersection Argument");
				}
				String key = keyObj.toString();

				Object value = this.value.evaluate(self, context);

				args.put(key, value);
			}
		}
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

}

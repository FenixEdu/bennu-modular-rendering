package org.fenixedu.bennu.rendering.view.pebble;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.fenixedu.bennu.rendering.Intersection;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.extension.NodeVisitor;
import com.mitchellbosecke.pebble.node.AbstractRenderableNode;
import com.mitchellbosecke.pebble.node.BodyNode;
import com.mitchellbosecke.pebble.node.expression.Expression;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplateImpl;

class IntersectNode extends AbstractRenderableNode {

    private static Writer NULL_WRITER = new Writer() {

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
        }

        @Override
        public void flush() throws IOException {
        }

        @Override
        public void close() throws IOException {
        }
    };

    private Expression<?> location;
    private Expression<?> position;
    private Expression<?> priority;
    private BodyNode body;

    protected IntersectNode(int lineNumber, Expression<?> location, Expression<?> position, Expression<?> priority, BodyNode body) {
        super(lineNumber);
        this.location = location;
        this.position = position;
        this.priority = priority;
        this.body = body;
    }

    @Override
    public void render(PebbleTemplateImpl self, Writer writer, EvaluationContext context) throws PebbleException, IOException {
        Map<String, Object> intersect = new HashMap<>();

        String location = null;

        if (this.location != null) {
            location =
                    Optional.ofNullable(this.location.evaluate(self, context))
                            .map(x -> x.toString())
                            .orElseThrow(
                                    () -> new PebbleException(new NullPointerException(), "Missing Location on Intersection"));
        } else {
            throw new PebbleException(new NullPointerException(), "Missing Location on Intersection");
        }

        String position = null;

        if (this.position != null) {
            position = Optional.ofNullable(this.location.evaluate(self, context)).map(x -> x.toString()).orElse(null);
        }

        String priority = null;

        if (this.priority != null) {
            priority = Optional.ofNullable(this.location.evaluate(self, context)).map(x -> x.toString()).orElse(null);
        }

        Map<String, Object> args = new HashMap<String, Object>();
        intersect.put("location", location);
        intersect.put("position", position);
        intersect.put("priority", priority);
        intersect.put("args", args);

        context.pushScope(null);
        context.put("intersect", intersect);
        body.render(self, NULL_WRITER, context);
        context.popScope();

        Intersection.generate(location, position, args, writer);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}

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

import java.io.IOException;
import java.io.Writer;
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
    public void render(PebbleTemplateImpl self, Writer writer, EvaluationContext context) throws PebbleException, IOException {
        Object obj = context.getScopeChain().get("intersect");
        if (obj != null) {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) obj;

            Object obj2 = map.get("args");

            if (obj2 != null) {
                @SuppressWarnings("unchecked")
                Map<String, Object> args = (Map<String, Object>) obj2;

                Object keyObj = this.key.evaluate(self, context);
                if (keyObj == null) {
                    throw new PebbleException(new NullPointerException(), "Missing key on Intersection Argument");
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

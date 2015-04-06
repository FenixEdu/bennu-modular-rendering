package org.fenixedu.bennu.rendering.annotations;

import java.lang.annotation.*;

/**
 * Created by nurv on 06/04/15.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(BennuIntersections.class)
public @interface BennuIntersection {
    String location();

    String position() default "";

    int priority() default Integer.MAX_VALUE;

    String file() default "";
}

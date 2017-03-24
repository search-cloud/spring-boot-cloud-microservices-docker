package org.asion.base.ddd.domain.annotations;

import java.lang.annotation.*;

/**
 *
 * @author Asion
 * @since 2017/3/20.
 * @see {org.springframework.context.event.EventListener}
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EventListener {

    /**
     * value.
     */
    Class<?>[] value() default {};

}
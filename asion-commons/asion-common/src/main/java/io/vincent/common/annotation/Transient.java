package io.vincent.common.annotation;

import java.lang.annotation.*;

/**
 * 标注实体字段，是不持久化的。
 *
 * @author Vincent
 * @since 1.0, 06/07/2018
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Documented
public @interface Transient {
}

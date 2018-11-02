package io.vincent.common.annotation;

import java.lang.annotation.*;

/**
 *
 * 标注一个类是实体类。
 *
 * @author Vincent
 * @since 1.0, 06/07/2018
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Entity {
}

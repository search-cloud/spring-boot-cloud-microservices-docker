package org.asion.webflux.test;

import org.asion.webflux.boot.AsionWebfluxAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

/**
 * @author Asion.
 * @since 16/12/12.
 */
@Configuration
@ComponentScan(value = "org.asion.webflux.mvc",
        excludeFilters = @ComponentScan.Filter(
                type = ASSIGNABLE_TYPE,
                classes = AsionWebfluxAutoConfiguration.class)
        )
public class AsionWebfluxWebTestsConfiguration {

}

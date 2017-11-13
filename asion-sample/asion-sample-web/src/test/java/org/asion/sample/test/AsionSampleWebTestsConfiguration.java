package org.asion.sample.test;

import org.asion.sample.boot.AsionSampleAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

/**
 * @author Asion.
 * @since 16/12/12.
 */
@Configuration
@ComponentScan(value = "org.asion.sample.mvc",
        excludeFilters = @ComponentScan.Filter(
                type = ASSIGNABLE_TYPE,
                classes = AsionSampleAutoConfiguration.class)
        )
public class AsionSampleWebTestsConfiguration {

}

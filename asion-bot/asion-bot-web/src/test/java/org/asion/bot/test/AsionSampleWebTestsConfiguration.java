package org.asion.bot.test;

import org.asion.bot.boot.AsionBotAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

/**
 * @author Asion.
 * @since 16/12/12.
 */
@Configuration
@ComponentScan(value = "org.asion.bot",
        excludeFilters = @ComponentScan.Filter(
                type = ASSIGNABLE_TYPE,
                classes = AsionBotAutoConfiguration.class)
        )
public class AsionSampleWebTestsConfiguration {

}

package org.asion.account.boot.security;

import org.asion.account.boot.AsionAccountAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

/**
 * @author Asion.
 * @since 8/25/16.
 */
@Configuration
@AutoConfigureAfter(AsionAccountAutoConfiguration.class)
public class AsionAccountSecurityAutoConfiguration {
}

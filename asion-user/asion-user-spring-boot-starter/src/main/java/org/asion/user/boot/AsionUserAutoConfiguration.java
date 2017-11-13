package org.asion.user.boot;

import org.mvnsearch.spring.boot.dubbo.DubboAutoConfiguration;
import org.mvnsearch.spring.boot.dubbo.DubboBasedAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * User auto configuration
 *
 * @author Asion.
 * @since 16/8/24.
 */
@Configuration
@EnableConfigurationProperties(AsionUserProperties.class)
@AutoConfigureAfter(DubboAutoConfiguration.class)
public class AsionUserAutoConfiguration extends DubboBasedAutoConfiguration {

    @Autowired
    private AsionUserProperties properties;

//    @Bean
//    public ReferenceBean asionUserManager() {
//        return getConsumerBean(UserManager.class, properties.getVersion(), properties.getTimeout());
//    }

}

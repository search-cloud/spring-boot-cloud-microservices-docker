package org.asion.security.boot;

import org.mvnsearch.spring.boot.dubbo.DubboAutoConfiguration;
import org.mvnsearch.spring.boot.dubbo.DubboBasedAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Account auto configuration
 * @author Asion.
 * @since 16/8/24.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableConfigurationProperties(AsionSecurityProperties.class)
@AutoConfigureAfter(DubboAutoConfiguration.class)
public class AsionSecurityAutoConfiguration extends DubboBasedAutoConfiguration {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private AsionSecurityProperties properties;

//    @Bean
//    public ReferenceBean asionAccountManager() {
//        return getConsumerBean(AccountManager.class, properties.getVersion(), properties.getTimeout());
//    }

}

package org.asion.account.boot;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import org.asion.account.domain.application.AccountManager;
import org.mvnsearch.spring.boot.dubbo.DubboAutoConfiguration;
import org.mvnsearch.spring.boot.dubbo.DubboBasedAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Account auto configuration
 * @author Asion.
 * @since 16/8/24.
 */
@Configuration
@EnableConfigurationProperties(AsionAccountProperties.class)
@AutoConfigureAfter(DubboAutoConfiguration.class)
public class AsionAccountAutoConfiguration extends DubboBasedAutoConfiguration {

    @Autowired
    private AsionAccountProperties properties;

    @Bean
    public ReferenceBean asionAccountManager() {
        return getConsumerBean(AccountManager.class, properties.getVersion(), properties.getTimeout());
    }

}

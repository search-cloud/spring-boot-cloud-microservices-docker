package org.asion.webflux.boot;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import org.asion.webflux.WebfluxManager;
import org.mvnsearch.spring.boot.dubbo.DubboAutoConfiguration;
import org.mvnsearch.spring.boot.dubbo.DubboBasedAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * sample auto configuration
 * @author Asion.
 * @since 16/5/29.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableConfigurationProperties(org.asion.webflux.boot.AsionWebfluxProperties.class)
@AutoConfigureAfter(DubboAutoConfiguration.class)
public class AsionWebfluxAutoConfiguration extends DubboBasedAutoConfiguration {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    private final org.asion.webflux.boot.AsionWebfluxProperties properties;

    @Autowired
    public AsionWebfluxAutoConfiguration(AsionWebfluxProperties properties) {this.properties = properties;}

    @Bean
    public ReferenceBean asionSampleManager() {
        return getConsumerBean(WebfluxManager.class, properties.getVersion(), properties.getTimeout());
    }

}

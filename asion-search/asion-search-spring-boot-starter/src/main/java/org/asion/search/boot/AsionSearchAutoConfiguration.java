package org.asion.search.boot;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import org.asion.search.SeekManger;
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
@Configuration
@EnableConfigurationProperties(AsionSearchProperties.class)
@AutoConfigureAfter(DubboAutoConfiguration.class)
public class AsionSearchAutoConfiguration extends DubboBasedAutoConfiguration {

    private final AsionSearchProperties properties;

    @Autowired
    public AsionSearchAutoConfiguration(AsionSearchProperties properties) {this.properties = properties;}

    @Bean
    public ReferenceBean asionSearchManager() {
        return getConsumerBean(SeekManger.class, properties.getVersion(), properties.getTimeout());
    }

}

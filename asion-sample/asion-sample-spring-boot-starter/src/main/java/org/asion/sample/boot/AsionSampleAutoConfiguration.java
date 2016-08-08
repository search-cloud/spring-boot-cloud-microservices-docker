package org.asion.sample.boot;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import org.asion.sample.SampleManager;
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
@EnableConfigurationProperties(AsionSampleProperties.class)
@AutoConfigureAfter(DubboAutoConfiguration.class)
public class AsionSampleAutoConfiguration extends DubboBasedAutoConfiguration {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private AsionSampleProperties properties;

    @Bean
    public ReferenceBean asionSampleManager() {
        return getConsumerBean(SampleManager.class, properties.getVersion(), properties.getTimeout());
    }

}

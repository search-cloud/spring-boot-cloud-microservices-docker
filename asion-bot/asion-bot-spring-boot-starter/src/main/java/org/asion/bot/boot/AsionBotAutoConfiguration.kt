package org.asion.bot.boot


import com.alibaba.dubbo.config.spring.ReferenceBean
import org.asion.bot.CaptureItemManger
import org.mvnsearch.spring.boot.dubbo.DubboAutoConfiguration
import org.mvnsearch.spring.boot.dubbo.DubboBasedAutoConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Asion Bot auto configuration
 *
 * @author Asion
 */
@Configuration
@EnableConfigurationProperties(AsionBotProperties::class)
@AutoConfigureAfter(DubboAutoConfiguration::class)
open class AsionBotAutoConfiguration @Autowired
constructor(private val properties: AsionBotProperties) : DubboBasedAutoConfiguration() {

    @Bean
    open fun captureItemManger(): ReferenceBean<CaptureItemManger> {
        return getConsumerBean(CaptureItemManger::class.java, properties.version, properties.timeout)
    }

}

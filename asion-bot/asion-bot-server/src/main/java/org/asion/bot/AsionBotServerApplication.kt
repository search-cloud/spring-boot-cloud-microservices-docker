package org.asion.bot

import org.mvnsearch.spring.boot.dubbo.EnableDubboConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.ComponentScan

/**
 * @author Asion.
 * @since 16/4/29.
 */
@SpringBootApplication
@EnableDubboConfiguration("org.asion.bot.service")
@ComponentScan("org.asion.bot")
@EnableEurekaClient
open class AsionBotServerApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(AsionBotServerApplication::class.java, *args)
        }
    }
}

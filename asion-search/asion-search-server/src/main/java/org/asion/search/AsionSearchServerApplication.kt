package org.asion.search

import org.mvnsearch.spring.boot.dubbo.EnableDubboConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories

/**
 * @author Asion.
 * @since 16/4/29.
 */
@SpringBootApplication
//@EnableEurekaClient
@ComponentScan("org.asion.search")
@EnableDubboConfiguration("org.asion.search.server")
@EnableElasticsearchRepositories("org.asion.search.repository")
open class AsionSearchServerApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(AsionSearchServerApplication::class.java, *args)
        }
    }

}


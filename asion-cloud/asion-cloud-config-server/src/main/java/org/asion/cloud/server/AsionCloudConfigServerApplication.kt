package org.asion.cloud.server

import org.springframework.boot.SpringApplication.run
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.config.server.EnableConfigServer
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
open class AsionCloudConfigServerApplication

fun main(args: Array<String>) {
    run(AsionCloudConfigServerApplication::class.java, *args)
}

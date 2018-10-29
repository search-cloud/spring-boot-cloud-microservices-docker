package org.asion.cloud.server

import org.springframework.boot.SpringApplication.run
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
@EnableEurekaClient
open class AsionCloudRegistryServerApplication

fun main(args: Array<String>) {
    run(AsionCloudRegistryServerApplication::class.java, *args)
}

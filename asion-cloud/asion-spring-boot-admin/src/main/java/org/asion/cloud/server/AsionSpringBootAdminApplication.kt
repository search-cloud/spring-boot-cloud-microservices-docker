package org.asion.cloud.server

import de.codecentric.boot.admin.config.EnableAdminServer
import org.springframework.boot.SpringApplication.run
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
@EnableAdminServer
open class AsionSpringBootAdminApplication

fun main(args: Array<String>) {
    run(AsionSpringBootAdminApplication::class.java, *args)
}

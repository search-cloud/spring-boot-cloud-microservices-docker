package org.asion.search

import org.sitemesh.builder.SiteMeshFilterBuilder
import org.sitemesh.config.ConfigurableSiteMeshFilter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.util.*
import javax.servlet.DispatcherType

@SpringBootApplication
@EnableWebMvc
@EnableAsync
@ComponentScan("org.asion.search")
open class AsionSearchWebApplication : WebMvcConfigurer {

    @Bean
    open fun siteMeshFilter(): FilterRegistrationBean<ConfigurableSiteMeshFilter> {
        val filterRegistrationBean = FilterRegistrationBean<ConfigurableSiteMeshFilter>()
        filterRegistrationBean.filter = object : ConfigurableSiteMeshFilter() {
            override fun applyCustomConfiguration(builder: SiteMeshFilterBuilder?) {
                builder!!.addExcludedPath("/jsondoc-ui.html")
                builder.addDecoratorPath("/*", "/static/layout.html")
            }
        }
        filterRegistrationBean.setDispatcherTypes(EnumSet.allOf(DispatcherType::class.java))
        return filterRegistrationBean
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry?) {
        if (!registry!!.hasMappingForPattern("/static/**")) {
            registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/")
        }
        if (!registry.hasMappingForPattern("/FEtest/**")) {
            registry.addResourceHandler("/FEtest/**").addResourceLocations("classpath:/FEtest/")
        }
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**").addResourceLocations(
                    "classpath:/META-INF/resources/webjars/")
        }
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(AsionSearchWebApplication::class.java, *args)
        }
    }

}

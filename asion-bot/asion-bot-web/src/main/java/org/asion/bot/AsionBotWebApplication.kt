package org.asion.bot

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

/**
 * @author Asion.
 * @since 16/5/29.
 */
@SpringBootApplication
@EnableWebMvc
@EnableAsync
@ComponentScan("org.asion.bot")
open class AsionBotWebApplication : WebMvcConfigurer {

    @Bean
    open fun siteMeshFilter(): FilterRegistrationBean<*> {
        val filterRegistrationBean = FilterRegistrationBean<ConfigurableSiteMeshFilter>()
        filterRegistrationBean.filter = object : ConfigurableSiteMeshFilter() {
            override fun applyCustomConfiguration(builder: SiteMeshFilterBuilder?) {
                // "/jsondoc-ui.html" 这个请求不使用模板页
//                builder!!.addExcludedPath("/jsondoc-ui.html")
                // 在"/*"下面所有的页面都使用模板页"/static/layout.html"
                builder!!.addDecoratorPath("/*", "/static/layout.html")
            }
        }
        filterRegistrationBean.setDispatcherTypes(EnumSet.allOf(DispatcherType::class.java))
        return filterRegistrationBean
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry?) {
        if (!registry!!.hasMappingForPattern("/static/**")) {
            registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/")
        }
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**").addResourceLocations(
                    "classpath:/META-INF/resources/webjars/")
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(AsionBotWebApplication::class.java, *args)
        }
    }

}

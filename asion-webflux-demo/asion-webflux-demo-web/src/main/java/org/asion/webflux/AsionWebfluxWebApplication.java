package org.asion.webflux;

import org.asion.webflux.flux.EchoHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author Asion.
 * @since 16/5/29.
 */
@SpringBootApplication
@EnableWebFlux
@EnableAsync
@ComponentScan("org.asion.webflux")
@EnableEurekaClient
public class AsionWebfluxWebApplication implements WebFluxConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(AsionWebfluxWebApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> monoRouterFunction(EchoHandler echoHandler) {
        return route(POST("/demo/{id}/flux"), echoHandler::findOne);
    }

//    @Bean
//    public FilterRegistrationBean<ConfigurableSiteMeshFilter> siteMeshFilter() {
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(new ConfigurableSiteMeshFilter() {
//            @Override
//            protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
//                // "/jsondoc-ui.html" 这个请求不使用模板页
////                builder.addExcludedPath("/jsondoc-ui.html");
//                // 在"/*"下面所有的页面都使用模板页"/static/layout.html"
//                builder.addDecoratorPath("/*", "/static/layout.html");
//            }
//        });
//        filterRegistrationBean.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
//        return filterRegistrationBean;
//    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        if (!registry.hasMappingForPattern("/static/**")) {
//            registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        }
//        if (!registry.hasMappingForPattern("/webjars/**")) {
//            registry.addResourceHandler("/webjars/**").addResourceLocations(
//                    "classpath:/META-INF/resources/webjars/");
//        }
//    }

}

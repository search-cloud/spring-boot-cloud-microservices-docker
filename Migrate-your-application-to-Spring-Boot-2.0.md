Spring Boot 2.0 Migration Guide
===============================

Andy Wilkinson edited this page 8 days ago · [74 revisions](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.0-Migration-Guide/_history)
This document is meant to help you migrate your application to Spring Boot 2.0 by providing thematic sections that mirror the developer guide.

Before You Start
================

First, Spring Boot 2.0 requires Java 8 or later. Java 6 and 7 are no longer supported. It also requires Spring Framework 5.0.
With Spring Boot 2.0, many configuration properties were renamed/removed and developers need to update their application.properties/application.yml accordingly. To help you with that, Spring Boot ships a new spring-boot-properties-migrator module. Once added as a dependency to your project, this will not only analyze your application's environment and print diagnostics at startup, but also temporarily migrate properties at runtime for you. This is a must have during your application migration:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-properties-migrator</artifactId>
    <scope>runtime</scope>
</dependency>
```
```
runtime("org.springframework.boot:spring-boot-properties-migrator")
```

|------|---------------------------------------------------------------------------------------------------------------|
| Note | Once you're done with the migration, please make sure to remove this module from your project's dependencies. |

<br />
If you wish to look into specifics, here's a curated list of resources - otherwise, proceed to the next sections:
*
  [Spring Boot 2.0.0 Release Notes](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.0.0-Release-Notes)
*
  [Running Spring Boot on Java 9](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-with-Java-9)
*
  [Upgrading to Spring Framework 5.0](https://github.com/spring-projects/spring-framework/wiki/Upgrading-to-Spring-Framework-5.x#upgrading-to-version-50)

Building Your Spring Boot Application
=====================================

Spring Boot Maven plugin
------------------------

The plugin configuration attributes that are exposed as properties now all start with a spring-boot prefix for consistency and to avoid clashes with other plugins.
For instance, the following command enables the prod profile using the command line:
mvn spring-boot:run -Dspring-boot.run.profiles=prod

### Surefire Defaults

Custom include/exclude patterns have been aligned to latest Surefire's defaults. If you were relying on ours, update your plugin configuration accordingly. They used to be as follows:
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <configuration>
        <includes>
            <include>**/*Tests.java</include>
            <include>**/*Test.java</include>
        </includes>
        <excludes>
            <exclude>**/Abstract*.java</exclude>
        </excludes>         
    </configuration>
</plugin>
```

|-----|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Tip | If you are using JUnit 5, you should [downgrade Surefire to](http://junit.org/junit5/docs/5.0.0/user-guide/#running-tests-build-maven) [2.19.1](http://junit.org/junit5/docs/5.0.0/user-guide/#running-tests-build-maven). The **/*Tests.java pattern is not included in this version so if you are relying on that, make sure to add it in your configuration. |

<br />

Spring Boot Gradle Plugin
-------------------------

Spring Boot's Gradle plugin has been largely rewritten to enable a number of significant improvements. You can read more about the plugin's capabilities in its [reference](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/gradle-plugin/reference) and [api](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/gradle-plugin/api) documentation.

### Dependency Management

Spring Boot's Gradle plugin no longer automatically applies the dependency management plugin. Instead, Spring Boot's plugin now reacts to the dependency management plugin being applied by importing the correct version of the spring-boot-dependencies BOM. This gives you more control over how and when dependency management is configured.
For most applications applying the dependency management plugin will be sufficient:
apply plugin: 'org.springframework.boot'
apply plugin: 'io.spring.dependency-management' // <-- add this to your build.gradle

|------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Note | The dependency management plugin remains a transitive dependency of spring-boot-gradle-plugin so there's no need for it to be listed as a classpath dependency in your buildscript configuration. |

<br />

### Building Executable Jars and Wars

The bootRepackage task has been replaced with bootJar and bootWar tasks for building executable jars and wars respectively. The jar and war tasks are no longer involved.

### Configuration Updates

The BootRun, BootJar, and BootWar tasks now all use mainClassName as the property to configure the name of the main class. This makes the three Boot-specific tasks consistent with each other, and also aligns them with Gradle's own application plugin.

Spring Boot Features
====================

Default Proxying Strategy
-------------------------

Spring Boot now uses CGLIB proxying by default, including for the AOP support. If you need proxy-based proxy, you'll need to set the spring.aop.proxy-target-class to false.

SpringApplication
-----------------

### Web Environment

Spring Boot applications can now operates in more modes so spring.main.web-environment property is now deprecated in favor of spring.main.web-application-type that provides more control.
If you want to make sure an application doesn't start a web server you'd have to change the property to:
spring.main.web-application-type=none

|-----|-----------------------------------------------------------------------------------------------------|
| Tip | there is also a setWebApplicationType on SpringApplication if you want to do that programmatically. |

<br />

### Spring Boot Application Events Changes

We've added a new event, ApplicationStartedEvent. ApplicationStartedEvent is sent after the context has been refreshed but before any application and command-line runners have been called. ApplicationReadyEvent is sent after any application and command-line runners have been called. It indicates that the application is ready to service requests.
See the [updated reference documentation](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#boot-features-application-events-and-listeners).

### Banner

In our effort to limit the number of root namespaces that Spring Boot uses, banner-related properties have been relocated to spring.banner.

Externalized Configuration
--------------------------

### Relaxed Binding

The rules related to relaxed binding [have been tightened](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#boot-features-external-config-relaxed-binding)
This new relaxed bindings as several advantages:
*
  There is no need to worry about the structure of the key in @ConditionalOnProperty: as long as the key is defined in the canonical format, the supported relaxed variants will work transparently. If you were using the prefix attribute you can now simply put the full key using the name or value attributes.
*
  RelaxedPropertyResolver is no longer available as the Environment takes care of that automatically: env.getProperty("com.foo.my-bar") will find a com.foo.myBar property.

The org.springframework.boot.bind package is no longer available and is replaced by the [new relaxed binding infrastructure](https://github.com/spring-projects/spring-boot/wiki/Relaxed-Binding-2.0). In particular, RelaxedDataBinder and friends have been replaced with a new Binder API. The following samples binds MyProperties from the app.acme prefix.
MyProperties target = Binder.get(environment)
.bind("app.acme", MyProperties.class)
.orElse(null);
As relaxed binding is now built-in, you can request any property without having to care about the case as long as it's using one of the supported formats:
FlagType flagType = Binder.get(environment)
.bind("acme.app.my-flag", FlagType.class)
.orElse(FlagType.DEFAULT);

### Binding on static methods

While binding on static properties (using a static getter and setter pair) works in Spring Boot 1.x, we never really intended to provide such feature and it is no longer possible as of Spring Boot 2.

### @ConfigurationProperties Validation

It is now mandatory that your @ConfigurationProperties object is annotated with @Validated if you want to turn on validation.

### Configuration Location

The behavior of the spring.config.location configuration has been fixed; it previously added a location to the list of default ones, now it replaces the default locations. If you were relying on the way it was handled previously, you should now use spring.config.additional-location instead.

Developing Web Applications
---------------------------

### Embedded containers package structure

In order to support reactive use cases, the embedded containers package structure has been refactored quite extensively. EmbeddedServletContainer has been renamed to WebServer and the org.springframework.boot.context.embedded package has been relocated to org.springframework.boot.web.server. Correspondingly, EmbeddedServletContainerCustomizer has been renamed to WebServerFactoryCustomizer.
For example, if you were customizing the embedded Tomcat container using the TomcatEmbeddedServletContainerFactory callback interface, you should now use TomcatServletWebServerFactory and if you were using an EmbeddedServletContainerCustomizer bean, you should now use a WebServerFactoryCustomizer<TomcatServletWebServerFactory> bean.

### Servlet-specific server properties

A number of server.* properties that are Servlet-specific have moved to server.servlet:

|------------------------------|--------------------------------------|
| Old property                 | New property                         |
| server.context-parameters.*  | server.servlet.context-parameters.*  |
| server.context-path          | server.servlet.context-path          |
| server.jsp.class-name        | server.servlet.jsp.class-name        |
| server.jsp.init-parameters.* | server.servlet.jsp.init-parameters.* |
| server.jsp.registered        | server.servlet.jsp.registered        |
| server.servlet-path          | server.servlet.path                  |

<br />

### Web Starter as a Transitive Dependency

Previously several Spring Boot starters were transitively depending on Spring MVC with spring-boot-starter-web. With the new support of Spring WebFlux, spring-boot-starter-mustache, spring-boot-starter-freemarker and spring-boot-starter-thymeleaf are not depending on it anymore. It is the developer's responsibility to choose and add spring-boot-starter-web or spring-boot-starter-webflux.

### Template Engines

#### Thymeleaf

The Thymeleaf starter included the thymeleaf-layout-dialect dependency previously. Since Thymeleaf 3.0 now offers a \[native way to implement layouts\](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#template-layout), we removed that mandatory dependency and leave this choice up to you. If your application is relying on the layout-dialect project, please add it explicitly as a dependency.

#### Mustache Templates Default File Extension

The default file extension for Mustache templates was .html, it is now .mustache to align with the official spec and most IDE plugins. You can override this new default by changing the spring.mustache.suffix configuration key.

### Jackson / JSON Support

In 2.0, we've flipped a Jackson configuration default to write JSR-310 dates as ISO-8601 strings. If you wish to return to the previous behavior, you can add spring.jackson.serialization.write-dates-as-timestamps=true to your configuration.
A new spring-boot-starter-json starter gathers the necessary bits to read and write JSON. It provides not only jackson-databind but also useful modules when working with Java8: jackson-datatype-jdk8, jackson-datatype-jsr310 and jackson-module-parameter-names. If you were manually depending on those modules, you can now depend on this new starter instead.

### Spring MVC Path Matching Default Behavior Change

We've decided to change the default for suffix path matching in Spring MVC applications (see [#11105](https://github.com/spring-projects/spring-boot/issues/11105)). This feature is not enabled by default anymore, following a [best practice documented in Spring Framework](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-requestmapping-suffix-pattern-match).
If your application expects requests like "GET /projects/spring-boot.json" to be mapped to @GetMapping("/projects/spring-boot") mappings, this change is affecting you.
For more information about this and how to mitigate that change, check out [the reference documentation about path matching and content negotiation in Spring Boot](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/html/boot-features-developing-web-applications.html#boot-features-spring-mvc-pathmatch).

### Servlet Filters

The default dispatcher types for a Servlet Filter are now DipatcherType.REQUEST; this aligns Spring Boot's default with the Servlet specification's default. If you wish to map a filter to other dispatcher types, please register your Filter using a FilterRegistrationBean.

|------|-----------------------------------------------------------------------------------------------------------|
| Note | Spring Security and Spring Session filters are configured for ASYNC, ERROR, and REQUEST dispatcher types. |

<br />

### RestTemplateBuilder

The requestFactory(ClientHttpRequestFactory) method has been replaced by a new requestFactory(Supplier<ClientHttpRequestFactory> requestFactorySupplier) method. The use of a Supplier allows every template produced by the builder to use its own request factory, thereby avoiding side-effects that can be caused by sharing a factory. See [#11255](https://github.com/spring-projects/spring-boot/issues/11255).

### WebJars Locator

Spring Boot 1.x used and provided dependency management for org.webjars:webjars-locator. webjars-locator is a ["poorly named library ... that wraps the](https://github.com/webjars/webjars-locator/tree/ec6b793f2d6f031f6aa384a06c059ff499055652) [webjars-locator-core](https://github.com/webjars/webjars-locator/tree/ec6b793f2d6f031f6aa384a06c059ff499055652) project". Dependencies on org.webjars:webjars-locator should be updated to use org.webjars:webjars-locator-core instead.

Security
--------

Spring Boot 2 greatly simplifies the default security configuration and makes adding custom security easy. Rather than having several security-related auto-configurations, Spring Boot now has a single behavior that backs off as soon as you add your own WebSecurityConfigurerAdapter.
You are affected if you were using any of the following properties:
security.basic.authorize-mode
security.basic.enabled
security.basic.path
security.basic.realm
security.enable-csrf
security.headers.cache
security.headers.content-security-policy
security.headers.content-security-policy-mode
security.headers.content-type
security.headers.frame
security.headers.hsts
security.headers.xss
security.ignored
security.require-ssl
security.sessions

|-----|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Tip | If you are or if you want to know more about those changes, refer to the [Security migration use cases](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-Security-2.0) wiki page. |

<br />

### Default Security

The security auto-configuration no longer exposes options and uses Spring Security defaults as much as possible. One noticeable side effect of that is the use of Spring Security's content negotiation for authorization (form login).

### Default User

Spring Boot configures a single user with a generated password, by default. The user can be configured using properties under spring.security.user.*. To customize the user further or add other users, you will have to expose a UserDetailsService bean instead. [This sample demonstrates how to do it](https://github.com/spring-projects/spring-boot/blob/424793d806006144726796b7a656dbaf783d59c6/spring-boot-samples/spring-boot-sample-secure/src/main/java/sample/secure/SampleSecureApplication.java#L41).
To disable default user creation, provide a bean of type AuthenticationManager, AuthenticationProvider or UserDetailsService.

|------|----------------------------------------------------------------------------------------------------------------------------------|
| Note | Autowiring an AuthenticationManagerBuilder into a method in a configuration class does not disable creation of the default user. |

<br />

### AuthenticationManager Bean

If you want to expose Spring Security's AuthenticationManager as a bean, override the authenticationManagerBean method on your WebSecurityConfigurerAdapter and annotate it with @Bean.

### OAuth2

Functionality from the [Spring Security OAuth project](http://projects.spring.io/spring-security-oauth/) is being migrated to core [Spring Security](https://projects.spring.io/spring-security/). Dependency management is no longer provided for that dependency and Spring Boot 2 provides [OAuth 2.0 client support](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#boot-features-security-oauth2) via Spring Security 5.
If you depend on Spring Security OAuth features that have not yet been migrated, you will need to add a dependency on an additional jar, check [the documentation](https://docs.spring.io/spring-security-oauth2-boot/docs/current/reference/htmlsingle/) for more details. We're also continuing to support Spring Boot 1.5 so older applications can continue to use that until an upgrade path is provided.

### Actuator Security

There is no longer a separate security auto-configuration for the Actuator (management.security.* property are gone). The sensitive flag of each endpoint is also gone to make things more explicit in the security configuration. If you were relying to this behavior, you need to create or adapt your security configuration to secure endpoints with the role of your choice.
For instance, assuming the following config
endpoints.flyway.sensitive=false
endpoints.info.sensitive=true
management.security.roles=MY_ADMIN
http
.authorizeRequests()
.requestMatchers(EndpointRequest.to("health", "flyway")).permitAll()
.requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("MY_ADMIN")
...
Note that in 2.x, health and info are enabled by default (with health details not shown by default). To be consistent with those new defaults, health has been added to the first matcher.

Working with SQL Databases
--------------------------

### Configuring a DataSource

The default connection pool has switched from Tomcat to HikariCP. If you used spring.datasource.type to force the use of Hikari in a Tomcat-based application, you can now remove that override.
In particular, if you had such setup:
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.apache.tomcat</groupId>
            <artifactId>tomcat-jdbc</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<dependency>
    <groupId>com.zaxxer</groupId>
    <artifactId>HikariCP</artifactId>
</dependency>
```
you can now replace it with:
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

### WARN Message for Implicit 'open-in-view'

From now on, applications that don't explicitly enable spring.jpa.open-in-view will get a WARN message during startup. While this behavior is a friendly default, this can lead to issues if you're not fully aware of what's that doing for you. This message makes sure that you understand that database queries may be performed during view rendering. If you're fine with that, you can configure explicitly this property to silence the warning message.

### JPA

In Spring Boot 1.x, some users were extending from HibernateJpaAutoConfiguration to apply advanced customizations to the auto-configured EntityManagerFactory. To prevent such faulty use case from happening, it is no longer possible to extend from it in Spring Boot 2.
To support those use cases, you can now define a HibernatePropertiesCustomizer bean that gives you full control over Hibernate properties, including the ability to register Hibernate interceptor declared as beans in the context.

#### Id generator

The spring.jpa.hibernate.use-new-id-generator-mappings property is now true by default to align with the default behaviour of Hibernate. If you need to temporarily restore this now deprecated behaviour, set the property to false.

### Flyway

Flyway configuration keys were moved to the spring namespace (i.e. spring.flyway)
Upgrading to Spring Boot 2 will upgrade Flyway from 3.x to 5.x. To make sure that the schema upgrade goes smoothly, please follow the following instructions:
*
  First upgrade your 1.5.x Spring Boot application to Flyway 4 (4.2.0 at the time of writing), see the instructions for [Maven](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#using-boot-maven-parent-pom) and [Gradle](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/gradle-plugin/reference/html//#managing-dependencies-customizing).
*
  Once your schema has been upgraded to Flyway 4, upgrade to Spring Boot 2 and run the migration again to port your application to Flyway 5.

### Liquibase

Liquibase configuration keys were moved to the spring namespace (i.e. spring.liquibase)

### Database Initialization

Basic DataSource initialization is now only enabled for embedded data sources and will switch off as soon as you're using a production database. The new spring.datasource.initialization-mode (replacing spring.datasource.initialize) offers more control.

### Updated Default 'create-drop' Handling

The spring.jpa.hibernate.ddl-auto property defaults to create-drop with an embedded database only if no schema manager, such as Liquibase or Flyway, is in use. As soon as a schema manager is detected, the default changes to none.

Working with NoSQL Technologies
-------------------------------

### Redis

[Lettuce](https://lettuce.io/) is now used instead of [Jedis](https://github.com/xetorthio/jedis) as the Redis driver when you use spring-boot-starter-redis. If you are using higher level Spring Data constructs you should find that the change is transparent.
We still support Jedis. Switch dependencies if you prefer Jedis by excluding io.lettuce:lettuce-core and adding redis.clients:jedis instead.
Connection pooling is optional and, if you are using it, you now need to add commons-pool2 yourself as Lettuce, contrary to Jedis, does not bring it transitively.

### Elasticsearch

Elasticsearch has been upgraded to 5.4+. In line with [Elastic's announcement that embedded Elasticsearch is no longer supported](https://www.elastic.co/blog/elasticsearch-the-server), auto-configuration of a NodeClient has been removed. A TransportClient can be auto-configured by using spring.data.elasticsearch.cluster-nodes to provide the addresses of one or more nodes to connect to.

Caching
-------

### Dedicated Hazelcast Auto-configuration for Caching

It is no longer possible to auto-configure both a general HazelcastInstance and a dedicated HazelcastInstance for caching. As a result, the spring.cache.hazelcast.config property is no longer available.

Batch
-----

The CommandLineRunner that executes batch jobs on startup has an order of 0.

Testing
-------

### Mockito 1.x

Mockito 1.x is no longer supported for @MockBean and @SpyBean. If you don't use spring-boot-starter-test to manage your dependencies you should upgrade to Mockito 2.x.

Spring Data
-----------

Spring Data Kay [renamed a number of its CRUD repository methods](https://spring.io/blog/2017/06/20/a-preview-on-spring-data-kay#improved-naming-for-crud-repository-methods). Application code calling the renamed methods will have to be updated. To ease the migration, you may want to consider using a custom CrudRepository sub-interface that declares deprecated default methods that use the old names and delegate to the equivalent newly named method. Marking the default methods has deprecated will help to ensure that the migration is not forgotten.

Spring Boot Actuator
====================

Spring Boot 2 brings important changes to the actuator, both internal and user-facing, please check the [updated section in the reference guide](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#production-ready) and the [new Actuator API documentation](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/actuator-api/html).
You should expect changes in the programming model, configuration keys and the response format of some endpoints. Actuator is now natively supported on Spring MVC, Spring WebFlux and Jersey.

Build
-----

The code of the Actuator has been split in two modules: the existing spring-boot-actuator and a new spring-boot-actuator-autoconfigure. If you were importing the actuator using its original module (spring-boot-actuator), please consider using the spring-boot-starter-actuator starter instead.

Configuration Keys Structure
----------------------------

Endpoints infrastructure key have been harmonized:

|-------------------------|----------------------------------------|
| Old property            | New property                           |
| endpoints.<id>.*        | management.endpoint.<id>.*             |
| endpoints.cors.*        | management.endpoints.web.cors.*        |
| endpoints.jmx.*         | management.endpoints.jmx.*             |
| management.address      | management.server.address              |
| management.context-path | management.server.servlet.context-path |
| management.ssl.*        | management.server.ssl.*                |
| management.port         | management.server.port                 |

<br />

Base Path
---------

All endpoints have moved to /actuator by default.
We fixed the meaning of management.server.servlet.context-path: it is now the endpoint management equivalent of server.servlet.context-path (only active when management.server.port is set). Additionally, you can also set the base path for the management endpoints with a new, separate property: management.endpoints.web.base-path.
For example, if you've set management.server.servlet.context-path=/management and management.endpoints.web.base-path=/application, you'll be able to reach the health endpoint at the following path: /management/application/health.
If you want to restore the behavior of 1.x (i.e. having /health instead of /actuator/health), set the following property:
management.endpoints.web.base-path=/

Audit Event API Change
----------------------

AuditEventRepository now has a single method with all optional arguments.

Endpoints
---------

To make an actuator endpoint available via HTTP, it needs to be both enabled and exposed. By default:
*
  Only the /health and /info endpoints are exposed, regardless of Spring Security being present and configured in your application.
*
  All endpoints but /shutdown are enabled.

You can expose all endpoints as follows:
management.endpoints.web.exposure.include=*
You can explicitly enable the /shutdown endpoint with:
management.endpoint.shutdown.enabled=true
To expose all (enabled) web endpoints but the env endpoint:
management.endpoints.web.exposure.include=*
management.endpoints.web.exposure.exclude=env

|--------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1.x Endpoint | Changes                                                                                                                                                                                                                                                                           |
| /actuator    | No longer available. There is, however, a mapping available at the root of management.endpoints.web.base-path that provides links to all the exposed endpoints.                                                                                                                   |
| /auditevents | The after parameter is no longer required                                                                                                                                                                                                                                         |
| /autoconfig  | Renamed to /conditions                                                                                                                                                                                                                                                            |
| /docs        | No longer available (the [API documentation](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/actuator-api/html) is part of the published documentation now)                                                                                                              |
| /health      | Rather than relying on the sensitive flag to figure out if the health endpoint had to show full details or not, there is now a management.endpoint.health.show-details option: never, always, when-authorized. By default, /actuator/health is exposed and details are not shown. |
| /trace       | Renamed to /httptrace                                                                                                                                                                                                                                                             |

Endpoint properties have changed as follows:
*
  endpoints.<id>.enabled has moved to management.endpoint.<id>.enabled
*
  endpoints.<id>.id has no replacement (the id of an endpoint is no longer configurable)
*
  endpoints.<id>.sensitive has no replacement (See [Actuator Security](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.0-Migration-Guide#actuator-security))
*
  endpoints.<id>.path has moved to management.endpoints.web.path-mapping.<id>

Endpoint Format
---------------

### Overhaul of the "/actuator/mappings" Actuator Endpoint

The JSON format has changed to now properly include information about context hierarchies, multiple DispatcherServlets, deployed Servlets and Servlet filters. See [#9979](https://github.com/spring-projects/spring-boot/issues/9979#issuecomment-357930821) for more details.
The [relevant section](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/actuator-api/html/#mappings) of the Actuator API documentation provides a sample document.

### Overhaul of the "/actuator/httptrace" Actuator Endpoint

The structure of the response has been refined to reflect the endpoint's focus on tracing HTTP request-response exchanges. More details about the endpoint and its response structure can be found in the [relevant section](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/actuator-api/html/#http-trace) of the Actuator API documentation.

Migrate Custom Endpoints
------------------------

If you have custom actuator endpoints, please [check out the dedicated blog post](https://spring.io/blog/2017/08/22/introducing-actuator-endpoints-in-spring-boot-2-0). The team also wrote a wiki page that describes [how to migrate your existing Actuator endpoints](https://github.com/spring-projects/spring-boot/wiki/Migrating-a-custom-Actuator-endpoint-to-Spring-Boot-2) to the new infrastructure.

Metrics
=======

Spring Boot's own metrics have been replaced with support, including auto-configuration, for [Micrometer](https://micrometer.io/) and dimensional metrics.

Setting up Micrometer
---------------------

If your Spring Boot 2.0 application already depends on Actuator, Micrometer is already here and auto-configured. If you wish to export metrics to an external registry like Prometheus, Atlas or Datadog, Micrometer provides dependencies for many registries; you can then configure your application with spring.metrics.* properties to export to a particular registry.
For more on this, check out the [Micrometer documentation about Spring Boot 2.0](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#production-ready-metrics).

Migrating Custom Counters/Gauges
--------------------------------

Instead of injecting CounterService or GaugeService instances in your application code, you can create various metrics by:
*
  Injecting a MeterRegistry and calling methods on it.
*
  Directly calling static methods like Counter featureCounter = Metrics.counter("feature");.

Micrometer brings many interesting features - check out the [core concepts behind Micrometer](http://micrometer.io/docs/concepts) and the [specifics about Spring Boot integration](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle#production-ready-metrics).

Spring Boot 1.5 Support
-----------------------

You can plug existing Spring Boot 1.5 applications in the same metrics infrastructure by using the [Micrometer Legacy support](http://micrometer.io/docs/ref/spring/1.5).

Developer Tools
===============

Hot swapping
------------

As the Spring Loaded project has been moved to the attic, its support in Spring Boot has been removed. We advise to use Devtools instead.

Devtools Remote Debug Tunnel
----------------------------

The support for tunnelling remote debugging over HTTP has been removed from Devtools.

Removed Features
================

The following features are no longer available:
*
  CRaSH support
*
  Auto-configuration and dependency management for Spring Mobile.
*
  Auto-configuration and dependency management for Spring Social. Please check the [Spring Social project](https://github.com/spring-projects/spring-social) for more details.
*
  Dependency management for commons-digester.

Dependency Versions
===================

The minimum supported version of the following libraries has changed:
*
  Elasticsearch 5.6
*
  Gradle 4
*
  Hibernate 5.2
*
  Jetty 9.4
*
  Spring Framework 5
*
  Spring Security 5
*
  Tomcat 8.5


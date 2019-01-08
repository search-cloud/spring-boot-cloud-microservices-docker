import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

var selenium_version: String by extra("3.12.0")
var selenium_htmlunit_version: String by extra("2.29.0")
//val unitilsBom: String by extra("3.4.2")
//val assertjVersion: String by extra("3.6.2")
//val mybatisSpringBootVersion: String by extra("1.3.2")
//val lombokVersion: String by extra("1.18.4")
//val dubboStarterVersion: String by extra("0.2.0")

/**
 * asion-bot"s sub projects configuration
 */
project("asion-bot-common") {
    dependencies {
        //compile("org.asion:asion-base:${version}")
        compile(project(":asion-base"))
        compileOnly("org.projectlombok:lombok")
    }
}

/**
 * asion bot spring boot starter
 */
project("asion-bot-spring-boot-starter") {
    dependencies {
        //compile("org.asion:asion-bot-common:${version}")
        compile(project(":asion-bot:asion-bot-common"))
        compile("com.ytx.common:ytx-common:1.0.0-SNAPSHOT")

        compile("org.mvnsearch.spring.boot:spring-boot-starter-dubbo:2.0.0-SNAPSHOT")
        compile("org.springframework.boot:spring-boot-starter-actuator")
        compile("org.springframework.boot:spring-boot-actuator-autoconfigure")
        compile("org.springframework.boot:spring-boot-configuration-processor")
    }
}

project("asion-bot-core") {
    dependencies {
        //compile("org.asion:asion-bot-common:${version}")
        compile(project(":asion-bot:asion-bot-common"))

        compile("org.springframework:spring-context-support")
        compile("org.mybatis.spring.boot:mybatis-spring-boot-starter:${ext["mybatisSpringBootVersion"]}")
        compile("org.springframework.boot:spring-boot-starter-jdbc")
        compile("org.springframework.boot:spring-boot-starter-data-jpa")
        compile("mysql:mysql-connector-java")
        compile("com.h2database:h2")

        compile("org.projectlombok:lombok:${ext["lombokVersion"]}")
        testCompile("org.springframework.boot:spring-boot-starter-test")
        testCompile("org.assertj:assertj-core:${ext["assertjVersion"]}")
        testCompile("org.unitils:unitils-bom:${ext["unitilsBom"]}")
    }
}

project("asion-bot-server") {
    apply(plugin = "org.springframework.boot")

    dependencies {
        //compile("asion-bot:asion-bot-core")
        compile(project(":asion-bot:asion-bot-core"))
        compile("org.mvnsearch.spring.boot:spring-boot-starter-dubbo:2.0.0-SNAPSHOT")
        compile("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
//        compile("org.springframework.cloud:spring-cloud-starter-config")
        compile("de.codecentric:spring-boot-admin-starter-client:${ext["springBootAdminVersion"]}")
        compile("org.jolokia:jolokia-core")

        compile("org.springframework.boot:spring-boot-starter-actuator")
        compile("org.springframework.boot:spring-boot-starter-jdbc")
        compile("mysql:mysql-connector-java")
        compile("com.h2database:h2")
        compile("redis.clients:jedis")

        compile("org.springframework.boot:spring-boot-starter-web")
        compile("org.springframework.boot:spring-boot-starter-thymeleaf")

//        testCompile("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.named<BootJar>("bootJar") {
        mainClassName = "org.asion.bot.AsionBotServerApplication"
    }

    tasks.named<BootRun>("bootRun") {
        main = "org.asion.bot.AsionBotServerApplication"
    }

}

project("asion-bot-web") {
    apply(plugin = "org.springframework.boot")

    dependencies {
        //
        //compile("org.asion:asion-bot-spring-boot-starter:${version}")
        compile(project(":asion-bot:asion-bot-spring-boot-starter"))

        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("de.codecentric:spring-boot-admin-starter-client:${ext["springBootAdminVersion"]}")
        implementation("org.jolokia:jolokia-core")

        implementation("redis.clients:jedis")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
        implementation("org.webjars:bootstrap:3.3.6")
        implementation("org.webjars:jquery:2.2.3")
        implementation("org.webjars:jquery-validation:1.15.0")
        implementation("org.sitemesh:sitemesh:3.0.1")

        implementation("edu.uci.ics:crawler4j:4.4.0")
        implementation("org.jsoup:jsoup:1.9.2")
        implementation("net.sourceforge.htmlunit:htmlunit:2.30")
        implementation("net.sourceforge.javacsv:javacsv:2.0")

        // seleniumhq
        implementation("org.seleniumhq.selenium:htmlunit-driver:$selenium_htmlunit_version")
        implementation("org.seleniumhq.selenium:selenium-api:$selenium_version")
        implementation("org.seleniumhq.selenium:selenium-chrome-driver:$selenium_version")
        implementation("org.seleniumhq.selenium:selenium-firefox-driver:$selenium_version")
        implementation("org.seleniumhq.selenium:selenium-ie-driver:$selenium_version")
        implementation("org.seleniumhq.selenium:selenium-java:$selenium_version")
        implementation("org.seleniumhq.selenium:selenium-remote-driver:$selenium_version")
        implementation("org.seleniumhq.selenium:selenium-safari-driver:$selenium_version")
        implementation("org.seleniumhq.selenium:selenium-support:$selenium_version")
        implementation("com.codeborne:phantomjsdriver:1.4.4")
        implementation("com.google.guava:guava:22.0")
        implementation("org.apache.commons:commons-exec:1.3")
        implementation("us.codecraft:webmagic-core:0.7.3")
        implementation("us.codecraft:webmagic-extension:0.7.3")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.named<BootJar>("bootJar") {
        mainClassName = "org.asion.bot.AsionBotWebApplication"
    }

    tasks.named<BootRun>("bootRun") {
        main = "org.asion.bot.AsionBotWebApplication"
    }
}

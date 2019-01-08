import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

/**
 * asion-account"s sub projects configuration
 */
project("asion-account-common") {
    dependencies {
        //compile("org.asion:asion-base:${version}")
        compile(project(":asion-base"))
        compile(project(":asion-user:asion-user-common"))
        compile(project(":asion-commons:asion-common"))
        compile("org.projectlombok:lombok:1.18.4")
    }
}

/**
 * asion account spring boot starter
 */
project("asion-account-spring-boot-starter") {
    dependencies {
        //compile("org.asion:asion-account-common:${version}")
        compile(project(":asion-account:asion-account-common"))

        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.boot:spring-boot-configuration-processor")
        implementation("org.mvnsearch.spring.boot:spring-boot-starter-dubbo:2.0.0-SNAPSHOT")
    }
}

project("asion-account-core") {
    dependencies {
        //compile("org.asion:asion-account-common:${version}")
        compile(project(":asion-account:asion-account-common"))

        implementation("org.springframework:spring-context-support")
//        implementation("org.springframework.boot:spring-boot-starter-security")
        implementation("org.springframework.boot:spring-boot-starter-jdbc")
        compile("org.mybatis.spring.boot:mybatis-spring-boot-starter:1.3.2")
        implementation("com.github.pagehelper:pagehelper:4.2.1")
        implementation("mysql:mysql-connector-java")
        implementation("com.h2database:h2")

        implementation("org.projectlombok:lombok")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.unitils:unitils-bom:${ext["unitilsBom"]}")
    }
}

project("asion-account-server") {
    apply(plugin= "org.springframework.boot")

    dependencies {
        //compile("asion-account:asion-account-core")
        implementation(project(":asion-account:asion-account-core"))
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.boot:spring-boot-starter-jdbc")

        implementation("com.alibaba.boot:dubbo-spring-boot-starter:0.2.0")
//        implementation("org.mvnsearch.spring.boot:spring-boot-starter-dubbo:2.0.0-SNAPSHOT")

        implementation("mysql:mysql-connector-java")
//        implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
//        implementation("org.springframework.cloud:spring-cloud-starter-config")
//        implementation("de.codecentric:spring-boot-admin-starter-client:${springBootAdminVersion}")
        implementation("org.jolokia:jolokia-core")

        implementation("com.h2database:h2")
        implementation("redis.clients:jedis")

        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.named<BootJar>("bootJar") {
        mainClassName = "io.vincent.account.AsionAccountServerApplication"
    }

    tasks.named<BootRun>("bootRun") {
        main = "io.vincent.account.AsionAccountServerApplication"
    }

}

project("asion-account-web") {
    apply(plugin= "org.springframework.boot")

    dependencies {
        //
        //implementation("org.asion:asion-account-spring-boot-starter:${version}")
//        implementation(project(":asion-account:asion-account-spring-boot-starter"))
        implementation(project(":asion-account:asion-account-common"))
        implementation("org.springframework.boot:spring-boot-starter-actuator")
//        implementation(project(":asion-account:asion-account-security-spring-boot-starter"))
        implementation("com.alibaba.boot:dubbo-spring-boot-starter:${ext["dubboStarterVersion"]}")
//        implementation("com.alibaba.boot:dubbo-spring-boot-starter:${ext["dubboStarterVersion"]}")
        implementation("com.alibaba.boot:dubbo-spring-boot-actuator:${ext["dubboStarterVersion"]}")

//        implementation("org.springframework.boot:spring-boot-starter-security")
//        implementation("org.springframework.security.oauth:spring-security-oauth2")

//        implementation("de.codecentric:spring-boot-admin-starter-client:${springBootAdminVersion}")
        implementation("org.jolokia:jolokia-core")

        implementation("redis.clients:jedis")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
        implementation("org.webjars:bootstrap:3.3.6")
        implementation("org.webjars:font-awesome:4.7.0")
        implementation("org.webjars:jquery:2.2.3")
        implementation("org.webjars:jquery-validation:1.15.0")
//        implementation("org.webjars.npm:popper.js:1.14.4")
        implementation("org.webjars:popper.js:1.14.4")
        implementation("org.webjars:vue:2.5.16")
        implementation("org.sitemesh:sitemesh:3.0.1")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.named<BootJar>("bootJar") {
        //        archiveName = "app.jar"
        mainClassName = "io.vincent.account.AsionAccountWebApplication"
    }

    tasks.named<BootRun>("bootRun") {
        main = "io.vincent.account.AsionAccountServerApplication"
//        args("--spring.profiles.active=demo")
    }
}

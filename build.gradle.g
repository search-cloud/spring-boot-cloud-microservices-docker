/**
 * 构建的配置
 */
buildscript {
    ext {
        kotlinVersion = '1.3.10'
        springBootVersion = '2.0.2.RELEASE'
    }

    repositories {
        mavenLocal()
        jcenter()
        mavenCentral()
        maven { url "https://repo.spring.io/release" }
        maven { url "https://plugins.gradle.org/m2/" }
        maven { url "https://repo.spring.io/libs-milestone" }
        maven { url "https://repo.spring.io/snapshot" }
    }

    dependencies {
        classpath "io.spring.gradle:dependency-management-plugin:1.0.5.RELEASE"
        classpath "org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}"
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}"
        classpath "org.jetbrains.kotlin:kotlin-allopen:${kotlinVersion}"
        classpath "org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:2.6.2"
        classpath "org.jetbrains.kotlin:kotlin-allopen:${kotlinVersion}"
    }
}

ext {
    springBootVersion = '2.0.2.RELEASE'
    springVersion = '5.0.6.RELEASE'
    springCloudVersion = 'Finchley.SR2'//'Edgware.SR3'
    springBootAdminVersion = '2.0.0'
    platformBomVersion = 'Cairo-SR1'
    unitilsBom = '3.4.2'
    assertjVersion = '3.6.2'
    mybatisSpringBootVersion = '1.3.2'
    lombokVersion = '1.18.0'
    dubboStarterVersion = '0.2.0'
}

/**
 * All projects configuration
 */
configure(allprojects) { project ->
    group = "org.asion"
    version = qualifyVersionIfNecessary(version)

    apply plugin: 'idea'
    apply plugin: 'java'
//    apply plugin: 'maven'
    apply plugin: 'groovy'
    apply plugin: 'kotlin'
    apply plugin: "org.jetbrains.kotlin.plugin.allopen"
//    apply plugin: "org.sonarqube"

    compileJava {
        sourceCompatibility = 1.8
        targetCompatibility = 1.8
        options.encoding = "UTF-8"
    }

    compileTestJava {
        sourceCompatibility = 1.8
        targetCompatibility = 1.8
        options.encoding = "UTF-8"
        options.compilerArgs += "-parameters"
    }

    compileKotlin {
        kotlinOptions {
//            javaTarget = "1.8"
            freeCompilerArgs = ["-Xjsr305=strict"]
            apiVersion = "1.2"
            languageVersion = "1.2"
        }
    }

    compileTestKotlin {
        kotlinOptions {
//            javaTarget = "1.8"
            freeCompilerArgs = ["-Xjsr305=strict"]
        }
    }

    repositories {
        mavenLocal()
        jcenter()
        maven { url "https://repo.spring.io/release" }
        maven { url "https://repo.spring.io/libs-milestone" }
        maven { url "https://repo.spring.io/snapshot" }
        maven { url "https://artifacts.elastic.co/maven" }
    }

    sourceSets {
        main.kotlin.srcDirs += 'src/main/java'
        main.java.srcDirs += 'src/main/kotlin'
    }

    // noinspection GroovyAssignabilityCheck
    jar {
        manifest {
            attributes 'Created-By': "${System.getProperty("java.version")} (${System.getProperty("java.specification.vendor")})",
                    'Implementation-Title': project.name,
                    'Implementation-Version': project.version
        }
        from("${rootProject.projectDir}/src/dist") {
            include "license.txt"
            include "notice.txt"
            into "META-INF"
            expand(copyright: new Date().format("yyyy"), version: project.version)
        }
    }

    javadoc {
        options.encoding = 'UTF-8'
    }

    idea {
        module {
            //if you hate browsing Javadoc
            downloadJavadoc = false
            //and love reading sources :)
            downloadSources = true
        }
    }
}

/**
 * asion-projects's sub projects configuration
 */
subprojects {
    apply plugin: "io.spring.dependency-management"
    apply plugin: 'kotlin'

    dependencyManagement {
        imports {
            mavenBom "org.springframework:spring-framework-bom:${springVersion}"
            mavenBom "org.springframework.boot:spring-boot-dependencies:${springBootVersion}"
            mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
            mavenBom "io.spring.platform:platform-bom:${platformBomVersion}"
        }
    }

    dependencies {
        compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
        compile("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
        compile("org.projectlombok:lombok:${lombokVersion}")
        testCompile "org.jetbrains.kotlin:kotlin-test-junit:${kotlinVersion}"
        runtime("org.springframework.boot:spring-boot-properties-migrator")

        compile "org.elasticsearch.client:x-pack-transport:5.6.9"
    }

    task sourcesJar(type: Jar, dependsOn: classes) {
        classifier = 'sources'
        from sourceSets.main.allSource
        // don't include or exclude anything explicitly by default. See SPR-12085.
    }

    task javadocJar(type: Jar) {
        classifier = "javadoc"
        from javadoc
    }

    artifacts {
        archives sourcesJar
        archives javadocJar
    }
}

/**
 * rootProject configuration
 */
configure(rootProject) {
    description = "Asion System"
}

//task wrapper(type: Wrapper) {
//    gradleVersion = '4.8'
//}

/*
 * Support publication of artifacts versioned by topic branch.
 * CI builds supply `-P BRANCH_NAME=<TOPIC>` to gradle at build time.
 * If <TOPIC> starts with 'SPR-', change version
 *     from BUILD-SNAPSHOT => <TOPIC>-SNAPSHOT
 *     e.g. 3.2.1.BUILD-SNAPSHOT => 3.2.1.SPR-1234-SNAPSHOT
 */
def qualifyVersionIfNecessary(version) {
    if (rootProject.hasProperty("BRANCH_NAME")) {
        def qualifier = rootProject.getProperty("BRANCH_NAME")
        if (qualifier.startsWith("SPR-")) {
            return version.replace('BUILD', qualifier)
        }
    }
    return version
}

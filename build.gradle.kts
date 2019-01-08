import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.plugins.ide.idea.model.IdeaModel
import org.gradle.plugins.ide.idea.model.IdeaProject
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.*

/**
 * Build script.
 */
buildscript {

    val kotlinVersion: String by extra ("1.3.10")
    val springBootVersion: String by extra ("2.0.5.RELEASE")

    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        gradlePluginPortal()
        listOf("https://repo.spring.io/release",
                "https://repo.spring.io/libs-milestone",
                "https://plugins.gradle.org/m2/",
                "https://repo.spring.io/snapshot",
                "https://artifacts.elastic.co/maven").forEach {
            maven { url = uri(it) }
        }
    }

    dependencies {
        classpath("io.spring.gradle:dependency-management-plugin:1.0.6.RELEASE")
        classpath("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-allopen:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-noarg:$kotlinVersion")
    }
}

/**
 * All projects configuration.
 */
allprojects {

    apply(plugin = "java")
    apply(plugin = "idea")

    ext {
        // extra propertis.
        set("kotlinVersion", "1.3.10")
        set("springBootVersion", "2.0.5.RELEASE")
        set("springVersion", "5.0.9.RELEASE")
        set("springCloudVersion", "Finchley.SR2")//"Edgware.SR3"
        set("springBootAdminVersion", "2.0.0")
        set("platformBomVersion", "Cairo-SR1")
        set("unitilsBom", "3.4.2")
        set("assertjVersion", "3.6.2")
        set("mybatisSpringBootVersion", "1.3.2")
        set("lombokVersion", "1.18.4")
        set("dubboStarterVersion", "0.2.0")
    }

    // Idea config.
    configure<IdeaModel> {
        module {
            //if you hate browsing Javadoc
            isDownloadJavadoc = false
            //and love reading sources :)
            isDownloadSources = true
        }
    }

}

/**
 * Subprojects configuration.
 */
configure(subprojects) {

    group = "io.vincent.projects"
    version = qualifyVersionIfNecessary(version as String)

    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "idea")
    apply(plugin = "org.jetbrains.kotlin.plugin.allopen")
    apply(plugin = "io.spring.dependency-management")

    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        listOf("https://repo.spring.io/release",
                "https://repo.spring.io/libs-milestone",
                "https://plugins.gradle.org/m2/",
                "https://repo.spring.io/snapshot",
                "https://artifacts.elastic.co/maven").forEach {
            maven { url = uri(it) }
        }
    }

    // DependencyManagement
    configure<DependencyManagementExtension> {
        imports {
            mavenBom("org.springframework:spring-framework-bom:${ext["springVersion"]}")
            mavenBom("org.springframework.boot:spring-boot-dependencies:${ext["springBootVersion"]}")
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${ext["springCloudVersion"]}")
            mavenBom("io.spring.platform:platform-bom:${ext["platformBomVersion"]}")
        }
    }

    tasks.existing(KotlinCompile::class) {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

    tasks.existing(JavaCompile::class) {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
        options.encoding = "UTF-8"
    }

    tasks.existing(Javadoc::class) {
        options.encoding = "UTF-8"
    }

    // All jar config.
    tasks.named<Jar>("jar") {
        //        archiveName = "foo.jar"
        manifest {
            attributes["Created-By"] = "${System.getProperty("java.version")} (${System.getProperty("java.specification.vendor")})"
            attributes["Implementation-Title"] = project.name
            attributes["Implementation-Version"] = project.version
        }
        from("${rootProject.projectDir}/src/dist", {
            include("license.txt")
            include("notice.txt")
            into("META-INF")
            expand(Pair("copyright", Date()), Pair("version", project.version))
        })
    }

    // TODO sourcesJar
    // TODO javadocJar
    // TODO artifacts


}

/**
 * asion-projects"s sub projects configuration
 */
subprojects {
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin")

    val kotlinVersion: String by extra("1.3.10")
    val lombokVersion: String by extra("1.18.4")
    val springBootVersion: String by extra("2.0.5.RELEASE")

    dependencies {
        "implementation"("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
        "implementation"("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
        "implementation"("org.projectlombok:lombok:$lombokVersion")
        "testImplementation"("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
        "runtimeOnly"("org.springframework.boot:spring-boot-properties-migrator:$springBootVersion")

//        "implementation"("org.elasticsearch.client:x-pack-transport:5.6.9")
    }
}

tasks.named("wrapper", Wrapper::class, {
    gradleVersion = "5.1"
})

/*
 * Support publication of artifacts versioned by topic branch.
 * CI builds supply `-P BRANCH_NAME=<TOPIC>` to gradle at build time.
 * If <TOPIC> starts with "SPR-", change version
 *     from BUILD-SNAPSHOT => <TOPIC>-SNAPSHOT
 *     e.g. 3.2.1.BUILD-SNAPSHOT => 3.2.1.SPR-1234-SNAPSHOT
 */
fun qualifyVersionIfNecessary(version: String): String {
    if (rootProject.hasProperty("BRANCH_NAME")) {
        val qualifier = rootProject.property("BRANCH_NAME") as String
        if (qualifier.startsWith("SPR-")) {
            return version.replace("BUILD", qualifier)
        }
    }
    return version
}

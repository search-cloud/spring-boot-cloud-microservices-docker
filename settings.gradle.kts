rootProject.name = "spring-boot-cloud-microservices-docker"

// ddd
include("asion-base")

// utils
include("asion-commons")
include("asion-commons:asion-common")

// cloud
include("asion-cloud")
include("asion-cloud:asion-cloud-registry-server")
include("asion-cloud:asion-cloud-config-server")
include("asion-cloud:asion-spring-boot-admin")

include("asion-user")
include("asion-user:asion-user-common")
include("asion-user:asion-user-spring-boot-starter")

//// email
//include("asion-email")
//       ("asion-email:asion-email-common")
//       ("asion-email:asion-email-spring-boot-starter")
//       ("asion-email:asion-email-core")
//       ("asion-email:asion-email-server")
//       ("asion-email:asion-email-web")

// account
include("asion-account")
include("asion-account:asion-account-common")
include("asion-account:asion-account-spring-boot-starter")
include("asion-account:asion-account-core")
include("asion-account:asion-account-server")
include("asion-account:asion-account-web")

// security
include("asion-security")
include("asion-security:asion-security-common")
include("asion-security:asion-security-spring-boot-starter")
include("asion-security:asion-security-core")
include("asion-security:asion-security-server")
include("asion-security:asion-security-web")

// sample
//include("asion-sample")
//include("asion-sample:asion-sample-common")
//include("asion-sample:asion-sample-spring-boot-starter")
//include("asion-sample:asion-sample-core")
//include("asion-sample:asion-sample-server")
//include("asion-sample:asion-sample-web")

//include("asion-category")
//       ("asion-category:asion-category-common")
//       ("asion-category:asion-category-spring-boot-starter")
//       ("asion-category:asion-category-core")
//       ("asion-category:asion-category-server")
//       ("asion-category:asion-category-crm-web")
//       ("asion-category:asion-category-seller-web"
//
//include("asion-brand")
//       ("asion-brand:asion-brand-common")
//       ("asion-brand:asion-brand-spring-boot-starter")
//       ("asion-brand:asion-brand-core")
//       ("asion-brand:asion-brand-server")
//       ("asion-brand:asion-brand-web"

// search
//include("asion-search")
//include("asion-search:asion-search-common")
//include("asion-search:asion-search-spring-boot-starter")
//include("asion-search:asion-search-core")
//include("asion-search:asion-search-server")
//include("asion-search:asion-search-web")

// robot
//include("asion-bot")
//include("asion-bot:asion-bot-common")
//include("asion-bot:asion-bot-spring-boot-starter")
//include("asion-bot:asion-bot-core")
//include("asion-bot:asion-bot-server")
//include("asion-bot:asion-bot-web")

// webflux
//include("asion-webflux-demo")
//include("asion-webflux-demo:asion-webflux-demo-common")
//include("asion-webflux-demo:asion-webflux-demo-spring-boot-starter")
//include("asion-webflux-demo:asion-webflux-demo-core")
//include("asion-webflux-demo:asion-webflux-demo-server")
//include("asion-webflux-demo:asion-webflux-demo-web")

// Exposes gradle buildSrc for IDE support
//include("buildSrc")
//rootProject.children.find { it.name == "buildSrc" }?.name = "asion-build-src"

include("asion-docker")
include("asion-docker:asion-docker-dockerfile")
findProject(":asion-docker:asion-docker-dockerfile")?.name = "asion-docker-dockerfile"
include("asion-docker:asion-docker-compose")
findProject(":asion-docker:asion-docker-compose")?.name = "asion-docker-compose"

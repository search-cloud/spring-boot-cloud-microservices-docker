rootProject.name = 'spring-boot-cloud-microservices-docker'

// ddd
include 'asion-base'

// utils
include 'asion-commons'
include 'asion-commons:asion-common'

// cloud
include 'asion-cloud',
        'asion-cloud:asion-cloud-registry-server',
        'asion-cloud:asion-cloud-config-server',
        'asion-cloud:asion-spring-boot-admin'

include 'asion-user'
include "asion-user:asion-user-common"
include "asion-user:asion-user-spring-boot-starter"

//// email
//include 'asion-email',
//        "asion-email:asion-email-common",
//        "asion-email:asion-email-spring-boot-starter",
//        "asion-email:asion-email-core",
//        "asion-email:asion-email-server",
//        "asion-email:asion-email-web"

// account
include 'asion-account',
        "asion-account:asion-account-common",
        "asion-account:asion-account-spring-boot-starter",
        "asion-account:asion-account-core",
        "asion-account:asion-account-server",
        "asion-account:asion-account-web"

// security
include 'asion-security',
        "asion-security:asion-security-common",
        "asion-security:asion-security-spring-boot-starter",
        "asion-security:asion-security-core",
        "asion-security:asion-security-server",
        "asion-security:asion-security-web"

// sample
include 'asion-sample',
        "asion-sample:asion-sample-common",
        "asion-sample:asion-sample-spring-boot-starter",
        "asion-sample:asion-sample-core",
        "asion-sample:asion-sample-server",
        "asion-sample:asion-sample-web"

//include 'asion-category',
//        "asion-category:asion-category-common",
//        "asion-category:asion-category-spring-boot-starter",
//        "asion-category:asion-category-core",
//        "asion-category:asion-category-server",
//        "asion-category:asion-category-crm-web",
//        "asion-category:asion-category-seller-web"
//
//include 'asion-brand',
//        "asion-brand:asion-brand-common",
//        "asion-brand:asion-brand-spring-boot-starter",
//        "asion-brand:asion-brand-core",
//        "asion-brand:asion-brand-server",
//        "asion-brand:asion-brand-web"

// search
include 'asion-search',
        "asion-search:asion-search-common",
        "asion-search:asion-search-spring-boot-starter",
        "asion-search:asion-search-core",
        "asion-search:asion-search-server",
        "asion-search:asion-search-web"

// robot
include 'asion-bot',
        "asion-bot:asion-bot-common",
        "asion-bot:asion-bot-spring-boot-starter",
        "asion-bot:asion-bot-core",
        "asion-bot:asion-bot-server",
        "asion-bot:asion-bot-web"

// webflux
include 'asion-webflux-demo',
        "asion-webflux-demo:asion-webflux-demo-common",
        "asion-webflux-demo:asion-webflux-demo-spring-boot-starter",
        "asion-webflux-demo:asion-webflux-demo-core",
        "asion-webflux-demo:asion-webflux-demo-server",
        "asion-webflux-demo:asion-webflux-demo-web"

// Exposes gradle buildSrc for IDE support
include "buildSrc"
rootProject.children.find{ it.name == "buildSrc" }.name = "asion-build-src"

include 'asion-docker'
include 'asion-docker:asion-docker-dockerfile'
findProject(':asion-docker:asion-docker-dockerfile')?.name = 'asion-docker-dockerfile'
include 'asion-docker:asion-docker-compose'
findProject(':asion-docker:asion-docker-compose')?.name = 'asion-docker-compose'

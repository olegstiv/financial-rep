const val kotlinVersion = "1.9.20"
const val JVM = "21"

object BuildPlugins {
    object Versions {
        const val springDependencyManagement = "1.1.4"
        const val springBoot3 = "3.2.0"
        const val liquibase = "2.2.0"
        const val springDoc = "1.6.0"
    }
}

object Libraries {
    private object Versions {
        const val arrow = "1.2.0-RC"
        const val konform = "0.4.0"
        const val kotlinLogging = "3.0.5"
        const val sentry = "6.18.0"
        const val springDoc = "2.3.0"
        const val dotenv = "2.5.4"
        const val jacsonKotlin = "2.14.2"
        const val liquibase = "4.25.0"
        const val hibernateValidator = "8.0.1.Final"

//        const val kotlinCoroutine = "1.7.2"
//        const val kotlinReactorExt = "1.2.2"
    }

    object Spring {
        const val starter = "org.springframework.boot:spring-boot-starter:${BuildPlugins.Versions.springBoot3}"
        const val actuator = "org.springframework.boot:spring-boot-starter-actuator:${BuildPlugins.Versions.springBoot3}"
        const val log4j2 = "org.springframework.boot:spring-boot-starter-log4j2:${BuildPlugins.Versions.springBoot3}"
        const val dataJpa = "org.springframework.boot:spring-boot-starter-data-jpa:${BuildPlugins.Versions.springBoot3}"
        const val security = "org.springframework.boot:spring-boot-starter-security:${BuildPlugins.Versions.springBoot3}"
        const val web = "org.springframework.boot:spring-boot-starter-web:${BuildPlugins.Versions.springBoot3}"
        const val annotationProcessor = "org.springframework.boot:spring-boot-configuration-processor:${BuildPlugins.Versions.springBoot3}"
    }

    const val kotlinLogging = "io.github.microutils:kotlin-logging:${Versions.kotlinLogging}"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
    const val postgresql = "org.postgresql:postgresql"
    const val springDoc = "org.springdoc:springdoc-openapi-starter-webmvc-ui:${Versions.springDoc}"
    const val dotenv = "me.paulschwarz:spring-dotenv:${Versions.dotenv}"
    const val jacksonKotlin = "com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jacsonKotlin}"
    const val liquibase = "org.liquibase:liquibase-core:${Versions.liquibase}"
    const val hibernateValidator = "org.hibernate.validator:hibernate-validator:${Versions.hibernateValidator}"

//    object KotlinReactive {
//        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutine}"
//        const val reactor = "org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${Versions.kotlinCoroutine}"
//        const val reactive = "org.jetbrains.kotlinx:kotlinx-coroutines-reactive:${Versions.kotlinCoroutine}"
//        const val reactorExt = "io.projectreactor.kotlin:reactor-kotlin-extensions:${Versions.kotlinReactorExt}"
//    }
}

//object TestLibraries {
//    private object Versions {
//        const val kotest = "5.6.1"
//
//        //        const val springKotest = "1.1.3"
//        const val mockK = "1.13.5"
//    }
//
//    const val kotest = "io.kotest:kotest-runner-junit5:${Versions.kotest}"
//    const val assertK = "io.kotest:kotest-assertions-core:${Versions.kotest}"
//    const val propertyK = "io.kotest:kotest-property:${Versions.kotest}"
//    const val mockK = "io.mockk:mockk:${Versions.mockK}"
//
////    const val springKotest = "io.kotest.extensions:kotest-extensions-spring:${Versions.springKotest}"
//}

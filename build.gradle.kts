import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version BuildPlugins.Versions.springBoot3
    id("io.spring.dependency-management") version BuildPlugins.Versions.springDependencyManagement
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
}

group = Base.GROUP
version = Base.VERSION_NAME

repositories {
    mavenCentral()
}

dependencies {
    implementation(Libraries.Spring.dataJpa)
    implementation(Libraries.Spring.security)
    implementation(Libraries.Spring.web)
    implementation(Libraries.Spring.starter)
    implementation(Libraries.Spring.log4j2)
    implementation(Libraries.dotenv)
    implementation(Libraries.hibernateValidator)
    implementation(Libraries.springDoc)
    implementation(Libraries.kotlinLogging)
    implementation(Libraries.liquibase)

    implementation(Libraries.jacksonKotlin)
    implementation(Libraries.kotlinStdLib)
    runtimeOnly(Libraries.postgresql)
    annotationProcessor(Libraries.Spring.annotationProcessor)
//    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

configurations {
    all {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
}
tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = JVM
    }
}

tasks.withType<JavaCompile> {
    sourceCompatibility = JVM
    targetCompatibility = JVM
    options.encoding = "UTF-8"
}

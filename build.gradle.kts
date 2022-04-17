plugins {
    java
    war
}

group "com.stakhiyevich"
version "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.8.2")

    implementation ("org.apache.logging.log4j:log4j-core:2.17.2")
    implementation ("org.apache.logging.log4j:log4j-api:2.17.2")
    implementation ("jakarta.servlet:jakarta.servlet-api:5.0.0")
    implementation("org.glassfish.web:jakarta.servlet.jsp.jstl:2.0.0")
    compileOnly("jakarta.servlet.jsp:jakarta.servlet.jsp-api:3.0.0")

    implementation ("mysql:mysql-connector-java:8.0.28")
    implementation("com.sun.mail:jakarta.mail:2.0.1")
}

tasks {
    "test"(Test::class) {
        useJUnitPlatform()
    }
}
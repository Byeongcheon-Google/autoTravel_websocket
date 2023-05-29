import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    id("org.jetbrains.compose") version "1.3.1"

    id("org.springframework.boot") version "2.7.11"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"

    kotlin("jvm") version "1.8.10"
    kotlin("plugin.spring") version "1.6.21"
}

group = "com.hidevlop"
version = ""
java.sourceCompatibility = JavaVersion.VERSION_11



repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {

    implementation ("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.boot:spring-boot-starter-websocket")
    implementation ("org.springframework.boot:spring-boot-starter-freemarker")
    implementation ("org.springframework.boot:spring-boot-devtools")
    implementation ("org.springframework.boot:spring-boot-starter-data-redis")


    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("org.springframework.boot:spring-boot-starter-security")

    //embedded-redis
    implementation ("it.ozimov:embedded-redis:0.7.2")
    implementation ("org.webjars.bower:bootstrap:4.3.1")
    implementation ("org.webjars.bower:vue:2.5.16")
    implementation ("org.webjars.bower:axios:0.17.1")
    implementation ("org.webjars:sockjs-client:1.1.2")
    implementation ("org.webjars:stomp-websocket:2.3.3-1")
    implementation ("com.google.code.gson:gson:2.8.0")

//    implementation ("com.googlecode.json-simple:json-simple:1.1.1")
    implementation ("io.springfox:springfox-boot-starter:3.0.0")
    implementation ("io.springfox:springfox-swagger-ui:3.0.0")

    compileOnly ("org.projectlombok:lombok")
    annotationProcessor ("org.projectlombok:lombok")
    testImplementation ("org.springframework.boot:spring-boot-starter-test")

    implementation("com.googlecode.concurrentlinkedhashmap:concurrentlinkedhashmap-lru:1.4.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("io.reactivex.rxjava3:rxjava:3.1.6")
    implementation(kotlin("stdlib-jdk8"))
    implementation(compose.desktop.currentOs)
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")


    runtimeOnly ("com.mysql:mysql-connector-j")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

compose.desktop {
    application {
        mainClass = "MainKt"
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}



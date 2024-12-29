
plugins {
    kotlin("jvm") version "2.0.21"
    id("application")
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "read"
version = ""

repositories {
   mavenCentral()
}
val mainClassName: String ="MainKt"
val kotlin_version: String ="2.0.21"

application{
    mainClass=mainClassName
}

val ktorm_version="4.1.1"

dependencies {
    implementation(project(":api"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    //implementation("io.github.microutils:kotlin-logging:1.6.24")
    //implementation("info.picocli:picocli:4.7.6")

    //数据库
    implementation("com.mysql:mysql-connector-j:9.1.0")
    implementation("org.xerial:sqlite-jdbc:3.47.1.0")
    implementation("org.dromara.mpe:mybatis-plus-ext-spring-boot3-starter:3.5.9-EXT811")
    testImplementation("p6spy:p6spy:3.9.1")

    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")


    implementation("commons-codec:commons-codec:1.17.1")

    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.+")

    // 网络
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.1.0")
    //implementation("org.jetbrains.kotlin:kotlin-reflect")
    //testImplementation("org.springframework.boot:spring-boot-starter-test")
    //testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    //testRuntimeOnly("org.junit.platform:junit-platform-launcher")



}


//tasks.jar {
//    duplicatesStrategy = DuplicatesStrategy.INCLUDE
//    manifest.attributes["Main-Class"] = mainClassName
//    from(configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) })
//    exclude("LICENSE.txt", "NOTICE.txt", "rootdoc.txt")
//    exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
//    exclude("META-INF/NOTICE", "META-INF/NOTICE.txt")
//    exclude("META-INF/LICENSE", "META-INF/LICENSE.txt")
//    exclude("META-INF/DEPENDENCIES")
//}

sourceSets {
    main {
        java.srcDir("src/main/kotlin")
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType(JavaCompile::class).configureEach {
    options.encoding = "UTF-8"
}

tasks.withType(Javadoc::class).configureEach {
    options.encoding = "UTF-8"
}

tasks.withType(JavaExec::class).configureEach {
    jvmArgs=listOf("-Xmx512m", "-Dfile.encoding=UTF-8", "-Dsun.stdout.encoding=UTF-8","-Dsun.stderr.encoding=UTF-8")
}


//tasks.register("bootBuild") {
//    dependsOn("build")
//}
//
//tasks.named("bootTestRun") {
//    dependsOn("bootBuild")
//}

//configurations.all{
//    resolutionStrategy.cacheChangingModulesFor(24, "hours")
//    resolutionStrategy.cacheDynamicVersionsFor(10*60, "seconds")
//}
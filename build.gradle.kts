import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.1.0"
    id("application")
}

group = "read"
version = "1.0-SNAPSHOT"
val kotlin_version: String ="2.1.0"
val mainClassName: String ="web.AppKt"
val libsDir = "libs"

application{
    mainClass=mainClassName
}

repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/") }
}

configurations.all {
    resolutionStrategy {
        force("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
        force("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
        force("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.5")
        force("org.slf4j:slf4j-api:2.0.16")
    }
}

dependencies {
    implementation(project(":book"))
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

    implementation("javax.mail:mail:1.4.7")

    implementation(platform("org.noear:solon-parent:3.3.2-M1"))

    implementation("org.noear:solon-web"){
        exclude(group = "org.noear", module = "solon-serialization-snack3")
    }
    implementation("org.noear:solon-web-staticfiles"){
        exclude(group = "org.slf4j", module = "slf4j-simple")
    }
    implementation("org.noear:solon-view-freemarker"){
        exclude(group = "org.slf4j", module = "slf4j-simple")
    }
    implementation("org.noear:solon-logging-logback"){
        exclude(group = "org.slf4j", module = "slf4j-simple")
    }
    implementation("org.noear:solon-scheduling-simple"){
        exclude(group = "org.slf4j", module = "slf4j-simple")
    }
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.1.0"){
        exclude(group = "org.slf4j", module = "slf4j-simple")
    }
    implementation("org.noear:solon-serialization-gson"){
        exclude(group = "org.slf4j", module = "slf4j-simple")
    }
    implementation("org.noear:solon-cache-redisson"){
        exclude(group = "org.slf4j", module = "slf4j-simple")
    }

    //数据库
    implementation("com.mysql:mysql-connector-j:9.1.0")
    implementation("org.xerial:sqlite-jdbc:3.49.1.0")
    implementation("com.baomidou:mybatis-plus-solon-plugin:3.5.9")
    implementation("com.baomidou:mybatis-plus-jsqlparser-4.9:3.5.9")
    //implementation("org.dromara.mpe:mybatis-plus-ext-autotable-core:3.5.10.1-EXT822")
    implementation("org.dromara.autotable:auto-table-core:2.1.4")
    //implementation("org.dromara.autotable:auto-table-solon-plugin:2.1.4")
    implementation("cn.hutool:hutool-crypto:5.8.22")


    implementation("com.zaxxer:HikariCP:6.2.1")
    runtimeOnly("p6spy:p6spy:3.9.1")

    implementation("com.google.code.gson:gson:2.11.0")

    implementation("org.bouncycastle:bcpkix-jdk18on:1.80")


    // 网络
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.1.0")

    implementation("org.apache.xmlgraphics:fop:2.11")
    implementation("org.apache.xmlgraphics:batik-all:1.19")

    compileOnly("org.projectlombok:lombok")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}
tasks.withType<KotlinCompile> {
    compilerOptions.javaParameters = true
}

tasks {
    val copyDependencies by registering(Copy::class) {
        from(configurations.runtimeClasspath)
        into(libsDir)
    }

    val classPathEntries = configurations.runtimeClasspath.get()
        .files
        .map { file -> "$libsDir/${file.name}" }
        .joinToString(" ") { it.replace("\\", "/") }

    jar {
        dependsOn(copyDependencies)
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        //duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest {
            attributes("Class-Path" to classPathEntries)
        }
        manifest.attributes["Main-Class"] = mainClassName
        //from(configurations.runtimeClasspath.get().files.map { "$libsDir/${it.name}" }
         //   .joinToString(" "))



        //exclude("LICENSE.txt", "NOTICE.txt", "rootdoc.txt")
       // exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
       // exclude("META-INF/NOTICE", "META-INF/NOTICE.txt")
        //exclude("META-INF/LICENSE", "META-INF/LICENSE.txt")
        //exclude("META-INF/DEPENDENCIES")
    }

    // 清理生成的libs目录
    clean {
        delete(libsDir)
    }
}
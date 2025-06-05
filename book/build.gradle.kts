plugins {
    kotlin("jvm")
}

val kotlin_version: String ="2.1.0"
group = "read"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

    // 规则相关
    implementation("org.jsoup:jsoup:1.16.2")
    implementation("cn.wanghaomiao:JsoupXpath:2.5.3")
    implementation("com.jayway.jsonpath:json-path:2.9.0")

    // json
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.+")

    //log
    implementation("org.slf4j:slf4j-api:2.0.16")
    //implementation("org.slf4j:slf4j-simple:2.0.16")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.6.1")
    implementation("com.julienviet:retrofit-vertx:1.1.3")
    implementation("org.bouncycastle:bcpkix-jdk18on:1.80")

    // 网络
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.1.0")

    //加解密类库
    implementation("cn.hutool:hutool-crypto:5.8.22")

    //js
   // implementation(files("src/lib/rhino-1.7.14.jar"))
    implementation("org.mozilla:rhino:1.8.0")
    implementation(files("src/lib/xmlpull-1.1.3.1.jar"))
    implementation("org.apache.commons:commons-text:1.13.0")
    implementation("com.github.liuyueyi:quick-transfer-core:0.2.16")
    testImplementation(kotlin("test"))
}
sourceSets {
    main {
        java.srcDir("src/main/kotlin")
    }
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(19)
}
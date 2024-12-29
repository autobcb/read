plugins {
    kotlin("jvm")
}
val kotlin_version: String ="2.0.21"
group = "com"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    //implementation("io.github.microutils:kotlin-logging:3.0.5")

    implementation(files("src/lib/rhino-1.7.13-1.jar"))
    implementation(files("src/lib/xmlpull-1.1.3.1.jar"))

    // 规则相关
    implementation("org.jsoup:jsoup:1.14.1")
    implementation("cn.wanghaomiao:JsoupXpath:2.5.0")
    implementation("com.jayway.jsonpath:json-path:2.6.0")

    // json
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.+")
    //加解密类库
    implementation("cn.hutool:hutool-crypto:5.8.0.M1")
    // 网络
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.1.0")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.6.1")
    implementation("com.julienviet:retrofit-vertx:1.1.3")

    // log
    implementation("io.github.microutils:kotlin-logging:1.6.24")
    implementation("uk.org.lidalia:sysout-over-slf4j:1.0.2")
    //implementation("org.slf4j:slf4j-api:1.7.6")
    //implementation("org.slf4j:slf4j-simple:1.7.6")

}


sourceSets {
    main {
        java.srcDir("src/main/kotlin")
    }
}

configurations.all{
    resolutionStrategy.cacheChangingModulesFor(24, "hours")
    resolutionStrategy.cacheDynamicVersionsFor(24, "hours")
}
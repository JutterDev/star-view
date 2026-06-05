plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization") version "2.1.0"
}

group = "online.jutter"
version = "unspecified"

repositories {
    mavenCentral()
    google()
}

dependencies {

    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)

    // Include the Test API
    testImplementation(compose.desktop.uiTestJUnit4)

    // Lets-Plot Kotlin API
    implementation("org.jetbrains.lets-plot:lets-plot-kotlin-kernel:4.12.1")

    // Lets-Plot Multiplatform
    implementation("org.jetbrains.lets-plot:lets-plot-common:4.8.2")
    implementation("org.jetbrains.lets-plot:canvas:4.8.2")
    implementation("org.jetbrains.lets-plot:plot-raster:4.8.2")

    // Lets-Plot 'image export' (optional - enables exporting to raster formats)
    implementation("org.jetbrains.lets-plot:lets-plot-image-export:4.8.2")

    // Lets-Plot Compose UI
    implementation("org.jetbrains.lets-plot:lets-plot-compose:3.0.2")

    implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
    implementation("org.jetbrains.androidx.lifecycle:lifecycle-runtime-compose:2.8.4")
    implementation("org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha10")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    implementation("io.insert-koin:koin-core:4.0.0")
    implementation("io.insert-koin:koin-compose:4.0.0")
    implementation("io.insert-koin:koin-compose-viewmodel:4.0.0")
    implementation("io.insert-koin:koin-compose-viewmodel-navigation:4.0.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

    implementation(project(":domain"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(23)
}
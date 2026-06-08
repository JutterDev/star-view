plugins {
    kotlin("jvm")
}

group = "online.jutter"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {

    implementation("io.insert-koin:koin-core:4.0.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(23)
}
java {
    sourceCompatibility = JavaVersion.VERSION_23
    targetCompatibility = JavaVersion.VERSION_23
}
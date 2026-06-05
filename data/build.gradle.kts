plugins {
    kotlin("jvm")
}

group = "online.jutter"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {

    implementation(project(":domain"))

    implementation("io.insert-koin:koin-core:4.0.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(23)
}
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    id("org.jetbrains.dokka") version "1.9.10"
    `maven-publish`
}

val host = "github.com/TheFruxz/Ascend"

version = "2024.1"
group = "dev.fruxz"

repositories {
    mavenCentral()
}

dependencies {

    // Kotlin

    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    implementation("org.jetbrains.exposed:exposed-core:0.47.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.46.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.47.0")

}

val dokkaHtmlJar by tasks.register<Jar>("dokkaHtmlJar") {
    dependsOn(tasks.dokkaHtml)
    from(tasks.dokkaHtml.flatMap { it.outputDirectory })
    archiveClassifier.set("html-docs")
}

val dokkaJavadocJar by tasks.register<Jar>("dokkaJavadocJar") {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

val sourceJar by tasks.register<Jar>("sourceJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

publishing {

    repositories {
        mavenLocal()
        maven("https://repo.fruxz.dev/releases") {
            name = "fruxz.dev"
            credentials {
                username = project.findProperty("fruxz.dev.user") as? String? ?: System.getenv("FRUXZ_DEV_USER")
                password = project.findProperty("fruxz.dev.secret") as? String? ?: System.getenv("FRUXZ_DEV_SECRET")
            }
        }
    }

    publications.create("Ascend", MavenPublication::class) {
        artifactId = name.lowercase()
        version = version.lowercase()

        artifact(dokkaJavadocJar)
        artifact(dokkaHtmlJar)
        artifact(sourceJar) {
            classifier = "sources"
        }

        from(components["kotlin"])

    }

}

tasks {

    withType<KotlinCompile> {
        kotlinOptions.freeCompilerArgs += "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
    }

    dokkaHtml.configure {
        outputDirectory.set(layout.projectDirectory.dir("docs"))
    }

}

kotlin {
    jvmToolchain(17)
}

java {
    withJavadocJar()
    withSourcesJar()
}
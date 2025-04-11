import java.util.Calendar

plugins {
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.serialization") version "2.1.20"
    id("org.jetbrains.dokka") version "2.0.0"
    id("org.hildan.kotlin-publish") version "1.7.0"
    id("ru.vyarus.github-info") version "2.0.0"
    `maven-publish`
}

val publishVersion = System.getenv("GH_RELEASE_VERSION")
val calendar = Calendar.getInstance()

version = publishVersion ?: "${calendar[Calendar.YEAR]}.${calendar[Calendar.MONTH] + 1}-dev"
group = "dev.fruxz"

repositories {
    mavenCentral()
}

dependencies {

    // Kotlin

    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

    implementation("org.jetbrains.exposed:exposed-core:0.61.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.61.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.61.0")

    implementation("org.pkl-lang:pkl-config-kotlin:0.28.1")

}

github {
    user = "TheFruxz"
    license = "LGPLv3"
}

publishing {

    repositories {
        mavenLocal()
        maven("https://repo.fruxz.dev/releases") {
            name = "fruxz.dev"
            credentials {
                username = System.getenv("FRUXZ_DEV_USER")
                password = System.getenv("FRUXZ_DEV_SECRET")
            }
        }
    }

}

tasks {

    compileKotlin {
        compilerOptions {
            freeCompilerArgs.add("-opt-in=kotlinx.serialization.ExperimentalSerializationApi")
        }
    }

    dokkaHtml.configure {
        outputDirectory.set(layout.projectDirectory.dir("docs"))
    }

}

kotlin {
    jvmToolchain(21)
}
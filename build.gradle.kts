import java.util.Calendar

plugins {
    kotlin("jvm") version "2.4.10"
    kotlin("plugin.serialization") version "2.4.10"
    id("org.jetbrains.dokka") version "2.2.0"
    id("org.hildan.kotlin-publish") version "1.7.0"
    id("ru.vyarus.github-info") version "2.0.0"
    `maven-publish`
}

val publishVersion = System.getenv("GH_RELEASE_VERSION")
val calendar = Calendar.getInstance()
val exposedVersion = "1.4.0"

version = publishVersion ?: "${calendar[Calendar.YEAR]}.${calendar[Calendar.MONTH] + 1}-dev"
group = "dev.fruxz"

repositories {
    mavenCentral()
    maven {
        name = "fruxz.dev"
        url = uri("https://nexus.fruxz.dev/repository/public/")
    }
}

dependencies {

    // Kotlin

    testImplementation(kotlin("test"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposedVersion")

}

github {
    user = "TheFruxz"
    license = "LGPLv3"
}

publishing {

    repositories {
        mavenLocal()
        maven("https://nexus.fruxz.dev/repository/releases/") {
            name = "fruxz.dev"
            credentials {
                username = System.getenv("FXZ_NEXUS_USER")
                password = System.getenv("FXZ_NEXUS_SECRET")
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
}

kotlin {
    jvmToolchain(21)
}

// alias dokkaHtml -> dokkaGenerateHtml
tasks.register("dokkaHtml") {
    dependsOn(tasks.dokkaGenerateHtml)
}

dokka {
    moduleName.set("Ascend @ MoltenKt")
    dokkaPublications.html {
        outputDirectory.set(layout.buildDirectory.dir("dokkaDir"))
    }
    dokkaSourceSets.main {
        //includes.from("README.md")
        sourceLink {
            localDirectory.set(file("src/main/kotlin"))
            remoteUrl("https://github.com/TheFruxz/Ascend/tree/develop/src/main/kotlin")
        }
    }
    pluginsConfiguration.html {
        homepageLink.set("https://fxz.koeln/")
        footerMessage.set("Ascend @ MoltenKt - by Fruxz")
    }
}
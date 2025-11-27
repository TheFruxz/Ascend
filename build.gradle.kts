import java.util.Calendar

plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.serialization") version "2.2.21"
    id("org.jetbrains.dokka") version "2.1.0"
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
    maven {
        name = "fruxz.dev"
        url = uri("https://nexus.fruxz.dev/repository/public/")
    }
}

dependencies {

    // Kotlin

    testImplementation(kotlin("test"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

    implementation("org.jetbrains.exposed:exposed-core:1.0.0-rc-4")
    implementation("org.jetbrains.exposed:exposed-dao:1.0.0-rc-4")
    implementation("org.jetbrains.exposed:exposed-jdbc:1.0.0-rc-4")

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
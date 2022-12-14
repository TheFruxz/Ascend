![Ascend Banner](https://user-images.githubusercontent.com/28064149/192885894-e412f3e6-9efe-4195-891e-7ba54f0edd7e.jpg)

<br>

## 👋 Welcome to Ascend

Ascend provides several small, but useful, features and tools, to help you get the most out of you code.
It is a collection of tools that I have found useful in my own projects, and I hope you find them useful too.
These tools are designed, to seamlessly integrate into your code, and to be as unobtrusive as possible.
You can find most of them, by looking into your code completions, because a huge part of them are provided via extension functions.

## ⚙️ Setup

### Repository

How can I use Ascend in my own projects? For this you need to know what your project is based on, or should be based on.
We ourselves recommend that you use `Gradle Kotlin` in all your projects, but you can also use other systems like `Gradle` and `Maven`!

#### Using JitPack
##### Repository
```kotlin
maven("https://jitpack.io")
```

##### Dependency
```kotlin
implementation("com.github.TheFruxz:Ascend:$ascendVersion")
```

#### Using GitHub Packages
##### Repository 
```kotlin
maven("https://maven.pkg.github.com/TheFruxz/Ascend") {
        credentials {
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
```

##### Dependency
```kotlin
implementation("de.fruxz:ascend:$ascendVersion")
```

#### 🔐 Auth

You need to have set the system variables `USERNAME` and `TOKEN` to your GitHub-Username and GitHub-Personal-Access-Token,
to access the packages via the GitHub-Packages Feature. You can also use the project variables `gpr.user` and `gpr.key`, but
don't publish them to the web!

## 🗞 Version

Since we always try to use the latest versions as soon as possible, as already described in the point 'Version Policy', current versions quickly become obsolete, so we will soon release a list of versions, where it will be shown exactly how long a certain version is still being supported.

## 👥 Contribution

Of course, you can also participate in Ascend and contribute to the development. However, please follow all community and general guidelines of GitHub and the repositories. You also have to respect the licenses set in this repository as well as in other repositories.

If you have any questions, suggestions or other items you would like to contribute to Ascend or just discuss, check out the Discussions' section of this repository, where you will find the respective areas where you can create your own questions or join in discussions on other things.

###### We build & use Ascend on Java 17 - [Eclipse Temurin](https://adoptium.net/).
###### Also build & run Ascend with [Eclipse Temurin](https://adoptium.net/) to get the best possible experience!

[![Open Source](https://forthebadge.com/images/badges/open-source.svg)](https://github.com/TheFruxz/Ascend/blob/main/LICENSE)
[![Built by developers](https://forthebadge.com/images/badges/built-by-developers.svg)](https://github.com/TheFruxz/Ascend/graphs/contributors)
[![Written in Kotlin](https://forthebadge.com/images/badges/makes-people-smile.svg)](https://github.com/JetBrains/kotlin)

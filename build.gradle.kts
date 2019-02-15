// Top-level build file where you can add configuration options common to all sub-projects/modules.
//apply from: "config.gradle"

buildscript {
    repositories {
        mavenCentral()
        maven(url = "https://jitpack.io")
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.3.1")
        classpath("com.neenbedankt.gradle.plugins:android-apt:1.8")
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}")
        classpath("io.objectbox:objectbox-gradle-plugin:${Versions.objectBox}")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url = "http://dl.bintray.com/kotlin/kotlin-eap")
        google()
        jcenter()
    }
}





// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Basic Plugins for Android Dev
    id("com.android.application") version "8.1.3" apply false
    id("com.android.library") version "8.1.3" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false

    id("com.google.dagger.hilt.android") version "2.43.2" apply false

    // Add the dependency for the Google Services Gradle Plugin
    id("com.google.gms.google-services") version "4.4.0" apply false

    // Adding Safe-args to the project
    id("androidx.navigation.safeargs") version "2.5.0" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
        val nav_version = "2.7.6"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}
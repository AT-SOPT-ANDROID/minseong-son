// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    id("com.google.dagger.hilt.android") version "2.52" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.23"
    id("com.google.devtools.ksp") version "1.9.23-1.0.20"
}

buildscript {
    //ext.hilt_version = "2.52"
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.52")
        classpath("com.google.gms:google-services:4.3.3")
    }
}
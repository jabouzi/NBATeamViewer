// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    extra["kotlin_version"] = "1.6.10"
    val kotlin_version = "1.6.10"
    repositories {
        google()
        mavenCentral()
        
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.40")
        classpath(kotlin("gradle-plugin", version = kotlin_version))
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
 }

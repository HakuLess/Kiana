import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.jetbrains.kotlin.com.google.common.collect.ImmutableSet
import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.apache.commons.io.FileUtils
import java.io.*
import org.objectweb.asm.*
import jdk.internal.org.objectweb.asm.Opcodes.ASM5


plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kiana")
}

android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "com.haku.kiana"
        minSdkVersion(19)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    lintOptions {
        isAbortOnError = false
    }
}


dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))
    implementation("androidx.core:core-ktx:1.1.0-alpha03")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("com.google.android.material:material:1.0.0")
}
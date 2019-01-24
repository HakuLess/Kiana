import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("java-library")
    id("java-gradle-plugin")
    id("kotlin")
    id("maven-publish")
}

publishing {
    publications {
        create("mavenJava", MavenPublication::class.java) {
            groupId = "com.haku.less"
            artifactId = "kiana"
            version = "0.0.1"

            from(components["java"])
        }
    }

    repositories {
        maven("/Users/HaKu/WorkSpace")
    }
}


dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))
    implementation(gradleApi())
    implementation(localGroovy())
    implementation("com.android.tools.build:gradle:3.1.4")
}

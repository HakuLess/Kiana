package com.haku.kiana.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project


/**
 * Usage:
 *
 * Created by HaKu on 2019-01-12.
 */
class HakuPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.task("HaKuPlugin") {
            println("test Plugin Success")
        }.doLast {
            println("doLast")
        }
    }
}

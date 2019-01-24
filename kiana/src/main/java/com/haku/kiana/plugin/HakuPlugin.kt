package com.haku.kiana.plugin

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryPlugin
import com.haku.kiana.transform.VisitorTransForm
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project


/**
 * Usage:
 *
 * Created by HaKu on 2019-01-12.
 */
open class HakuPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        val isAndroid =
            project.plugins.hasPlugin(AppPlugin::class.java) || project.plugins.hasPlugin(LibraryPlugin::class.java)
        if (!isAndroid) {
            throw GradleException("'com.android.application' or 'com.android.library' plugin required.")
        }

        println("Apply Transform Start")
        val transform = VisitorTransForm()

//        val extension = target.extensions.create("logALot", LogALotExtension::class.java)

        val android = project.extensions.findByName("android") as BaseExtension
        android.registerTransform(transform)

//        project.extensions.getByType(AppExtension::class.java).registerTransform(transform)
        println("Apply Transform Finish")

        project.task("HaKuPlugin") {
            println("test Plugin Success")
        }.doLast {
            println("doLast")
        }
    }
}

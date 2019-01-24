package com.haku.kiana.transform

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager


/**
 * Usage:
 *
 * Created by HaKu on 2019-01-24.
 */
class VisitorTransForm : Transform() {

    override fun getName(): String {
        return "visitor"
    }

    override fun getInputTypes(): Set<QualifiedContent.ContentType> {
        return setOf(QualifiedContent.DefaultContentType.CLASSES)
    }

    override fun isIncremental(): Boolean {
        return false
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return mutableSetOf(QualifiedContent.Scope.PROJECT)
    }

    override fun getReferencedScopes(): MutableSet<in QualifiedContent.Scope> {
        return mutableSetOf(
            QualifiedContent.Scope.EXTERNAL_LIBRARIES,
            QualifiedContent.Scope.SUB_PROJECTS
        )
    }

    override fun transform(transformInvocation: TransformInvocation?) {
        super.transform(transformInvocation)
        if (transformInvocation == null) {
            return
        }
        val input = transformInvocation.inputs
        input.forEach {

            val dirInputs = it.directoryInputs
            val jarInputs = it.jarInputs

            dirInputs.forEach { dirInput ->
                println("transform DirectoryInput:" + dirInput.file.absolutePath)
//                doASMTest(dirInput.file.absolutePath)
            }
        }
    }
}
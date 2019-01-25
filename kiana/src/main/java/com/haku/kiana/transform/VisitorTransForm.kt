package com.haku.kiana.transform

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.io.FileUtils
import org.gradle.api.GradleException
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import java.io.*


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
//            QualifiedContent.Scope.PROJECT
        )
    }

    override fun transform(transformInvocation: TransformInvocation?) {
        if (transformInvocation == null) {
            return
        }

        transformInvocation.inputs.forEach { input ->
            input.directoryInputs.forEach { dirInput ->
                doASMTest(dirInput.file.absolutePath)

                val dest = transformInvocation.outputProvider.getContentLocation(
                    dirInput.name,
                    dirInput.contentTypes, dirInput.scopes,
                    Format.DIRECTORY
                )
                FileUtils.copyDirectory(dirInput.file, dest)
            }
        }
    }

    private fun doASMTest(rootFolder: String) {

        val iter = FileUtils.iterateFiles(File(rootFolder), null, true)
        while (iter.hasNext()) {

            val file = iter.next()
            println("ASM manipulate ${file.name}")
            if (file.name == "MainActivity.class") {
                // ASM
                processClass(file)
            }
        }
    }

    private fun processClass(file: File) {
        println("start process class " + file.path)
        val optClass = File(file.parent, file.name + ".opt")
        var inputStream: FileInputStream? = null
        var outputStream: FileOutputStream? = null
        try {
            inputStream = FileInputStream(file)
            outputStream = FileOutputStream(optClass)
            val bytes = referHack(inputStream)
            outputStream.write(bytes)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

        }
        if (file.exists()) {
            file.delete()
        }
        optClass.renameTo(file)
    }

    private fun referHack(inputStream: InputStream): ByteArray? {
        try {
            val classReader = ClassReader(inputStream)
            val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
            val changeVisitor = ChangeVisitor(Opcodes.ASM4, classWriter)
            classReader.accept(changeVisitor, ClassReader.EXPAND_FRAMES)
            return classWriter.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
        }
        return null
    }
}
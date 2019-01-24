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

class TestTransForm : Transform() {
    override fun getName(): String {
        return "test"
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return mutableSetOf(QualifiedContent.DefaultContentType.CLASSES)
    }

    override fun isIncremental(): Boolean {
        return false
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun transform(transformInvocation: TransformInvocation?) {
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

//            jarInputs.forEach { jarInput ->
//                println("transform jarInput:" + jarInput.file.absolutePath)
//            }
        }
    }

    private fun doASMTest(rootFolder: String) {

        val iter = FileUtils.iterateFiles(File(rootFolder), null, true)
        while (iter.hasNext()) {
            val file = iter.next()
            if (file.name == "MainActivity.class") {

                //ASM
                println("ASM manipulate")
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

class ChangeVisitor(p0: Int, p1: ClassVisitor?) : ClassVisitor(p0, p1) {

    override fun visitField(access: Int, name: String?, desc: String?, signature: String?, value: Any?): FieldVisitor {
        println("$access $name $desc $signature $value")
//        cv.visitField(Opcodes.ACC_PUBLIC, "c", Type.getDescriptor(Int::class.javaPrimitiveType), null, null)
        cv.visitField(Opcodes.ACC_PUBLIC, "d", Type.getDescriptor(Int::class.javaPrimitiveType), null, 1)
        return super.visitField(access, name, desc, signature, value)
    }

    override fun visitEnd() {
        super.visitEnd()
    }
}
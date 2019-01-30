package com.haku.kiana.transform

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type


/**
 * Usage:
 *
 * Created by HaKu on 2019-01-24.
 */
class ChangeVisitor(p0: Int, p1: ClassVisitor?) : ClassVisitor(p0, p1) {

    var hack: MethodVisitor? = null

    override fun visitMethod(
        access: Int,
        name: String?,
        desc: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val mv = cv.visitMethod(access, name, desc, signature, exceptions)

//        if (name == "clickViewHack") {
//            return cv.visitMethod(access, "clickView", desc, signature, exceptions)
//        }
//        if (name == "clickView") {
//            return cv.visitMethod(access, "clickViewHack", desc, signature, exceptions)
//        }
//        return mv
        return HackMethodVisitor(name ?: "", mv)
    }

    override fun visitEnd() {
        super.visitEnd()
    }
}

class HackMethodVisitor(private val methodName: String, mv: MethodVisitor) : MethodVisitor(Opcodes.ASM5, mv) {

    private val methodList = arrayListOf<MethodIns>()

    override fun visitCode() {

    }

    override fun visitMethodInsn(opcode: Int, owner: String?, name: String?, desc: String?, itf: Boolean) {
        println("$methodName Visit In: $opcode $owner $name $desc $itf")
        when (name) {
            "clickView" -> {
                mv.visitMethodInsn(opcode, owner, name + "Hack", desc, itf)
            }
//            "clickViewHack" -> {
//                methodList.add(MethodIns(opcode, owner, name, desc, itf))
//                mv.visitMethodInsn(opcode, owner, name, desc, itf)
//            }
            else -> {
                mv.visitMethodInsn(opcode, owner, name, desc, itf)
            }
        }
//        if (name == "clickView") {
//            mv.visitMethodInsn(opcode, owner, name + "Hack", desc, itf)
//        } else {
//            mv.visitMethodInsn(opcode, owner, name, desc, itf)
//        }
    }
}
package com.haku.kiana.transform

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type


/**
 * Usage:
 *
 * Created by HaKu on 2019-01-24.
 */
class ChangeVisitor(p0: Int, p1: ClassVisitor?) : ClassVisitor(p0, p1) {

    override fun visitField(access: Int, name: String?, desc: String?, signature: String?, value: Any?): FieldVisitor {
        println("$access $name $desc $signature $value")
        cv.visitField(Opcodes.ACC_PUBLIC, "c", Type.getDescriptor(Int::class.javaPrimitiveType), null, 2)
        cv.visitField(Opcodes.ACC_PUBLIC, "d", Type.getDescriptor(Int::class.javaPrimitiveType), null, 1)
        return super.visitField(access, name, desc, signature, value)
    }

    override fun visitEnd() {
        super.visitEnd()
    }
}
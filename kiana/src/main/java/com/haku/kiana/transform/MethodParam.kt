package com.haku.kiana.transform


/**
 * Usage:
 *
 * Created by HaKu on 2019-01-29.
 */
data class MethodParam(
    val access: Int,
    val name: String?,
    val desc: String?,
    val signature: String?,
    val exceptions: Array<out String>?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MethodParam

        if (access != other.access) return false
        if (name != other.name) return false
        if (desc != other.desc) return false
        if (signature != other.signature) return false
        if (exceptions != null) {
            if (other.exceptions == null) return false
            if (!exceptions.contentEquals(other.exceptions)) return false
        } else if (other.exceptions != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = access
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (desc?.hashCode() ?: 0)
        result = 31 * result + (signature?.hashCode() ?: 0)
        result = 31 * result + (exceptions?.contentHashCode() ?: 0)
        return result
    }
}

data class MethodIns(
    val opcode: Int,
    val owner: String?,
    val name: String?,
    val desc: String?,
    val itf: Boolean
)
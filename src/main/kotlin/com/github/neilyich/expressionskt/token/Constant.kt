package com.github.neilyich.expressionskt.token

class Constant<V: Any>(private val value: V, private val valueClass: Class<in V>) : Operand<V>() {

    constructor(value: V): this(value, value.javaClass)

    override fun value(): V = value

    override fun valueClass(): Class<in V> = valueClass

    companion object {
        @JvmStatic
        inline fun <reified V: Any> of(value: V): Constant<V> = Constant(value, V::class.javaObjectType)
    }

    override fun toString(): String {
        return value.toString()
    }
}
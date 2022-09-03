package com.github.neilyich.expressionskt.token

import java.util.function.Supplier

open class Variable<V: Any>(private val valueSupplier: Supplier<V?>, private val valueClassSupplier: Supplier<Class<in V>>) : Operand<V>() {

    final override fun value(): V = valueSupplier.get() ?: throw RuntimeException("Could not get value for variable $this")

    final override fun valueClass(): Class<in V> = valueClassSupplier.get()

}
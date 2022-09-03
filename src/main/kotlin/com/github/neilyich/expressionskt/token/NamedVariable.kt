package com.github.neilyich.expressionskt.token

import java.util.function.Supplier

class NamedVariable<V: Any>(val name: String, valueSupplier: Supplier<V?>, valueClassSupplier: Supplier<Class<in V>>) : Variable<V>(valueSupplier, valueClassSupplier) {

    override fun toString(): String {
        return name
    }

}
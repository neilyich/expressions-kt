package com.github.neilyich.expressionskt.token

class OperandsEnumeration(private val operands: List<Operand<*>>) : Operand<List<*>>() {
    override fun value(): List<Operand<*>> = operands

    override fun valueClass(): Class<in Any> {
        return operands.javaClass
    }

    operator fun <V> plus(operand: Operand<V>): OperandsEnumeration {
        return OperandsEnumeration(operands + operand)
    }

    companion object {
        @JvmStatic
        fun empty() = OperandsEnumeration(listOf())
    }

    override fun toString(): String {
        return operands.toString()
    }
}
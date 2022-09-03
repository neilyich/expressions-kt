package com.github.neilyich.expressionskt.evaluator.args

import com.github.neilyich.expressionskt.token.Operand

open class SimpleOperandsHolder(private val operands: List<Operand<*>>) : OperandsHolder {

    override fun <V: Any> get(i: Int, clazz: Class<V>): Operand<V> {
        val op = operands[i]
        return op as Operand<V>
    }

    override fun operandsCount(): Int = operands.size

    override fun getClass(i: Int): Class<*> = operands[i].valueClass()

    override fun operands(): List<Operand<*>> {
        return operands
    }

    override fun toString(): String {
        return operands.toString()
    }
}
package com.github.neilyich.expressionskt.evaluator.args

import com.github.neilyich.expressionskt.token.Operand

object EmptyOperandsHolder : OperandsHolder {

    override fun <V: Any> get(i: Int, clazz: Class<V>): Operand<V> {
        throw UnsupportedOperationException("operands holder is empty")
    }

    override fun operandsCount(): Int = 0

    override fun getClass(i: Int): Class<*> {
        throw UnsupportedOperationException("operands holder is empty")
    }

    override fun operands(): List<Operand<out Any>> {
        return emptyList()
    }
}
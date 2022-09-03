package com.github.neilyich.expressionskt.token.operator.evaluators

import com.github.neilyich.expressionskt.evaluator.BinaryEvaluator
import com.github.neilyich.expressionskt.token.Operand
import com.github.neilyich.expressionskt.token.OperandsEnumeration

object CommaEvaluator : BinaryEvaluator<List<*>, Any, List<*>>(List::class.javaObjectType, Any::class.javaObjectType) {

    override fun evaluate(left: Operand<List<*>>, right: Operand<Any>): Operand<List<*>> {
        if (left is OperandsEnumeration) {
            return left + right
        }
        return OperandsEnumeration(listOf(left, right))
    }

    override fun isCommutative(): Boolean = false

    override fun resultClass(): Class<List<*>> = List::class.java
}
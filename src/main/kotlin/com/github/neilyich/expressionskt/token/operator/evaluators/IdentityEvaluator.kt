package com.github.neilyich.expressionskt.token.operator.evaluators

import com.github.neilyich.expressionskt.evaluator.UnaryEvaluator
import com.github.neilyich.expressionskt.token.Constant
import com.github.neilyich.expressionskt.token.Operand

object IdentityEvaluator : UnaryEvaluator<Any, Any>(Any::class.javaObjectType) {
    override fun evaluate(operand: Operand<Any>): Operand<Any> {
        return operand
    }

    override fun resultClass(): Class<Any> {
        return Any::class.java
    }
}
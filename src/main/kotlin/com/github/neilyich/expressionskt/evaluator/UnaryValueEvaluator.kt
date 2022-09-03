package com.github.neilyich.expressionskt.evaluator

import com.github.neilyich.expressionskt.token.Constant
import com.github.neilyich.expressionskt.token.Operand

abstract class UnaryValueEvaluator<Argument: Any, Result: Any>(supportedClass: Class<Argument>): UnaryEvaluator<Argument, Result>(supportedClass) {
    abstract fun evaluate(arg: Argument): Result

    final override fun evaluate(operand: Operand<Argument>): Operand<Result> {
        return Constant(evaluate(operand.value()))
    }
}
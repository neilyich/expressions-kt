package com.github.neilyich.expressionskt.evaluator

import com.github.neilyich.expressionskt.token.Constant
import com.github.neilyich.expressionskt.token.Operand

abstract class NoArgsValueEvaluator<Result: Any> : NoArgsEvaluator<Result>() {
    abstract fun result(): Result

    final override fun resultOperand(): Operand<Result> = Constant(result())
}
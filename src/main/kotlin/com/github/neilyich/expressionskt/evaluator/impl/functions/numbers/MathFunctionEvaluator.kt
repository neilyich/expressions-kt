package com.github.neilyich.expressionskt.evaluator.impl.functions.numbers

import com.github.neilyich.expressionskt.evaluator.UnaryValueEvaluator

abstract class MathFunctionEvaluator(private val function: (Double) -> Number): UnaryValueEvaluator<Number, Number>(Number::class.java) {
    final override fun evaluate(arg: Number): Number {
        return function.invoke(arg.toDouble())
    }

    override fun resultClass(): Class<Number> = Number::class.java
}
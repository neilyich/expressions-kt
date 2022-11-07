package com.github.neilyich.expressionskt.evaluator.impl.operators.numbers

import com.github.neilyich.expressionskt.evaluator.UnaryValueEvaluator
import com.github.neilyich.expressionskt.evaluator.impl.decimal
import java.math.BigDecimal
import java.math.BigInteger

abstract class UnaryNumberEvaluator: UnaryValueEvaluator<Number, Number>(Number::class.javaObjectType) {
    final override fun evaluate(arg: Number): Number {
        if (arg is BigInteger) {
            return evaluateBigInteger(arg)
        }
        return evaluateBigDecimal(arg.decimal())
    }

    override fun resultClass(argClasses: List<Class<Any>>): Class<Number> = Number::class.java

    abstract fun evaluateBigInteger(arg: BigInteger): Number
    abstract fun evaluateBigDecimal(arg: BigDecimal): Number
}
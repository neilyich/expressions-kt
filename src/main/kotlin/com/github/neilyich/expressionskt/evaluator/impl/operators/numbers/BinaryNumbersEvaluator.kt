package com.github.neilyich.expressionskt.evaluator.impl.operators.numbers

import com.github.neilyich.expressionskt.evaluator.BinaryValuesEvaluator
import com.github.neilyich.expressionskt.evaluator.impl.decimal
import java.math.BigDecimal
import java.math.BigInteger

abstract class BinaryNumbersEvaluator : BinaryValuesEvaluator<Number, Number, Number>(Number::class.javaObjectType, Number::class.javaObjectType) {
    final override fun evaluate(left: Number, right: Number): Number {
        if (left is BigInteger && right is BigInteger) {
            return evaluateBigInteger(left, right)
        }
        return evaluateBigDecimal(left.decimal(), right.decimal())
    }

    override fun resultClass(argClasses: List<Class<Any>>): Class<Number> = Number::class.java

    abstract fun evaluateBigInteger(left: BigInteger, right: BigInteger): Number
    abstract fun evaluateBigDecimal(left: BigDecimal, right: BigDecimal): Number
}
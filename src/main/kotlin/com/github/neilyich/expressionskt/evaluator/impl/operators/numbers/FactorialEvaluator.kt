package com.github.neilyich.expressionskt.evaluator.impl.operators.numbers

import ch.obermuhlner.math.big.BigDecimalMath
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext

object FactorialEvaluator: UnaryNumberEvaluator() {

    override fun evaluateBigInteger(arg: BigInteger): Number {
        return evaluateBigDecimal(arg.toBigDecimal())
    }

    override fun evaluateBigDecimal(arg: BigDecimal): Number {
        return BigDecimalMath.factorial(arg, MathContext.DECIMAL128)
    }
}
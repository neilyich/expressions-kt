package com.github.neilyich.expressionskt.evaluator.impl.operators.numbers

import java.math.BigDecimal
import java.math.BigInteger

object UnaryMinusEvaluator : UnaryNumberEvaluator() {
    override fun evaluateBigInteger(arg: BigInteger): Number {
        return arg.unaryMinus()
    }

    override fun evaluateBigDecimal(arg: BigDecimal): Number {
        return arg.unaryMinus()
    }
}
package com.github.neilyich.expressionskt.evaluator.impl.operators.numbers

import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext

object DivEvaluator: BinaryNumbersEvaluator() {
    override fun evaluateBigInteger(left: BigInteger, right: BigInteger): Number {
        val res = left.divideAndRemainder(right)
        val div = res[0]
        val rem = res[1]
        if (rem == BigInteger.ZERO) return div
        return evaluateBigDecimal(left.toBigDecimal(), right.toBigDecimal())
    }

    override fun evaluateBigDecimal(left: BigDecimal, right: BigDecimal): Number {
        return try {
            left.divide(right, MathContext.UNLIMITED)
        } catch (e: ArithmeticException) {
            left.divide(right, MathContext.DECIMAL128)
        }
    }

    override fun isCommutative(): Boolean {
        return false
    }
}
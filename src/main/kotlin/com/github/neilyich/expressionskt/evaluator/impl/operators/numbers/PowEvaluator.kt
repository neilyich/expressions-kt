package com.github.neilyich.expressionskt.evaluator.impl.operators.numbers

import ch.obermuhlner.math.big.BigDecimalMath
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import kotlin.math.pow

object PowEvaluator: BinaryNumbersEvaluator() {

    override fun evaluateBigInteger(left: BigInteger, right: BigInteger): BigDecimal {
        return evaluateBigDecimal(left.toBigDecimal(), right.toBigDecimal())
    }

    override fun evaluateBigDecimal(left: BigDecimal, right: BigDecimal): BigDecimal {
        return BigDecimalMath.pow(left, right, MathContext.DECIMAL128)
    }

    override fun isCommutative(): Boolean {
        return false
    }
}
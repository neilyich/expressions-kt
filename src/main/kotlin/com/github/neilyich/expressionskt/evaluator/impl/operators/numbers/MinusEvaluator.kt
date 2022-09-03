package com.github.neilyich.expressionskt.evaluator.impl.operators.numbers

import java.math.BigDecimal
import java.math.BigInteger

object MinusEvaluator : BinaryNumbersEvaluator() {
    override fun evaluateBigInteger(left: BigInteger, right: BigInteger): BigInteger {
        return left - right
    }

    override fun evaluateBigDecimal(left: BigDecimal, right: BigDecimal): BigDecimal {
        return left - right
    }

    override fun isCommutative(): Boolean {
        return false
    }
}
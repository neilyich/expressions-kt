package com.github.neilyich.expressionskt.evaluator.impl.functions.numbers

import com.github.neilyich.expressionskt.evaluator.NoArgsValueEvaluator
import java.lang.System.currentTimeMillis
import java.math.BigInteger

object NowEvaluator: NoArgsValueEvaluator<BigInteger>() {
    override fun result(): BigInteger {
        return currentTimeMillis().toBigInteger()
    }

    override fun resultClass(argClasses: List<Class<Any>>): Class<BigInteger> = BigInteger::class.java
}
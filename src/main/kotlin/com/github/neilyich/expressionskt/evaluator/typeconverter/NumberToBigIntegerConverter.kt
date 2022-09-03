package com.github.neilyich.expressionskt.evaluator.typeconverter

import com.github.neilyich.expressionskt.evaluator.impl.integer
import java.math.BigInteger

object NumberToBigIntegerConverter : TypeConverter<Number, BigInteger> {
    override fun convert(value: Number): BigInteger {
        return value.integer()
    }

    override fun fromClass(): Class<Number> {
        return Number::class.java
    }

    override fun toClass(): Class<BigInteger> {
        return BigInteger::class.java
    }
}
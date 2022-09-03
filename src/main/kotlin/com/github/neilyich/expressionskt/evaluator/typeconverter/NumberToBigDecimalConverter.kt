package com.github.neilyich.expressionskt.evaluator.typeconverter

import com.github.neilyich.expressionskt.evaluator.impl.decimal
import java.math.BigDecimal

object NumberToBigDecimalConverter : TypeConverter<Number, BigDecimal> {
    override fun convert(value: Number): BigDecimal {
        return value.decimal()
    }

    override fun fromClass(): Class<Number> {
        return Number::class.java
    }

    override fun toClass(): Class<BigDecimal> {
        return BigDecimal::class.java
    }
}
package com.github.neilyich.expressionskt.evaluator.impl

import java.math.BigDecimal
import java.math.BigInteger


fun Number.decimal(): BigDecimal {
    if (this is BigDecimal) return this
    if (this is BigInteger) return toBigDecimal()
    return when (this) {
        is Double -> toBigDecimal()
        is Long -> toBigDecimal()
        is Int -> toBigDecimal()
        is Float -> toBigDecimal()
        is Short -> toInt().toBigDecimal()
        is Byte -> toInt().toBigDecimal()
        else -> toDouble().toBigDecimal()
    }
}

fun Number.integer(): BigInteger {
    if (this is BigInteger) return this
    if (this is BigDecimal) return toBigIntegerExact()
    return decimal().toBigIntegerExact()
}

fun Number.compareTo(other: Number): Int {
    return toDouble().compareTo(other.toDouble())
}


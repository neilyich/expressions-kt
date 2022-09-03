package com.github.neilyich.expressionskt.token

sealed class Operand<V> : ExpressionToken {
    abstract fun value(): V
    abstract fun valueClass(): Class<in V>
}
package com.github.neilyich.expressionskt.token

sealed class UnaryOperator(str: String, priority: Int) : Operator(str, 1, priority) {
}
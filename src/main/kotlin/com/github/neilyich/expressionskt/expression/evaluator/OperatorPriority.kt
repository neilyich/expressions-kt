package com.github.neilyich.expressionskt.expression.evaluator

import com.github.neilyich.expressionskt.token.Operator

data class OperatorPriority(val operator: Operator, val bracketsLevel: Int): Comparable<OperatorPriority> {
    override fun compareTo(other: OperatorPriority): Int {
        val cmp = bracketsLevel.compareTo(other.bracketsLevel)
        if (cmp != 0) return cmp
        return operator.priority.compareTo(other.operator.priority)
    }
}
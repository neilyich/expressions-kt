package com.github.neilyich.expressionskt.token

sealed class Operator(val str: String, val operandsCount: Int, val priority: Int) : ExpressionToken {

    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Operator) return false

        if (str != other.str) return false
        if (operandsCount != other.operandsCount) return false
        if (priority != other.priority) return false

        return true
    }

    final override fun hashCode(): Int {
        var result = str.hashCode()
        result = 31 * result + operandsCount
        result = 31 * result + priority
        return result
    }

    final override fun toString(): String {
        return str
    }
}
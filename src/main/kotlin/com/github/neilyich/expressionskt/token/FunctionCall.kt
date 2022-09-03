package com.github.neilyich.expressionskt.token

import com.github.neilyich.expressionskt.token.operator.OperatorPriorities

private fun formatName(str: String): String {
    if (str.endsWith("(")) return str
    return "$str("
}

open class FunctionCall(str: String) : Operator(formatName(str), 1, OperatorPriorities.FUNCTION) {
}
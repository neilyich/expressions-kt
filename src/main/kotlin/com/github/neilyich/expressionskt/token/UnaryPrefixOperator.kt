package com.github.neilyich.expressionskt.token

import com.github.neilyich.expressionskt.token.operator.OperatorPriorities

open class UnaryPrefixOperator(str: String) : UnaryOperator(str, OperatorPriorities.UNARY_PREFIX_OPERATOR) {
}
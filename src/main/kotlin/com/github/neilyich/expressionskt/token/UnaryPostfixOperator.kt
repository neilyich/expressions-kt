package com.github.neilyich.expressionskt.token

import com.github.neilyich.expressionskt.token.operator.OperatorPriorities

open class UnaryPostfixOperator(str: String) : UnaryOperator(str, OperatorPriorities.UNARY_POSTFIX_OPERATOR) {
}
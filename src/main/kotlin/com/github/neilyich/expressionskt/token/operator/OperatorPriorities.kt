package com.github.neilyich.expressionskt.token.operator

object OperatorPriorities {
    const val UNARY_POSTFIX_OPERATOR = 1 shl 8

    const val POW = 1 shl 7 // 64

    const val UNARY_PREFIX_OPERATOR = 1 shl 6 // 128


    const val MULT_DIV = 1 shl 5 // 32

    const val PLUS_MINUS = 1 shl 4 // 16

    const val FUNCTION = 1 shl 3 // 8
    const val COMMA = 1 + FUNCTION

    const val BRACKET = 0
}
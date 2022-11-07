package com.github.neilyich.expressionskt.token.operator

object OperatorPriorities {
    const val UNARY_POSTFIX_OPERATOR = 500

    const val POW = 400

    const val UNARY_PREFIX_OPERATOR = 300


    const val MULT_DIV = 200

    const val PLUS_MINUS = 100

    const val COMPARING = 75

    const val AND = 60

    const val OR = 55

    const val FUNCTION = 10
    const val COMMA = 1 + FUNCTION

    const val BRACKET = 0
}
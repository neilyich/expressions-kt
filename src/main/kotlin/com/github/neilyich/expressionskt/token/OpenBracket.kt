package com.github.neilyich.expressionskt.token

import com.github.neilyich.expressionskt.token.operator.OperatorPriorities

object OpenBracket : Operator("(", 1, OperatorPriorities.BRACKET) {
}
package com.github.neilyich.expressionskt.token

import com.github.neilyich.expressionskt.token.operator.OperatorPriorities

object Comma : Operator(",", 2, OperatorPriorities.COMMA) {
}
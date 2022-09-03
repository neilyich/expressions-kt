package com.github.neilyich.expressionskt.token.operator.impl.operators

import com.github.neilyich.expressionskt.token.BinaryOperator
import com.github.neilyich.expressionskt.token.operator.OperatorPriorities

object Minus: BinaryOperator("-", OperatorPriorities.PLUS_MINUS) {
}
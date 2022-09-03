package com.github.neilyich.expressionskt.token.operator.impl.operators

import com.github.neilyich.expressionskt.token.BinaryOperator
import com.github.neilyich.expressionskt.token.operator.OperatorPriorities

object Div: BinaryOperator("/", OperatorPriorities.MULT_DIV) {
}
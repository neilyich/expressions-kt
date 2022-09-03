package com.github.neilyich.expressionskt.parser

import com.github.neilyich.expressionskt.expression.Expression

interface ExpressionParser {
    fun parse(expr: String): Expression
}
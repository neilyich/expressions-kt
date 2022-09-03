package com.github.neilyich.expressionskt.expression

import com.github.neilyich.expressionskt.token.ExpressionToken

interface Expression {
    fun content(): List<ExpressionToken>
}
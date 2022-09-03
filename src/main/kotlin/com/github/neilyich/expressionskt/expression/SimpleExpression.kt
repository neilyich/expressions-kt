package com.github.neilyich.expressionskt.expression

import com.github.neilyich.expressionskt.token.ExpressionToken

class SimpleExpression(private val tokens: List<ExpressionToken>) : Expression {
    override fun content(): List<ExpressionToken> = tokens
}
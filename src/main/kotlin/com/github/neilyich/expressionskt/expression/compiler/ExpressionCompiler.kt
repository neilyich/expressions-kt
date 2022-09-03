package com.github.neilyich.expressionskt.expression.compiler

import com.github.neilyich.expressionskt.expression.Expression

interface ExpressionCompiler {
    fun compile(expression: Expression): CompiledExpression
}
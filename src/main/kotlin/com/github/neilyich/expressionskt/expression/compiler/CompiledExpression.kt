package com.github.neilyich.expressionskt.expression.compiler

import com.github.neilyich.expressionskt.expression.Expression
import com.github.neilyich.expressionskt.expression.compiler.task.CompiledTask
import com.github.neilyich.expressionskt.expression.compiler.task.EvaluationTask
import com.github.neilyich.expressionskt.token.ExpressionToken

class CompiledExpression(private val content: List<ExpressionToken>, private val tasks: List<CompiledTask>) : Expression {
    override fun content(): List<ExpressionToken> = content
    fun tasks() = tasks
}
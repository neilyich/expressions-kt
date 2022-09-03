package com.github.neilyich.expressionskt.expression.compiler

import com.github.neilyich.expressionskt.expression.Expression
import com.github.neilyich.expressionskt.expression.compiler.task.CompiledTask
import com.github.neilyich.expressionskt.expression.compiler.task.EvaluationTask
import com.github.neilyich.expressionskt.token.ExpressionToken
import com.github.neilyich.expressionskt.token.Operand
import com.github.neilyich.expressionskt.token.Variable

class CompiledExpression(private val content: List<ExpressionToken>, private val resultOperand: Operand<Any>) : Expression {
    override fun content(): List<ExpressionToken> = content

    fun evaluate(): Any {
        return resultOperand.value()
    }
}
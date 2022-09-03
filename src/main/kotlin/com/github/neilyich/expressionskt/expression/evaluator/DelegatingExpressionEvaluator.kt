package com.github.neilyich.expressionskt.expression.evaluator

import com.github.neilyich.expressionskt.expression.Expression
import com.github.neilyich.expressionskt.expression.compiler.CompiledExpression

class DelegatingExpressionEvaluator<E : Expression>(
    private val defaultDelegate: ExpressionEvaluator<E>,
    private val compiledExpressionEvaluator: ExpressionEvaluator<CompiledExpression>
) : ExpressionEvaluator<E> {
    override fun <R : Any> evaluate(expression: E, resultClass: Class<R>): R {
        if (expression is CompiledExpression) return compiledExpressionEvaluator.evaluate(expression, resultClass)
        return defaultDelegate.evaluate(expression, resultClass)
    }
}
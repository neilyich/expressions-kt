package com.github.neilyich.expressionskt.expression.evaluator

import com.github.neilyich.expressionskt.evaluator.evaluationsuitability.EvaluationSuitabilityProvider
import com.github.neilyich.expressionskt.expression.compiler.CompiledExpression

class CompiledExpressionEvaluator(
    private val suitabilityProvider: EvaluationSuitabilityProvider
) : ExpressionEvaluator<CompiledExpression> {
    override fun <R : Any> evaluate(expression: CompiledExpression, resultClass: Class<R>): R {
        val result = expression.evaluate()
        val resultSuitability = suitabilityProvider.forClasses(resultClass, result.javaClass)
        if (resultSuitability.isSuitable) {
            return resultSuitability.typeConverters[0].convert(result) as R
        }
        throw RuntimeException("Unable to cast actual result ${result.javaClass} to expected $resultClass")
    }
}
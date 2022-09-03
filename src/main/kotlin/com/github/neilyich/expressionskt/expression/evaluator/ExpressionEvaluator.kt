package com.github.neilyich.expressionskt.expression.evaluator

import com.github.neilyich.expressionskt.expression.Expression

interface ExpressionEvaluator<E: Expression> {
    fun <R : Any> evaluate(expression: E, resultClass: Class<R>): R
}
package com.github.neilyich.expressionskt.evaluator.impl.operators.logical

import com.github.neilyich.expressionskt.evaluator.UnaryValueEvaluator

object NotEvaluator: UnaryValueEvaluator<Boolean, Boolean>(Boolean::class.javaObjectType) {
    override fun evaluate(arg: Boolean): Boolean = !arg

    override fun resultClass(argClasses: List<Class<Any>>): Class<Boolean> = Boolean::class.javaObjectType
}
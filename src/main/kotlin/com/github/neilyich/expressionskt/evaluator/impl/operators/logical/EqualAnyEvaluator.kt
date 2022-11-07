package com.github.neilyich.expressionskt.evaluator.impl.operators.logical

import com.github.neilyich.expressionskt.evaluator.BinaryValuesEvaluator

object EqualAnyEvaluator: BinaryValuesEvaluator<Any, Any, Boolean>(Any::class.java, Any::class.java) {
    override fun evaluate(left: Any, right: Any): Boolean = left == right

    override fun isCommutative(): Boolean = true

    override fun resultClass(argClasses: List<Class<Any>>): Class<Boolean> = Boolean::class.javaObjectType
}
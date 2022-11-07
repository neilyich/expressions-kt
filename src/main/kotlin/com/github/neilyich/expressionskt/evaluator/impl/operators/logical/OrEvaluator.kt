package com.github.neilyich.expressionskt.evaluator.impl.operators.logical

import com.github.neilyich.expressionskt.evaluator.BinaryValuesEvaluator

object OrEvaluator: BinaryValuesEvaluator<Boolean, Boolean, Boolean>(Boolean::class.javaObjectType, Boolean::class.javaObjectType) {
    override fun evaluate(left: Boolean, right: Boolean): Boolean = left or right

    override fun isCommutative(): Boolean = true

    override fun resultClass(argClasses: List<Class<Any>>): Class<Boolean> = Boolean::class.javaObjectType
}
package com.github.neilyich.expressionskt.evaluator.impl.operators.logical

import com.github.neilyich.expressionskt.evaluator.BinaryValuesEvaluator

object EqualComparableEvaluator: BinaryValuesEvaluator<Comparable<*>, Comparable<*>, Boolean>(Comparable::class.java, Comparable::class.java) {
    override fun evaluate(left: Comparable<*>, right: Comparable<*>): Boolean {
        return (left as Comparable<Any>).compareTo(right) == 0
    }

    override fun isCommutative(): Boolean = true

    override fun resultClass(argClasses: List<Class<Any>>): Class<Boolean> = Boolean::class.javaObjectType
}
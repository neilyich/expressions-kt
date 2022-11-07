package com.github.neilyich.expressionskt.evaluator.impl.operators.logical

import com.github.neilyich.expressionskt.evaluator.BinaryValuesEvaluator

object GreaterEvaluator: BinaryValuesEvaluator<Comparable<*>, Comparable<*>, Boolean>(Comparable::class.java, Comparable::class.java) {
    override fun evaluate(left: Comparable<*>, right: Comparable<*>): Boolean {
        return (left as Comparable<Any>) > right
    }

    override fun isCommutative(): Boolean = true

    override fun resultClass(argClasses: List<Class<Any>>): Class<Boolean> = Boolean::class.javaObjectType
}
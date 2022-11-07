package com.github.neilyich.expressionskt.evaluator.impl.functions.logical

import com.github.neilyich.expressionskt.evaluator.FunctionValuesEvaluator
import com.github.neilyich.expressionskt.utils.ClassUtils

object IfElseEvaluator: FunctionValuesEvaluator<Any, Any>() {
    override fun evaluate(args: List<Any>): Any {
        val condition = args[0] as Boolean
        return if (condition) args[1] else args[2]
    }

    private val SUPPORTED_CLASSES = listOf(Boolean::class.javaObjectType, Any::class.java, Any::class.java)

    override fun supportedValueClass(index: Int): Class<Any> {
        return SUPPORTED_CLASSES[index] as Class<Any>
    }

    override fun supportedArgumentsCount(): Int {
        return 3
    }

    override fun resultClass(argClasses: List<Class<Any>>): Class<Any> {
        val leftClass = argClasses[1]
        val rightClass = argClasses[2]
        return ClassUtils.nearestSuperClass(leftClass, rightClass) as Class<Any>
    }
}
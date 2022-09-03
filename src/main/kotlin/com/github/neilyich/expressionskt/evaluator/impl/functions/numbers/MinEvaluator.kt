package com.github.neilyich.expressionskt.evaluator.impl.functions.numbers

import com.github.neilyich.expressionskt.evaluator.FunctionValuesEvaluator
import com.github.neilyich.expressionskt.evaluator.impl.compareTo

object MinEvaluator : FunctionValuesEvaluator<Number, Number>() {
    override fun evaluate(args: List<Number>): Number {
        return args.minWithOrNull { o1, o2 -> o1.compareTo(o2) }!!
    }

    override fun supportedValueClass(index: Int): Class<Number> {
        return Number::class.javaObjectType
    }

    override fun supportedArgumentsCount(): Int = -1
    override fun resultClass(): Class<Number> = Number::class.java
}
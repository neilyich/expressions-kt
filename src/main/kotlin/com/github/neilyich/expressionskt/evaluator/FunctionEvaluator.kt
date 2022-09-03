package com.github.neilyich.expressionskt.evaluator

import com.github.neilyich.expressionskt.evaluator.args.OperandsHolder
import com.github.neilyich.expressionskt.evaluator.evaluationsuitability.EvaluationSuitabilityProvider
import com.github.neilyich.expressionskt.token.Operand

abstract class FunctionEvaluator<Argument : Any, Result : Any>: Evaluator<Result> {

    abstract fun supportedValueClass(index: Int): Class<Argument>

    abstract fun supportedArgumentsCount(): Int

    abstract fun evaluate(args: List<Operand<Argument>>): Operand<Result>

    final override fun evaluate(
        operandsHolder: OperandsHolder,
        evaluationSuitabilityProvider: EvaluationSuitabilityProvider
    ): Operand<Result> {
        val args = ArrayList<Operand<Argument>>(operandsHolder.operandsCount())
        for (i in 0 until operandsHolder.operandsCount()) {
            args.add(operandsHolder.get(i, supportedValueClass(i)))
        }
        return evaluate(args)
    }
}
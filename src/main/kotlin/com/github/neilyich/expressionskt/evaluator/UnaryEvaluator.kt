package com.github.neilyich.expressionskt.evaluator

import com.github.neilyich.expressionskt.evaluator.args.OperandsHolder
import com.github.neilyich.expressionskt.evaluator.evaluationsuitability.EvaluationSuitability
import com.github.neilyich.expressionskt.evaluator.evaluationsuitability.EvaluationSuitabilityProvider
import com.github.neilyich.expressionskt.token.Operand

abstract class UnaryEvaluator<Argument : Any, Result : Any>(private val supportedArgumentClass: Class<Argument>) : Evaluator<Result> {

    abstract fun evaluate(operand: Operand<Argument>): Operand<Result>

    final override fun evaluate(
        operandsHolder: OperandsHolder,
        evaluationSuitabilityProvider: EvaluationSuitabilityProvider
    ): Operand<Result> {
        return evaluate(operandsHolder.get(0, supportedArgumentClass))
    }

    override fun suitabilityFor(
        operandsHolder: OperandsHolder,
        evaluationSuitabilityProvider: EvaluationSuitabilityProvider
    ): EvaluationSuitability {
        if (operandsHolder.operandsCount() != 1) return EvaluationSuitability.notSuitable()
        return evaluationSuitabilityProvider.forClasses(supportedArgumentClass, operandsHolder.getClass(0))
    }
}
package com.github.neilyich.expressionskt.evaluator

import com.github.neilyich.expressionskt.evaluator.args.OperandsHolder
import com.github.neilyich.expressionskt.evaluator.evaluationsuitability.EvaluationSuitability
import com.github.neilyich.expressionskt.evaluator.evaluationsuitability.EvaluationSuitabilityProvider
import com.github.neilyich.expressionskt.token.Operand

abstract class NoArgsEvaluator<Result>: Evaluator<Result> {
    abstract fun resultOperand(): Operand<Result>

    override fun evaluate(
        operandsHolder: OperandsHolder,
        evaluationSuitabilityProvider: EvaluationSuitabilityProvider
    ): Operand<Result> {
        return resultOperand()
    }

    override fun suitabilityFor(
        operandsHolder: OperandsHolder,
        evaluationSuitabilityProvider: EvaluationSuitabilityProvider
    ): EvaluationSuitability {
        if (operandsHolder.operandsCount() != 0) return EvaluationSuitability.notSuitable()
        return EvaluationSuitability.empty()
    }
}
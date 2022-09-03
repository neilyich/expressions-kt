package com.github.neilyich.expressionskt.evaluator

import com.github.neilyich.expressionskt.evaluator.args.OperandsHolder
import com.github.neilyich.expressionskt.evaluator.evaluationsuitability.EvaluationSuitability
import com.github.neilyich.expressionskt.evaluator.evaluationsuitability.EvaluationSuitabilityProvider
import com.github.neilyich.expressionskt.token.Operand

sealed interface Evaluator<Result> {

    fun evaluate(operandsHolder: OperandsHolder, evaluationSuitabilityProvider: EvaluationSuitabilityProvider): Operand<Result>

    fun suitabilityFor(operandsHolder: OperandsHolder, evaluationSuitabilityProvider: EvaluationSuitabilityProvider): EvaluationSuitability

    fun resultClass(): Class<Result>
}
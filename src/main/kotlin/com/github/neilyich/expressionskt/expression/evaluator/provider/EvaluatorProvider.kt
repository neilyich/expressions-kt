package com.github.neilyich.expressionskt.expression.evaluator.provider

import com.github.neilyich.expressionskt.evaluator.Evaluator
import com.github.neilyich.expressionskt.evaluator.args.OperandsHolder
import com.github.neilyich.expressionskt.evaluator.evaluationsuitability.EvaluationSuitability
import com.github.neilyich.expressionskt.evaluator.evaluationsuitability.EvaluationSuitabilityProvider
import com.github.neilyich.expressionskt.token.Operator

interface EvaluatorProvider {
    fun register(operator: Operator, evaluator: Evaluator<*>)
    fun findBestEvaluator(operator: Operator, operandsHolder: OperandsHolder, suitabilityProvider: EvaluationSuitabilityProvider): Pair<Evaluator<*>, EvaluationSuitability>
}
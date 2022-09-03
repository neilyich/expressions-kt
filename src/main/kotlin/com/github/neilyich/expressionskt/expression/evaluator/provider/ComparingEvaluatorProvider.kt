package com.github.neilyich.expressionskt.expression.evaluator.provider

import com.github.neilyich.expressionskt.evaluator.Evaluator
import com.github.neilyich.expressionskt.evaluator.args.OperandsHolder
import com.github.neilyich.expressionskt.evaluator.evaluationsuitability.EvaluationSuitability
import com.github.neilyich.expressionskt.evaluator.evaluationsuitability.EvaluationSuitabilityProvider
import com.github.neilyich.expressionskt.token.Operator
import java.util.*

class ComparingEvaluatorProvider : EvaluatorProvider {

    private val operatorsEvaluators: MutableMap<Operator, MutableList<Evaluator<*>>> = mutableMapOf()

    override fun register(operator: Operator, evaluator: Evaluator<*>) {
        val evaluators = operatorsEvaluators.getOrPut(operator) { LinkedList() }
        evaluators.add(evaluator)
    }

    override fun findBestEvaluator(operator: Operator, operandsHolder: OperandsHolder, suitabilityProvider: EvaluationSuitabilityProvider): Pair<Evaluator<*>, EvaluationSuitability> {
        return operatorsEvaluators[operator]?.let {
            it.map { ev ->
                ev to ev.suitabilityFor(operandsHolder, suitabilityProvider)
            }.filter { ev ->
                ev.second.isSuitable
            }.maxByOrNull { ev ->
                ev.first.suitabilityFor(operandsHolder, suitabilityProvider)
            }
        } ?: throw RuntimeException("Unable find evaluator for operator: $operator, operands: $operandsHolder, ${operandsHolder.operands().map { it.valueClass() }}")
    }
}
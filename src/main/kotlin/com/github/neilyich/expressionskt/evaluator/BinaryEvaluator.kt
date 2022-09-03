package com.github.neilyich.expressionskt.evaluator

import com.github.neilyich.expressionskt.evaluator.args.OperandsHolder
import com.github.neilyich.expressionskt.evaluator.evaluationsuitability.EvaluationSuitability
import com.github.neilyich.expressionskt.evaluator.evaluationsuitability.EvaluationSuitabilityProvider
import com.github.neilyich.expressionskt.token.Operand

abstract class BinaryEvaluator<Left: Any, Right: Any, Result: Any>(private val supportedLeftClass: Class<Left>, private val supportedRightClass: Class<Right>) : Evaluator<Result> {

    abstract fun evaluate(left: Operand<Left>, right: Operand<Right>): Operand<Result>

    abstract fun isCommutative(): Boolean

    final override fun evaluate(
        operandsHolder: OperandsHolder,
        evaluationSuitabilityProvider: EvaluationSuitabilityProvider
    ): Operand<Result> {
        if (!isCommutative() ||
            suitabilityFor(operandsHolder.getClass(0), operandsHolder.getClass(1), evaluationSuitabilityProvider)
            >= suitabilityFor(operandsHolder.getClass(1), operandsHolder.getClass(0), evaluationSuitabilityProvider)) {
            return evaluate(operandsHolder.get(0, supportedLeftClass), operandsHolder.get(1, supportedRightClass))
        }
        return evaluate(operandsHolder.get(1, supportedLeftClass), operandsHolder.get(0, supportedRightClass))
    }

    private fun <A : Any, B : Any> suitabilityFor(
        actualLeftClass: Class<A>,
        actualRightClass: Class<B>,
        evaluationSuitabilityProvider: EvaluationSuitabilityProvider): EvaluationSuitability {
        return evaluationSuitabilityProvider.forClasses(supportedLeftClass, actualLeftClass) + evaluationSuitabilityProvider.forClasses(supportedRightClass, actualRightClass)
    }

    override fun suitabilityFor(
        operandsHolder: OperandsHolder,
        evaluationSuitabilityProvider: EvaluationSuitabilityProvider
    ): EvaluationSuitability {
        if (operandsHolder.operandsCount() != 2) return EvaluationSuitability.notSuitable()
        val suitability = suitabilityFor(operandsHolder.getClass(0), operandsHolder.getClass(1), evaluationSuitabilityProvider)
        if (isCommutative()) {
            val s = suitabilityFor(operandsHolder.getClass(1), operandsHolder.getClass(0), evaluationSuitabilityProvider)
            return maxOf(suitability, EvaluationSuitability(s.isSuitable, s.equalClasses, s.castedClasses, s.convertedClasses, s.typeConverters.reversed()))
        }
        return suitability
    }
}
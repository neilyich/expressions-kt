package com.github.neilyich.expressionskt.evaluator

import com.github.neilyich.expressionskt.evaluator.args.OperandsHolder
import com.github.neilyich.expressionskt.evaluator.evaluationsuitability.EvaluationSuitability
import com.github.neilyich.expressionskt.evaluator.evaluationsuitability.EvaluationSuitabilityProvider
import com.github.neilyich.expressionskt.token.Constant
import com.github.neilyich.expressionskt.token.Operand

abstract class FunctionValuesEvaluator<Argument: Any, Result: Any> : FunctionEvaluator<Argument, Result>() {
    abstract fun evaluate(args: List<Argument>): Result

    final override fun evaluate(args: List<Operand<Argument>>): Operand<Result> {
        return Constant(evaluate(args.map { it.value() }))
    }

    override fun suitabilityFor(
        operandsHolder: OperandsHolder,
        evaluationSuitabilityProvider: EvaluationSuitabilityProvider
    ): EvaluationSuitability {
        val supportedArgumentsCount = supportedArgumentsCount()
        if (supportedArgumentsCount < 0) {
            if (operandsHolder.operandsCount() == 0) return EvaluationSuitability.notSuitable()
        } else if (supportedArgumentsCount == 0) {
            return EvaluationSuitability.empty()
        } else if (operandsHolder.operandsCount() != supportedArgumentsCount) {
            return EvaluationSuitability.notSuitable()
        }

        var suitability = evaluationSuitabilityProvider.forClasses(supportedValueClass(0), operandsHolder.getClass(0))
        var i = 1
        while (suitability.isSuitable && i < operandsHolder.operandsCount()) {
            suitability += evaluationSuitabilityProvider.forClasses(supportedValueClass(i), operandsHolder.getClass(i++))
        }
        return suitability
    }

}
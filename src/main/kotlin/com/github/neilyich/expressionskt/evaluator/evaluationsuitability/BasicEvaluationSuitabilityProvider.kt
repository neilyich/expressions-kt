package com.github.neilyich.expressionskt.evaluator.evaluationsuitability

import com.github.neilyich.expressionskt.evaluator.typeconverter.CastingTypeConverter
import com.github.neilyich.expressionskt.evaluator.typeconverter.TypeConverter

class BasicEvaluationSuitabilityProvider: EvaluationSuitabilityProvider {
    override fun <Expected: Any, Actual: Any> forClasses(expectedClass: Class<Expected>, actualClass: Class<Actual>): EvaluationSuitability {
        if (!expectedClass.isAssignableFrom(actualClass)) return EvaluationSuitability.notSuitable()
        val eqClasses: Int
        val castedClasses: Int
        if (expectedClass == actualClass) {
            eqClasses = 1
            castedClasses = 0
        } else {
            eqClasses = 0
            castedClasses = 1
        }
        return EvaluationSuitability(true, eqClasses, castedClasses, 0, listOf(CastingTypeConverter))
    }

    override fun <From: Any, To: Any> register(
        typeConverter: TypeConverter<From, To>
    ) {
        throw UnsupportedOperationException("")
    }
}
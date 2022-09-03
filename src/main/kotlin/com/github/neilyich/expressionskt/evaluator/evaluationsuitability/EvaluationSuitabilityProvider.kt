package com.github.neilyich.expressionskt.evaluator.evaluationsuitability

import com.github.neilyich.expressionskt.evaluator.typeconverter.TypeConverter

interface EvaluationSuitabilityProvider {
    fun <Expected: Any, Actual: Any> forClasses(expectedClass: Class<Expected>, actualClass: Class<Actual>): EvaluationSuitability
    fun <From: Any, To: Any> register(typeConverter: TypeConverter<From, To>)
}
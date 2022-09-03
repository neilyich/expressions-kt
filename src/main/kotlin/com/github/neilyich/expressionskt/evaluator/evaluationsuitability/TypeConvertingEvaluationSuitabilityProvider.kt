package com.github.neilyich.expressionskt.evaluator.evaluationsuitability

import com.github.neilyich.expressionskt.evaluator.typeconverter.TypeConverter

class TypeConvertingEvaluationSuitabilityProvider: EvaluationSuitabilityProvider {

    private val typeConverters = mutableMapOf<ConversionData<*, *>, TypeConverter<Any, *>>()

    private val delegate = BasicEvaluationSuitabilityProvider()

    private data class ConversionData<From, To>(val fromClass: Class<in From>, val toClass: Class<out To>) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is ConversionData<*, *>) return false

            if (fromClass != other.fromClass) return false
            if (toClass != other.toClass) return false

            return true
        }

        override fun hashCode(): Int {
            var result = fromClass.hashCode()
            result = 31 * result + toClass.hashCode()
            return result
        }
    }

    override fun <Expected: Any, Actual: Any> forClasses(expectedClass: Class<Expected>, actualClass: Class<Actual>): EvaluationSuitability {
        var suitability = delegate.forClasses(expectedClass, actualClass)
        if (suitability.isSuitable) return suitability
        typeConverters
            .mapValues { (key, value) ->
                value to delegate.forClasses(key.fromClass, actualClass) + delegate.forClasses(key.toClass, expectedClass) }
            .filter {
                it.value.second.isSuitable
            }
            .maxByOrNull {
                it.value.second
            }?.value?.first?.let { converter ->
            suitability = EvaluationSuitability(true, 0, 0, 1, listOf(converter))
        }
        return suitability
    }

    override fun <From: Any, To: Any> register(
        typeConverter: TypeConverter<From, To>
    ) {
        val conversionData = ConversionData(typeConverter.fromClass(), typeConverter.toClass())
        typeConverters[conversionData] = typeConverter as TypeConverter<Any, *>
    }
}
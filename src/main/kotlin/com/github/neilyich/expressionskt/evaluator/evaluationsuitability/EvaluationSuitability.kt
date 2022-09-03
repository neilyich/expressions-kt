package com.github.neilyich.expressionskt.evaluator.evaluationsuitability

import com.github.neilyich.expressionskt.evaluator.typeconverter.TypeConverter

data class EvaluationSuitability(
    val isSuitable: Boolean,
    val equalClasses: Int, // a.class == b.class
    val castedClasses: Int, // a == (A) b
    val convertedClasses: Int, // typeConverter(a.class) = b.class
    val typeConverters: List<TypeConverter<Any, *>>
) : Comparable<EvaluationSuitability> {

    companion object {
        @JvmStatic
        fun notSuitable(): EvaluationSuitability {
            return EvaluationSuitability(false, 0, 0, 0, listOf())
        }

        @JvmStatic
        fun empty(): EvaluationSuitability {
            return EvaluationSuitability(true, 0, 0, 0, listOf())
        }
    }

    operator fun plus(other: EvaluationSuitability): EvaluationSuitability {
        if (isSuitable && other.isSuitable)
            return EvaluationSuitability(
                true,
                equalClasses + other.equalClasses,
                castedClasses + other.castedClasses,
                convertedClasses + other.convertedClasses,
                typeConverters + other.typeConverters
            )
        return notSuitable()
    }

    override fun compareTo(other: EvaluationSuitability): Int {
        val cmp = isSuitable.compareTo(other.isSuitable)
        if (cmp != 0) return cmp
        return sum().compareTo(other.sum())
    }

    private fun sum(): Int {
        return equalClasses * 2 + castedClasses - convertedClasses * 2
    }
}
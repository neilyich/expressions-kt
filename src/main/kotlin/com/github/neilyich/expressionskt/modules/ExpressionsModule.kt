package com.github.neilyich.expressionskt.modules

import com.github.neilyich.expressionskt.evaluator.Evaluator
import com.github.neilyich.expressionskt.evaluator.typeconverter.TypeConverter
import com.github.neilyich.expressionskt.token.Operator

interface ExpressionsModule {
    fun operatorsEvaluators(): Map<Operator, List<Evaluator<*>>>
    fun typeConverters(): List<TypeConverter<*, *>>
}
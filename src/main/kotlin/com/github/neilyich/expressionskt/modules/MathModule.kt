package com.github.neilyich.expressionskt.modules

import com.github.neilyich.expressionskt.evaluator.Evaluator
import com.github.neilyich.expressionskt.evaluator.impl.functions.numbers.*
import com.github.neilyich.expressionskt.evaluator.impl.operators.numbers.*
import com.github.neilyich.expressionskt.evaluator.typeconverter.NumberToBigDecimalConverter
import com.github.neilyich.expressionskt.evaluator.typeconverter.NumberToBigIntegerConverter
import com.github.neilyich.expressionskt.evaluator.typeconverter.TypeConverter
import com.github.neilyich.expressionskt.token.Operator
import com.github.neilyich.expressionskt.token.operator.impl.functions.*
import com.github.neilyich.expressionskt.token.operator.impl.operators.*

class MathModule: ExpressionsModule {
    override fun operatorsEvaluators(): Map<Operator, List<Evaluator<*>>> {
        return mapOf(
            Plus to PlusEvaluator,
            Minus to MinusEvaluator,
            Mult to MultEvaluator,
            Div to DivEvaluator,
            Pow to PowEvaluator,
            UnaryMinus to UnaryMinusEvaluator,
            Factorial to FactorialEvaluator,
            Min to MinEvaluator,
            Max to MaxEvaluator,
            Cos to CosEvaluator,
            Sin to SinEvaluator,
            Tan to TanEvaluator,
        ).mapValues { listOf(it.value) }
    }

    override fun typeConverters(): List<TypeConverter<*, *>> {
        return listOf(NumberToBigIntegerConverter, NumberToBigDecimalConverter)
    }
}
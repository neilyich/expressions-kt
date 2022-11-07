package com.github.neilyich.expressionskt.modules

import com.github.neilyich.expressionskt.evaluator.Evaluator
import com.github.neilyich.expressionskt.evaluator.impl.functions.logical.IfElseEvaluator
import com.github.neilyich.expressionskt.evaluator.impl.operators.logical.*
import com.github.neilyich.expressionskt.evaluator.typeconverter.TypeConverter
import com.github.neilyich.expressionskt.token.Operator
import com.github.neilyich.expressionskt.token.operator.impl.functions.logical.IfElse
import com.github.neilyich.expressionskt.token.operator.impl.operators.logical.*

class LogicalModule : ExpressionsModule {
    override fun operatorsEvaluators(): Map<Operator, List<Evaluator<*>>> {
        return mapOf(
            And to listOf(AndEvaluator),
            Equal to listOf(EqualComparableEvaluator, EqualAnyEvaluator),
            Greater to listOf(GreaterEvaluator),
            GreaterOrEqual to listOf(GreaterOrEqualEvaluator),
            Less to listOf(LessEvaluator),
            LessOrEqual to listOf(LessOrEqualEvaluator),
            Not to listOf(NotEvaluator),
            Or to listOf(OrEvaluator),
            IfElse to listOf(IfElseEvaluator)
        )
    }

    override fun typeConverters(): List<TypeConverter<*, *>> = listOf()
}
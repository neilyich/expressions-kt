package com.github.neilyich.expressionskt.modules

import com.github.neilyich.expressionskt.evaluator.Evaluator
import com.github.neilyich.expressionskt.evaluator.typeconverter.TypeConverter
import com.github.neilyich.expressionskt.token.CloseBracket
import com.github.neilyich.expressionskt.token.Comma
import com.github.neilyich.expressionskt.token.OpenBracket
import com.github.neilyich.expressionskt.token.Operator
import com.github.neilyich.expressionskt.token.operator.evaluators.CommaEvaluator
import com.github.neilyich.expressionskt.token.operator.evaluators.IdentityEvaluator

class SpecialSymbolsModule: ExpressionsModule {
    override fun operatorsEvaluators(): Map<Operator, List<Evaluator<*>>> {
        return mapOf(
            Comma to CommaEvaluator,
            OpenBracket to IdentityEvaluator,
            CloseBracket to IdentityEvaluator
        ).mapValues { listOf(it.value) }
    }

    override fun typeConverters(): List<TypeConverter<*, *>> = listOf()
}
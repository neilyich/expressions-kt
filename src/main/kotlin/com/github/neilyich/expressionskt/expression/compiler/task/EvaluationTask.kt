package com.github.neilyich.expressionskt.expression.compiler.task

import com.github.neilyich.expressionskt.evaluator.Evaluator
import com.github.neilyich.expressionskt.evaluator.typeconverter.TypeConverter

data class EvaluationTask(
    val operandsCount: Int,
    val evaluator: Evaluator<Any>,
    val typeConverters: List<TypeConverter<Any, *>>
): CompiledTask
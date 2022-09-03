package com.github.neilyich.expressionskt.expression.evaluator

import com.github.neilyich.expressionskt.evaluator.args.OperandsHolder
import com.github.neilyich.expressionskt.evaluator.evaluationsuitability.EvaluationSuitabilityProvider
import com.github.neilyich.expressionskt.expression.compiler.CompiledExpression
import com.github.neilyich.expressionskt.expression.compiler.task.AddOperandToStack
import com.github.neilyich.expressionskt.expression.compiler.task.AddOperatorToStack
import com.github.neilyich.expressionskt.expression.compiler.task.EvaluationTask
import com.github.neilyich.expressionskt.token.Operand
import com.github.neilyich.expressionskt.token.Operator

class CompiledExpressionEvaluator(
    private val suitabilityProvider: EvaluationSuitabilityProvider
) : ExpressionEvaluator<CompiledExpression> {
    override fun <R : Any> evaluate(expression: CompiledExpression, resultClass: Class<R>): R {
        val operators = ArrayDeque<Operator>()
        val operands = ArrayDeque<Operand<*>>()
        val tokens = expression.content()
        var i = 0
        for (task in expression.tasks()) {
            when (task) {
                is AddOperatorToStack -> operators.addFirst(tokens[i++] as Operator)
                is AddOperandToStack -> operands.addFirst(tokens[i++] as Operand<*>)
                is EvaluationTask -> {
                    processEvaluationTask(operators, operands, task)
                }
            }
        }
        if (operands.size != 1) {
            throw RuntimeException("Unexpected operands")
        }
        val result =  operands.removeFirst().value()!!
        val resultSuitability = suitabilityProvider.forClasses(resultClass, result.javaClass)
        if (resultSuitability.isSuitable) {
            return resultSuitability.typeConverters[0].convert(result) as R
        }
        throw RuntimeException("Unable to cast actual result ${result.javaClass} to expected $resultClass")
    }

    private fun processEvaluationTask(operators: ArrayDeque<Operator>, operands: ArrayDeque<Operand<*>>, task: EvaluationTask) {
        val operandsHolder = OperandsHolder.forOperands(operands.subList(0, task.operandsCount).reversed())
        val result = task.evaluator.evaluate(OperandsHolder.withTypeConverters(operandsHolder, task.typeConverters), suitabilityProvider)
        for (i in 0 until task.operandsCount) {
            operands.removeFirst()
        }
        operands.addFirst(result)
        operators.removeFirst()
    }
}
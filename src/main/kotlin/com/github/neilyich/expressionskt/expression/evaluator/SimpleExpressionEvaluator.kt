package com.github.neilyich.expressionskt.expression.evaluator

import com.github.neilyich.expressionskt.evaluator.Evaluator
import com.github.neilyich.expressionskt.evaluator.args.OperandsHolder
import com.github.neilyich.expressionskt.evaluator.args.TypeConvertingOperandsHolder
import com.github.neilyich.expressionskt.evaluator.evaluationsuitability.EvaluationSuitabilityProvider
import com.github.neilyich.expressionskt.evaluator.impl.decimal
import com.github.neilyich.expressionskt.evaluator.impl.integer
import com.github.neilyich.expressionskt.expression.Expression
import com.github.neilyich.expressionskt.expression.compiler.CompiledExpression
import com.github.neilyich.expressionskt.token.*
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.collections.ArrayDeque

class SimpleExpressionEvaluator(
    private val operatorsEvaluators: Map<Operator, List<Evaluator<*>>>,
    private val suitabilityProvider: EvaluationSuitabilityProvider
) : ExpressionEvaluator<Expression> {

    override fun <R : Any> evaluate(expression: Expression, resultClass: Class<R>): R {
        if (expression is CompiledExpression) return CompiledExpressionEvaluator(suitabilityProvider).evaluate(expression, resultClass)
        val operators = ArrayDeque<OperatorPriority>()
        val operands = ArrayDeque<Operand<*>>()
        val tokens = expression.content()
        var i = 0
        var bracketsLevel = 0
        while (i < tokens.size) {
            when (val currentToken = tokens[i]) {
                is Operand<*> -> operands.addFirst(currentToken)
                is Operator -> {
                    when (currentToken) {
                        is FunctionCall, OpenBracket -> bracketsLevel++
                        else -> {}
                    }
                    processOperator(OperatorPriority(currentToken, bracketsLevel), operands, operators)
                    when (currentToken) {
                        is CloseBracket -> bracketsLevel--
                        else -> {}
                    }
                }
            }
            i++
        }
        while (operators.isNotEmpty()) {
            evaluateOperator(operators.removeFirst().operator, operands)
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

    private fun processOperator(operatorPriority: OperatorPriority, operands: ArrayDeque<Operand<*>>, operators: ArrayDeque<OperatorPriority>) {
        while (operators.isNotEmpty() && operators.first() >= operatorPriority) {
            evaluateOperator(operators.removeFirst().operator, operands)
        }
        operators.addFirst(operatorPriority)
    }

    private fun evaluateOperator(operator: Operator, operands: ArrayDeque<Operand<*>>) {
        val operandsHolder = OperandsHolder.forOperands(operands.subList(0, operator.operandsCount).reversed())
        val (evaluator, suitability) = operatorsEvaluators[operator]?.let {
            it.map { ev ->
                ev to ev.suitabilityFor(operandsHolder, suitabilityProvider)
            }.filter { ev ->
                ev.second.isSuitable
            }.maxByOrNull { ev ->
                ev.first.suitabilityFor(operandsHolder, suitabilityProvider)
            }
        } ?: throw RuntimeException("Unable to evaluate expression for operator: $operator, operands: $operandsHolder, ${operandsHolder.operands().map { it.valueClass() }}")
        //println("$operator: $operandsHolder, ${evaluator.javaClass.simpleName}, ${suitability.typeConverters.map { it.javaClass.simpleName }}")
        val result = evaluator.evaluate(OperandsHolder.withTypeConverters(operandsHolder, suitability.typeConverters), suitabilityProvider)
        for (i in 0 until operator.operandsCount) {
            operands.removeFirst()
        }
        operands.addFirst(result)
    }
}
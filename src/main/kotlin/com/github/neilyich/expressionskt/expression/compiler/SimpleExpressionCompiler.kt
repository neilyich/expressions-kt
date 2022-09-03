package com.github.neilyich.expressionskt.expression.compiler

import com.github.neilyich.expressionskt.evaluator.Evaluator
import com.github.neilyich.expressionskt.evaluator.args.OperandsHolder
import com.github.neilyich.expressionskt.evaluator.evaluationsuitability.EvaluationSuitabilityProvider
import com.github.neilyich.expressionskt.expression.Expression
import com.github.neilyich.expressionskt.expression.compiler.task.AddOperandToStack
import com.github.neilyich.expressionskt.expression.compiler.task.AddOperatorToStack
import com.github.neilyich.expressionskt.expression.compiler.task.CompiledTask
import com.github.neilyich.expressionskt.expression.compiler.task.EvaluationTask
import com.github.neilyich.expressionskt.expression.evaluator.OperatorPriority
import com.github.neilyich.expressionskt.token.*
import com.github.neilyich.expressionskt.token.operator.evaluators.CommaEvaluator
import com.github.neilyich.expressionskt.token.operator.evaluators.IdentityEvaluator

class SimpleExpressionCompiler(
    private val operatorsEvaluators: Map<Operator, List<Evaluator<*>>>,
    private val suitabilityProvider: EvaluationSuitabilityProvider
) : ExpressionCompiler {
    override fun compile(expression: Expression): CompiledExpression {
        if (expression is CompiledExpression) return expression
        val operators = ArrayDeque<OperatorPriority>()
        val operands = ArrayDeque<Operand<*>>()
        val tokens = expression.content()
        val tasks = mutableListOf<CompiledTask>()
        var i = 0
        var bracketsLevel = 0
        while (i < tokens.size) {
            when (val currentToken = tokens[i]) {
                is Operand<*> -> {
                    operands.addFirst(currentToken)
                    tasks.add(AddOperandToStack)
                }
                is Operator -> {
                    when (currentToken) {
                        is FunctionCall, OpenBracket -> bracketsLevel++
                        else -> {}
                    }
                    processOperator(OperatorPriority(currentToken, bracketsLevel), operands, operators, tasks)
                    when (currentToken) {
                        is CloseBracket -> bracketsLevel--
                        else -> {}
                    }
                }
            }
            i++
        }
        while (operators.isNotEmpty()) {
            evaluateOperator(operators.removeFirst().operator, operands, tasks)
        }
        return CompiledExpression(tokens, tasks)
    }

    private fun processOperator(operatorPriority: OperatorPriority, operands: ArrayDeque<Operand<*>>, operators: ArrayDeque<OperatorPriority>, tasks: MutableList<CompiledTask>) {
        while (operators.isNotEmpty() && operators.first() >= operatorPriority) {
            evaluateOperator(operators.removeFirst().operator, operands, tasks)
        }
        operators.addFirst(operatorPriority)
        tasks.add(AddOperatorToStack)
    }

    private fun evaluateOperator(operator: Operator, operands: ArrayDeque<Operand<*>>, tasks: MutableList<CompiledTask>) {
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
        println("$operator: $operandsHolder, ${evaluator.javaClass.simpleName}, ${suitability.typeConverters.map { it.javaClass.simpleName }}")
        val result = when (evaluator) {
            is IdentityEvaluator -> {
                val f = operands.first()
                NamedVariable("compiledVar", { null }, { f.valueClass() })
            }
            is CommaEvaluator -> {
                evaluator.evaluate(operandsHolder, suitabilityProvider)
            }
            else -> {
                NamedVariable("compiledVar", { null }, { evaluator.resultClass() })
            }
        }
        for (i in 0 until operator.operandsCount) {
            operands.removeFirst()
        }
        operands.addFirst(result)
        tasks.add(EvaluationTask(operator.operandsCount, evaluator as Evaluator<Any>, suitability.typeConverters))
    }

}
package com.github.neilyich.expressionskt

import com.github.neilyich.expressionskt.evaluator.Evaluator
import com.github.neilyich.expressionskt.evaluator.evaluationsuitability.EvaluationSuitabilityProvider
import com.github.neilyich.expressionskt.evaluator.typeconverter.TypeConverter
import com.github.neilyich.expressionskt.expression.Expression
import com.github.neilyich.expressionskt.expression.compiler.ExpressionCompiler
import com.github.neilyich.expressionskt.expression.compiler.SimpleExpressionCompiler
import com.github.neilyich.expressionskt.expression.evaluator.ExpressionEvaluator
import com.github.neilyich.expressionskt.expression.evaluator.SimpleExpressionEvaluator
import com.github.neilyich.expressionskt.parser.ExpressionParser
import com.github.neilyich.expressionskt.parser.SimpleExpressionParser
import com.github.neilyich.expressionskt.token.Constant
import com.github.neilyich.expressionskt.token.Operand
import com.github.neilyich.expressionskt.token.Operator
import com.github.neilyich.expressionskt.token.NamedVariable
import java.util.LinkedList

class ExpressionsContextImpl(
    private val suitabilityProvider: EvaluationSuitabilityProvider
) : ExpressionsContext {

    private val operatorEvaluators = mutableMapOf<Operator, MutableList<Evaluator<*>>>()

    private val variables = mutableMapOf<String, Operand<*>>()

    override fun register(operator: Operator, evaluator: Evaluator<*>) {
        val evaluators = operatorEvaluators.getOrPut(operator) { LinkedList() }
        evaluators.add(evaluator)
    }

    override fun <From : Any, To : Any> register(typeConverter: TypeConverter<From, To>) {
        suitabilityProvider.register(typeConverter)
    }

    override fun <V : Any> registerVariable(name: String, valueClass: Class<in V>) {
        variables[name] = NamedVariable(name, { null }, { valueClass })
    }

    override fun <V: Any> setVariable(name: String, value: V, valueClass: Class<in V>) {
        variables[name] = Constant(value, valueClass)
    }

    override fun <V : Any> setVariableAsExpression(name: String, expr: String, valueClass: Class<V>) {
        val expression = parse(expr)
        variables[name] = NamedVariable("expr_$name", { evaluate(expression, valueClass) }) { valueClass }
    }

    override fun createEvaluator(): ExpressionEvaluator<Expression> = SimpleExpressionEvaluator(operatorEvaluators, suitabilityProvider)

    override fun createParser(): ExpressionParser = SimpleExpressionParser(operatorEvaluators.keys, variables)

    override fun createCompiler(): ExpressionCompiler = SimpleExpressionCompiler(operatorEvaluators, suitabilityProvider)
}
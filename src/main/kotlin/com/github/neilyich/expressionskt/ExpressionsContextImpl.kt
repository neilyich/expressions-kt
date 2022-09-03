package com.github.neilyich.expressionskt

import com.github.neilyich.expressionskt.evaluator.Evaluator
import com.github.neilyich.expressionskt.evaluator.evaluationsuitability.EvaluationSuitabilityProvider
import com.github.neilyich.expressionskt.evaluator.typeconverter.TypeConverter
import com.github.neilyich.expressionskt.expression.Expression
import com.github.neilyich.expressionskt.expression.compiler.CompiledExpression
import com.github.neilyich.expressionskt.expression.compiler.ExpressionCompiler
import com.github.neilyich.expressionskt.expression.compiler.SimpleExpressionCompiler
import com.github.neilyich.expressionskt.expression.evaluator.CompiledExpressionEvaluator
import com.github.neilyich.expressionskt.expression.evaluator.DelegatingExpressionEvaluator
import com.github.neilyich.expressionskt.expression.evaluator.ExpressionEvaluator
import com.github.neilyich.expressionskt.expression.evaluator.SimpleExpressionEvaluator
import com.github.neilyich.expressionskt.expression.evaluator.provider.ComparingEvaluatorProvider
import com.github.neilyich.expressionskt.expression.evaluator.provider.EvaluatorProvider
import com.github.neilyich.expressionskt.parser.ExpressionParser
import com.github.neilyich.expressionskt.parser.SimpleExpressionParser
import com.github.neilyich.expressionskt.token.*
import java.util.LinkedList

class ExpressionsContextImpl(
    private val suitabilityProvider: EvaluationSuitabilityProvider,
    private val evaluatorProvider: EvaluatorProvider
) : ExpressionsContext {

    private val operators = mutableSetOf<Operator>()

    private val variables = mutableMapOf<String, Operand<*>>()

    private val compiler = SimpleExpressionCompiler(evaluatorProvider, suitabilityProvider)

    private val parser = SimpleExpressionParser(operators, variables)

    private val expressionEvaluator: ExpressionEvaluator<Expression> = SimpleExpressionEvaluator(evaluatorProvider, suitabilityProvider)

    private val compiledExpressionEvaluator: ExpressionEvaluator<CompiledExpression> = CompiledExpressionEvaluator(suitabilityProvider)

    private val evaluator = DelegatingExpressionEvaluator(expressionEvaluator, compiledExpressionEvaluator)

    override fun register(operator: Operator, evaluator: Evaluator<*>) {
        operators.add(operator)
        evaluatorProvider.register(operator, evaluator)
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

    override fun <V : Any> setVariableAsExpression(name: String, expression: Expression, valueClass: Class<V>) {
        variables[name] = Variable({ evaluate(expression, valueClass) }) { valueClass }
    }

    override fun <V : Any> setVariableAsExpression(name: String, expr: String, valueClass: Class<V>) {
        setVariableAsExpression(name, compile(expr), valueClass)
    }

    override fun evaluator(): ExpressionEvaluator<Expression> = evaluator

    override fun parser(): ExpressionParser = parser

    override fun createCompiler(): ExpressionCompiler = compiler
}
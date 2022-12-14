package com.github.neilyich.expressionskt

import com.github.neilyich.expressionskt.evaluator.Evaluator
import com.github.neilyich.expressionskt.evaluator.evaluationsuitability.TypeConvertingEvaluationSuitabilityProvider
import com.github.neilyich.expressionskt.evaluator.impl.functions.numbers.*
import com.github.neilyich.expressionskt.evaluator.impl.operators.numbers.*
import com.github.neilyich.expressionskt.evaluator.typeconverter.NumberToBigDecimalConverter
import com.github.neilyich.expressionskt.evaluator.typeconverter.NumberToBigIntegerConverter
import com.github.neilyich.expressionskt.evaluator.typeconverter.TypeConverter
import com.github.neilyich.expressionskt.expression.Expression
import com.github.neilyich.expressionskt.expression.compiler.CompiledExpression
import com.github.neilyich.expressionskt.expression.compiler.ExpressionCompiler
import com.github.neilyich.expressionskt.expression.evaluator.ExpressionEvaluator
import com.github.neilyich.expressionskt.expression.evaluator.provider.ComparingEvaluatorProvider
import com.github.neilyich.expressionskt.modules.ExpressionsModule
import com.github.neilyich.expressionskt.modules.LogicalModule
import com.github.neilyich.expressionskt.modules.MathModule
import com.github.neilyich.expressionskt.modules.SpecialSymbolsModule
import com.github.neilyich.expressionskt.parser.ExpressionParser
import com.github.neilyich.expressionskt.token.*
import com.github.neilyich.expressionskt.token.operator.evaluators.CommaEvaluator
import com.github.neilyich.expressionskt.token.operator.evaluators.IdentityEvaluator
import com.github.neilyich.expressionskt.token.operator.impl.functions.*
import com.github.neilyich.expressionskt.token.operator.impl.operators.*

interface ExpressionsContext {

    fun register(module: ExpressionsModule) {
        module.operatorsEvaluators().forEach { entry ->
            entry.value.forEach {
                register(entry.key, it)
            }
        }
        module.typeConverters().forEach {
            register(it as TypeConverter<Any, Any>)
        }
    }

    fun variables(): Map<String, *>

    fun register(operator: Operator, evaluator: Evaluator<*>)

    fun <From: Any, To: Any> register(typeConverter: TypeConverter<From, To>)

    fun <V: Any> registerVariable(name: String, valueClass: Class<in V>)

    fun <V: Any> setVariable(name: String, value: V, valueClass: Class<in V>)

    fun <V: Any> setVariable(name: String, value: V) = setVariable(name, value, value.javaClass)

    fun <V: Any> setVariableAsExpression(name: String, expression: Expression, valueClass: Class<V>)

    fun <V: Any> setVariableAsExpression(name: String, expr: String, valueClass: Class<V>)

    fun evaluator(): ExpressionEvaluator<Expression>

    fun <R: Any> evaluate(expression: Expression, resultClass: Class<R>): R = evaluator().evaluate(expression, resultClass)

    fun evaluate(expression: Expression) = evaluate(expression, Any::class.java)

    fun parser(): ExpressionParser

    fun parse(expr: String): Expression = parser().parse(expr)

    fun <R: Any> evaluate(expr: String, resultClass: Class<R>): R = evaluate(parse(expr), resultClass)

    fun evaluate(expr: String) = evaluate(expr, Any::class.java)

    fun compiler(): ExpressionCompiler

    fun compile(expr: String): CompiledExpression = compiler().compile(parse(expr))

    companion object {
        @JvmStatic
        fun createDefault(): ExpressionsContext {
            val context = ExpressionsContextImpl(TypeConvertingEvaluationSuitabilityProvider(), ComparingEvaluatorProvider())

            context.register(SpecialSymbolsModule())

            context.register(MathModule())
            context.register(LogicalModule())

            return context
        }
    }
}
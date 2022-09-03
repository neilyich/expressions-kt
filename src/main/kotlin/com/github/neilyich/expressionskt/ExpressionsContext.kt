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
import com.github.neilyich.expressionskt.parser.ExpressionParser
import com.github.neilyich.expressionskt.token.*
import com.github.neilyich.expressionskt.token.operator.evaluators.CommaEvaluator
import com.github.neilyich.expressionskt.token.operator.evaluators.IdentityEvaluator
import com.github.neilyich.expressionskt.token.operator.impl.functions.*
import com.github.neilyich.expressionskt.token.operator.impl.operators.*

interface ExpressionsContext {

    fun register(operator: Operator, evaluator: Evaluator<*>)

    fun <From: Any, To: Any> register(typeConverter: TypeConverter<From, To>)

    fun <V: Any> registerVariable(name: String, valueClass: Class<in V>)

    fun <V: Any> setVariable(name: String, value: V, valueClass: Class<in V>)

    fun <V: Any> setVariable(name: String, value: V) = setVariable(name, value, value.javaClass)

    fun <V: Any> setVariableAsExpression(name: String, expr: String, valueClass: Class<V>)

    fun createEvaluator(): ExpressionEvaluator<Expression>

    fun <R: Any> evaluate(expression: Expression, resultClass: Class<R>): R = createEvaluator().evaluate(expression, resultClass)

    fun evaluate(expression: Expression) = evaluate(expression, Any::class.java)

    fun createParser(): ExpressionParser

    fun parse(expr: String): Expression = createParser().parse(expr)

    fun <R: Any> evaluate(expr: String, resultClass: Class<R>): R = evaluate(parse(expr), resultClass)

    fun evaluate(expr: String) = evaluate(expr, Any::class.java)

    fun createCompiler(): ExpressionCompiler

    fun compile(expr: String): CompiledExpression = createCompiler().compile(parse(expr))

    companion object {
        @JvmStatic
        fun createDefault(): ExpressionsContext {
            val context = ExpressionsContextImpl(TypeConvertingEvaluationSuitabilityProvider())

            context.register(Comma, CommaEvaluator)
            context.register(OpenBracket, IdentityEvaluator)
            context.register(CloseBracket, IdentityEvaluator)

            context.register(Plus, PlusEvaluator)
            context.register(Minus, MinusEvaluator)
            context.register(Mult, MultEvaluator)
            context.register(Div, DivEvaluator)
            context.register(Pow, PowEvaluator)
            context.register(UnaryMinus, UnaryMinusEvaluator)
            context.register(Factorial, FactorialEvaluator)

            context.register(Min, MinEvaluator)
            context.register(Max, MaxEvaluator)

            context.register(NumberToBigIntegerConverter)
            context.register(NumberToBigDecimalConverter)

            context.register(Cos, CosEvaluator)
            context.register(Sin, SinEvaluator)
            context.register(Tan, TanEvaluator)

            return context
        }
    }
}
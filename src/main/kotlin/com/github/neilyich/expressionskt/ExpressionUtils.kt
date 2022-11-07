package com.github.neilyich.expressionskt

import com.github.neilyich.expressionskt.evaluator.Evaluator
import com.github.neilyich.expressionskt.evaluator.typeconverter.TypeConverter
import com.github.neilyich.expressionskt.expression.Expression
import com.github.neilyich.expressionskt.expression.compiler.CompiledExpression
import com.github.neilyich.expressionskt.expression.compiler.ExpressionCompiler
import com.github.neilyich.expressionskt.expression.evaluator.ExpressionEvaluator
import com.github.neilyich.expressionskt.modules.ExpressionsModule
import com.github.neilyich.expressionskt.parser.ExpressionParser
import com.github.neilyich.expressionskt.token.Operator
import java.math.BigDecimal

class ExpressionUtils {
    companion object {
        private var context: ExpressionsContext = ExpressionsContext.createDefault()
        @JvmStatic
        fun setContext(c: ExpressionsContext) {
            context = c
        }

        @JvmStatic
        fun variables() = context.variables()

        @JvmStatic
        fun register(module: ExpressionsModule) {
            context.register(module)
        }

        @JvmStatic
        fun register(operator: Operator, evaluator: Evaluator<*>) {
            context.register(operator, evaluator)
        }

        @JvmStatic
        fun <From: Any, To: Any> register(typeConverter: TypeConverter<From, To>) {
            context.register(typeConverter)
        }

        @JvmStatic
        fun <V: Any> registerVariable(name: String, valueClass: Class<in V>) {
            context.registerVariable(name, valueClass)
        }

        @JvmStatic
        fun <V: Any> setVariable(name: String, value: V, valueClass: Class<in V>) {
            context.setVariable(name, value, valueClass)
        }

        @JvmStatic
        fun setNumberVariable(name: String, value: BigDecimal) {
            context.setVariable(name, value, BigDecimal::class.javaObjectType)
        }

        @JvmStatic
        fun <V: Any> setVariable(name: String, value: V) = setVariable(name, value, value.javaClass)

        @JvmStatic
        fun <V: Any> setVariableAsExpression(name: String, expression: Expression, valueClass: Class<V>) {
            context.setVariableAsExpression(name, expression, valueClass)
        }

        @JvmStatic
        fun <V: Any> setVariableAsExpression(name: String, expr: String, valueClass: Class<V>) {
            context.setVariableAsExpression(name, expr, valueClass)
        }

        @JvmStatic
        fun setVariableAsNumberExpression(name: String, expr: String) {
            context.setVariableAsExpression(name, expr, BigDecimal::class.javaObjectType)
        }

        @JvmStatic
        fun evaluator(): ExpressionEvaluator<Expression> {
            return context.evaluator()
        }

        @JvmStatic
        fun <R: Any> evaluate(expression: Expression, resultClass: Class<R>): R = evaluator().evaluate(expression, resultClass)

        @JvmStatic
        fun evaluate(expression: Expression) = evaluate(expression, Any::class.java)

        @JvmStatic
        fun parser(): ExpressionParser {
            return context.parser()
        }

        @JvmStatic
        fun parse(expr: String): Expression = parser().parse(expr)

        @JvmStatic
        fun <R: Any> evaluate(expr: String, resultClass: Class<R>): R = evaluate(parse(expr), resultClass)

        @JvmStatic
        fun evaluate(expr: String) = evaluate(expr, Any::class.java)

        @JvmStatic
        fun compiler(): ExpressionCompiler {
            return context.compiler()
        }

        @JvmStatic
        fun compile(expr: String): CompiledExpression = compiler().compile(parse(expr))

    }
}
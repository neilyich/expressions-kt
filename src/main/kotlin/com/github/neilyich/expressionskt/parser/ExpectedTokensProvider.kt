package com.github.neilyich.expressionskt.parser

import com.github.neilyich.expressionskt.token.*

object ExpectedTokensProvider {
    val initialExpectedTokens = listOf(OpenBracket.javaClass, FunctionCall::class.javaObjectType, UnaryPrefixOperator::class.javaObjectType, Operand::class.javaObjectType)
    fun after(token: ExpressionToken): List<Class<out ExpressionToken>> {
        return when (token) {
            is OpenBracket -> initialExpectedTokens
            is BinaryOperator -> listOf(Operand::class.javaObjectType, FunctionCall::class.javaObjectType, OpenBracket.javaClass)
            is UnaryPrefixOperator -> listOf(OpenBracket.javaClass, FunctionCall::class.javaObjectType, Operand::class.javaObjectType)
            is UnaryPostfixOperator -> listOf(CloseBracket::class.javaObjectType, BinaryOperator::class.javaObjectType, Comma.javaClass)
            is CloseBracket -> listOf(CloseBracket.javaClass, BinaryOperator::class.javaObjectType, Comma.javaClass, UnaryPostfixOperator::class.javaObjectType)
            is FunctionCall -> listOf(CloseBracket.javaClass, OpenBracket.javaClass, FunctionCall::class.javaObjectType, UnaryPrefixOperator::class.javaObjectType, Operand::class.javaObjectType)
            is Comma -> listOf(OpenBracket.javaClass, FunctionCall::class.javaObjectType, UnaryPrefixOperator::class.javaObjectType, Operand::class.javaObjectType)
            is NamedVariable<*> -> listOf(BinaryOperator::class.javaObjectType, UnaryPostfixOperator::class.javaObjectType, CloseBracket.javaClass, Comma.javaClass, OpenBracket.javaClass)
            is Operand<*> -> listOf(BinaryOperator::class.javaObjectType, UnaryPostfixOperator::class.javaObjectType, CloseBracket.javaClass, Comma.javaClass)
        }
    }
}
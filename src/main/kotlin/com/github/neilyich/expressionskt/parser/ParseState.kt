package com.github.neilyich.expressionskt.parser

import com.github.neilyich.expressionskt.token.*

class ParseState {
    companion object {
        @JvmStatic
        private val initialStateTokens = listOf<Class<*>>(OpenBracket.javaClass, FunctionCall::class.javaObjectType, UnaryPrefixOperator::class.javaObjectType, Operand::class.javaObjectType)

        @JvmStatic
        fun initial() = ParseState()
    }

    var expectedTokens = initialStateTokens

    private val expectedTokensStack = ArrayDeque<List<Class<*>>>()

    fun submit(token: ExpressionToken) {
        if (!isTokenExpected(token)) {
            throw ParseException("Expected one of the following tokens: $expectedTokens, got: ${token.javaClass}")
        }
        expectedTokensStack.addFirst(expectedTokens)
        expectedTokens = when (token) {
            is OpenBracket -> initialStateTokens
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
    
    fun isTokenExpected(token: ExpressionToken): Boolean {
        return expectedTokens.stream().anyMatch { it.isAssignableFrom(token.javaClass) }
    }

    fun stepBack() {
        expectedTokens = expectedTokensStack.removeFirst()
    }
}
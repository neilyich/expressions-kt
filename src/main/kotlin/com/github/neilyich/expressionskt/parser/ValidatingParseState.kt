package com.github.neilyich.expressionskt.parser

import com.github.neilyich.expressionskt.token.*

class ValidatingParseState {
    companion object {
        @JvmStatic
        private val initialStateTokens = listOf<Class<*>>(OpenBracket.javaClass, FunctionCall::class.javaObjectType, UnaryPrefixOperator::class.javaObjectType, Operand::class.javaObjectType)

        @JvmStatic
        fun initial() = ValidatingParseState()
    }

    var expectedTokens = initialStateTokens
        private set

    private val expectedTokensStack = ArrayDeque<List<Class<*>>>()

    private var bracketsBalance = 0
    private val insideFunctionFlagStack = ArrayDeque<Boolean>()

    fun submitAndValidate(token: ExpressionToken) {
        if (!isTokenExpected(token)) {
            throw ParseException("Expected one of the following tokens: $expectedTokens, got: ${token.javaClass}")
        }
        expectedTokensStack.addFirst(expectedTokens)
        expectedTokens = ExpectedTokensProvider.after(token)
        updateAndValidateBracketsBalance(token)
        validateComma(token)
    }

    private fun validateComma(token: ExpressionToken) {
        when (token) {
            is FunctionCall -> insideFunctionFlagStack.addFirst(true)
            is OpenBracket -> insideFunctionFlagStack.addFirst(false)
            is CloseBracket -> insideFunctionFlagStack.removeFirst()
            is Comma -> {
                if (!insideFunctionFlagStack.first()) throw ParseException("Unexpected comma not inside function call")
            }
            else -> {}
        }
    }

    private fun updateAndValidateBracketsBalance(token: ExpressionToken) {
        when (token) {
            is FunctionCall, OpenBracket -> bracketsBalance++
            is CloseBracket -> bracketsBalance--
            else -> {}
        }
        if (bracketsBalance < 0) {
            throw ParseException("Unexpected closing bracket before opening bracket")
        }
    }

    fun reset() {
        expectedTokens = initialStateTokens
    }
    
    fun isTokenExpected(token: ExpressionToken): Boolean {
        return expectedTokens.stream().anyMatch { it.isAssignableFrom(token.javaClass) }
    }

    fun validateTotal() {
        if (bracketsBalance != 0) {
            throw ParseException("Wrong amount of closing and opening brackets")
        }
    }
}
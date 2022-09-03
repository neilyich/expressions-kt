package com.github.neilyich.expressionskt.parser

import com.github.neilyich.expressionskt.expression.Expression
import com.github.neilyich.expressionskt.expression.SimpleExpression
import com.github.neilyich.expressionskt.token.*
import java.math.BigDecimal
import java.math.BigInteger

class SimpleExpressionParser(private val operators: Set<Operator>, private val variablesContainer: Map<String, Operand<*>>) : ExpressionParser {

    override fun parse(expr_: String): Expression {
        val expr = expr_.replace(Regex("\\s+"), "")
        val state = ParseState.initial()
        val tokens = ArrayList<ExpressionToken>()
        var currentIndex = 0
        while (currentIndex < expr.length) {
            val nexTokenInfo = nextToken(expr, currentIndex, state)
            var nextToken = nexTokenInfo.second
                ?: throw ParseException("Could not parse expression: ${expr.substring(currentIndex, nexTokenInfo.first)}, expected tokens were: ${state.expectedTokens.map { it.simpleName }}")
            if (tokens.isNotEmpty()) {
                val lastToken = tokens.last()
                if (lastToken is FunctionCall) {
                    tokens.add(OperandsEnumeration.empty())
                    if (nextToken !is CloseBracket) {
                        tokens.add(Comma)
                    }
                } else if (lastToken is NamedVariable<*> && nextToken is OpenBracket) {
                    state.stepBack()
                    tokens.removeLast()
                    nextToken = FunctionCall(lastToken.name)
                }
            }
            currentIndex = nexTokenInfo.first
            state.submit(nextToken)
            tokens.add(nextToken)
        }
        return SimpleExpression(tokens)
    }

    private fun nextToken(expr: String, startIndex: Int, state: ParseState): Pair<Int, ExpressionToken?> {
        var token: ExpressionToken? = null
        var endIndex = startIndex + 1
        if (expr[startIndex].isJavaIdentifierStart()) {
            while (endIndex < expr.length && expr[endIndex].isJavaIdentifierPart()) { endIndex++ }
            val javaId = expr.substring(startIndex, endIndex)
            return endIndex to NamedVariable(javaId, { variablesContainer[javaId]?.value() }) { variablesContainer[javaId]?.valueClass() as Class<Any> }
        } else if (expr[startIndex].isDigit()) {
            while (endIndex < expr.length && expr[endIndex].isDigit()) { endIndex++ }
            if (endIndex < expr.length && expr[endIndex] == '.') {
                endIndex++
                while (endIndex < expr.length && expr[endIndex].isDigit()) { endIndex++ }
                val number = expr.substring(startIndex, endIndex)
                try {
                    return endIndex to Constant(BigDecimal(number))
                } catch (ignored: NumberFormatException) {}
            } else {
                try {
                    return endIndex to Constant(BigInteger(expr.substring(startIndex, endIndex)))
                } catch (ignored: NumberFormatException) {}
            }
        } else {
            var opStr = expr.substring(startIndex, endIndex)
            var candidates = operators.filter { it.str == opStr }.filter { state.isTokenExpected(it) }.toSet()
            while (endIndex < expr.length && candidates.size != 1 && (!expr[endIndex].isJavaIdentifierStart() && !expr[endIndex].isDigit())) {
                endIndex++
                opStr = expr.substring(startIndex, endIndex)
                candidates = operators.filter { it.str != opStr }.filter { state.expectedTokens.contains(it.javaClass) }.toSet()
            }
            if (candidates.size == 1) {
                token = candidates.first()
            }
        }
        return endIndex to token
    }
}
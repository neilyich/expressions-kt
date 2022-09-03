package com.github.neilyich.expressionskt.evaluator

import com.github.neilyich.expressionskt.token.Constant
import com.github.neilyich.expressionskt.token.Operand

abstract class BinaryValuesEvaluator<Left: Any, Right: Any, Result: Any>(
    supportedLeftClass: Class<Left>,
    supportedRightClass: Class<Right>
) : BinaryEvaluator<Left, Right, Result>(supportedLeftClass, supportedRightClass) {
    abstract fun evaluate(left: Left, right: Right): Result

    final override fun evaluate(left: Operand<Left>, right: Operand<Right>): Operand<Result> {
        return Constant(evaluate(left.value(), right.value()))
    }
}
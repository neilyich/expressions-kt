package com.github.neilyich.expressionskt.evaluator.args

import com.github.neilyich.expressionskt.token.OperandsEnumeration

class OperandsEnumerationHolder(operandsEnumeration: OperandsEnumeration) : SimpleOperandsHolder(operandsEnumeration.value()) {
}
package com.github.neilyich.expressionskt.evaluator.args

import com.github.neilyich.expressionskt.evaluator.typeconverter.TypeConverter
import com.github.neilyich.expressionskt.token.Operand
import com.github.neilyich.expressionskt.token.OperandsEnumeration

sealed interface OperandsHolder {

    @Throws(ClassCastException::class)
    fun <V: Any> get(i: Int, clazz: Class<V>): Operand<V>

    fun operandsCount(): Int

    fun getClass(i: Int): Class<*>

    fun classes(): List<Class<*>>

    fun operands(): List<Operand<*>>

    companion object {
        @JvmStatic
        fun forOperands(operands: List<Operand<*>>): OperandsHolder {
            if (operands.isEmpty()) return EmptyOperandsHolder
            if (operands.size == 1) {
                val firstOp =  operands[0]
                if (firstOp is OperandsEnumeration) {
                    return OperandsEnumerationHolder(firstOp)
                }
            }
            return SimpleOperandsHolder(operands)
        }

        @JvmStatic
        fun withTypeConverters(delegate: OperandsHolder, typeConverters: List<TypeConverter<Any, *>>): OperandsHolder {
            return TypeConvertingOperandsHolder(delegate, typeConverters)
        }
    }
}
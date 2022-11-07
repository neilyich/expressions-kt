package com.github.neilyich.expressionskt.evaluator.args

import com.github.neilyich.expressionskt.evaluator.typeconverter.CastingTypeConverter
import com.github.neilyich.expressionskt.evaluator.typeconverter.TypeConverter
import com.github.neilyich.expressionskt.token.Operand
import com.github.neilyich.expressionskt.token.NamedVariable

class TypeConvertingOperandsHolder(
    private val delegate: OperandsHolder,
    private val typeConverters: List<TypeConverter<Any, *>>
) : OperandsHolder {

    @Suppress("UNCHECKED_CAST")
    override fun <V: Any> get(i: Int, clazz: Class<V>): Operand<V> {
        return when (val typeConverter = typeConverters[i]) {
            is CastingTypeConverter -> delegate.get(i, clazz)
            else -> NamedVariable("", { delegate.operands()[i].value()?.let { typeConverter.convert(it) as V } }) { clazz }
        }
    }

    override fun operandsCount(): Int {
        return delegate.operandsCount()
    }

    override fun getClass(i: Int): Class<out Any> {
        return delegate.getClass(i)
    }

    override fun classes(): List<Class<*>> {
        return delegate.classes()
    }

    override fun operands(): List<Operand<*>> {
        return delegate.operands()
    }
}
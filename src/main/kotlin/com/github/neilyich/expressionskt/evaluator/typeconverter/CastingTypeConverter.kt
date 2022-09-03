package com.github.neilyich.expressionskt.evaluator.typeconverter

object CastingTypeConverter : TypeConverter<Any, Any> {
    override fun convert(value: Any): Any {
        return value
    }

    override fun fromClass(): Class<Any> {
        return Any::class.java
    }

    override fun toClass(): Class<Any> {
        return Any::class.java
    }
}
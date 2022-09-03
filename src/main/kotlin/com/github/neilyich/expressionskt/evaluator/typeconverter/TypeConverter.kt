package com.github.neilyich.expressionskt.evaluator.typeconverter

@FunctionalInterface
interface TypeConverter<From, To> {
    fun convert(value: From): To
    fun fromClass(): Class<From>
    fun toClass(): Class<To>
}
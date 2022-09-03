package com.github.neilyich.expressionskt

import com.github.neilyich.expressionskt.evaluator.BinaryValuesEvaluator
import com.github.neilyich.expressionskt.evaluator.FunctionValuesEvaluator
import com.github.neilyich.expressionskt.evaluator.impl.decimal
import com.github.neilyich.expressionskt.evaluator.impl.functions.numbers.NowEvaluator
import com.github.neilyich.expressionskt.evaluator.typeconverter.TypeConverter
import com.github.neilyich.expressionskt.token.FunctionCall
import com.github.neilyich.expressionskt.token.operator.impl.functions.Now
import com.github.neilyich.expressionskt.token.operator.impl.operators.Mult
import com.github.neilyich.expressionskt.token.operator.impl.operators.Plus
import java.math.BigDecimal
import java.math.BigInteger
import java.time.LocalDate

class Test {
    inline fun <reified B: Any> foo(a: Number, b: Number) {
        println(Long::class.java.isAssignableFrom(B::class.java))
    }
}


fun main() {
    val c = ExpressionsContext.createDefault()

//    c.register(object : TypeConverter<Number, BigInteger> {
//        override fun convert(value: Number): BigInteger {
//            return value.decimal().toBigInteger()
//        }
//
//        override fun fromClass(): Class<Number> {
//            return Number::class.java
//        }
//
//        override fun toClass(): Class<BigInteger> {
//            return BigInteger::class.java
//        }
//    })

    c.register(Mult, object : BinaryValuesEvaluator<String, BigInteger, String>(String::class.java, BigInteger::class.javaObjectType) {
        override fun evaluate(left: String, right: BigInteger): String {
            return left.repeat(right.intValueExact())
        }

        override fun isCommutative(): Boolean {
            return true
        }

        override fun resultClass(): Class<String> {
            return String::class.java
        }

    })
    c.register(Now, NowEvaluator)
    c.registerVariable("x", Double::class.javaObjectType)
    val e = c.compile("(1 + 2 * (3 + 4) + cos(x) * sin(x) - tan(x) / now()) * (1 + 2 * (3 + 4) + cos(x) * sin(x) - tan(x) / now())")
    println(e.content())
    //val rr = mutableListOf<Any>()
    val start = System.currentTimeMillis()
    for (degree in 0 until 1_000_000) {
        val x = degree.toDouble() / 180.0 * Math.PI
        c.setVariable("x", x)
        val r = c.evaluate(e)
        //rr.add(c.evaluate(e))
    }
    val end = System.currentTimeMillis()
    println("\n".repeat(5))
    println(end - start)
    //println(rr)
//    c.setVariable("x", "1")
//    c.setVariable("y", BigDecimal("4.0"), BigDecimal::class.java)
//    println(c.evaluate(e, BigDecimal::class.java))
//    c.setVariable("x", 5, Int::class.javaObjectType)
//    c.setVariableAsExpression("y", "x + 5", BigInteger::class.javaObjectType)
//    c.setVariable("x", 3, Int::class.javaObjectType)
//    var r = c.evaluate(e, BigDecimal::class.javaObjectType)
//    println(r.toString())
//    c.setVariableAsExpression("y", "x + 1", BigInteger::class.javaObjectType)
//    var r1 = c.evaluate(e, BigInteger::class.javaObjectType)
//    println(r1.toString())
}

//fun main1() {
//    val c1 = object : TypeConverter<Int, Double> {
//        override fun convert(value: Int): Double {
//            return value.toDouble()
//        }
//    }
//    val c2: TypeConverter<Any, Double> = c1 as TypeConverter<Any, Double>
//    c2.convert("")
//}
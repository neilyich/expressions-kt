package com.github.neilyich.expressionskt

import com.github.neilyich.expressionskt.ExpressionUtils.Companion.evaluate
import com.github.neilyich.expressionskt.ExpressionUtils.Companion.register
import com.github.neilyich.expressionskt.ExpressionUtils.Companion.registerVariable
import com.github.neilyich.expressionskt.ExpressionUtils.Companion.setNumberVariable
import com.github.neilyich.expressionskt.ExpressionUtils.Companion.setVariable
import com.github.neilyich.expressionskt.ExpressionUtils.Companion.setVariableAsExpression
import com.github.neilyich.expressionskt.ExpressionUtils.Companion.setVariableAsNumberExpression
import com.github.neilyich.expressionskt.ExpressionUtils.Companion.variables
import com.github.neilyich.expressionskt.evaluator.BinaryValuesEvaluator
import com.github.neilyich.expressionskt.evaluator.impl.functions.numbers.NowEvaluator
import com.github.neilyich.expressionskt.modules.MathModule
import com.github.neilyich.expressionskt.token.BinaryOperator
import com.github.neilyich.expressionskt.token.Comma
import com.github.neilyich.expressionskt.token.UnaryPostfixOperator
import com.github.neilyich.expressionskt.token.UnaryPrefixOperator
import com.github.neilyich.expressionskt.token.operator.impl.functions.Now
import com.github.neilyich.expressionskt.token.operator.impl.operators.Mult
import com.github.neilyich.expressionskt.utils.ClassUtils
import java.math.BigDecimal
import java.math.BigInteger

class Test {
    inline fun <reified B: Any> foo(a: Number, b: Number) {
        println(Long::class.java.isAssignableFrom(B::class.java))
    }
}

interface A
interface B
interface AB: A, B
interface AB1: AB
open class AImpl: A
open class BImpl: B
class ABImpl: AImpl(), AB1

fun serVar(name: String, value: String) {
    setNumberVariable(name, BigDecimal(value))
}

val calcedVars = mutableMapOf<String, Any?>()

fun calc(varName: String, expr: String) {
    setVariableAsNumberExpression(varName, expr)
    calcedVars[varName] = evaluate(varName)
    //println("$varName = ${evaluate(varName)}")
}

fun main() {
    serVar("kp", "0.3")
    serVar("ke", "2.0")
    serVar("delta", "0.32")
    serVar("cpri", "79.0")
    serVar("Kc", "12.0")
    serVar("Kb", "3.5")
    serVar("tkp", "4.0")
    serVar("p", "8.0")
    serVar("ky", "1.3")
    serVar("Tn", "540")
    serVar("Nmes", "27.0")
    serVar("qpr1", "350.0")
    serVar("qpr2", "580.0")
    serVar("qpr3", "600.0")
    serVar("qpr4", "500.0")
    serVar("qpr5", "450.0")

    calc("Kn", "Kc+Kb")
    calc("kg", "Kn/20")
    calc("b", "0.6 - 0.5 * kg")
    calc("Nosv", "(Tn/120)^(1/b)")
    calc("tosv", "(Nosv/Nmes)/12")
    calc("Tsum", "Tn/(1-b)*(Nosv^(1-b)-1)")
    calc("OE", "tosv*(1-(Nmes/60))")
    val N = listOf(1 to 81, 82 to 324, 325 to 747, 748 to 1151)
    for (i in 1 .. 4) {
        calc("Nstart$i", "${N[i - 1].first}")
        calc("Nend$i", "${N[i - 1].second}")
        calc("Tsum$i", "Tn/(1-b)*(Nend$i^(1-b) - Nstart$i^(1-b))")
        calc("Tavg$i", "Tsum$i / (Nend$i - Nstart$i + 1)")
    }
    calc("Tavg5", "120")
    calc("Tsum5", "Tavg5 * 450")
    calc("b1", "(Nosv - Nend4) / Nosv")
    calc("b2", "(Tsum - (Tsum1 + Tsum2 + Tsum3 + Tsum4)) / Tsum")
    val Nmax = listOf(81, 243, 423, 654, 720)
    for (i in 1 .. 3) {
        calc("Nmax$i", "${Nmax[i - 1]}")
        calc("difqpr0$i", "(qpr$i - Nmax$i) / qpr$i")
        calc("difqpr$i", "min(difqpr0$i, 2 * delta)")
        calc("difcpr$i", "difqpr$i / 2")
        calc("qprUpd$i", "qpr$i * (1 - difqpr$i)")
        calc("cpr$i", "cpri * (1 + difcpr$i)")

        calc("difselfcost${i}", "0")
        calc("Nyear$i", "qprUpd$i")
    }
    calc("Nyear1", "81")
    for (i in 4 .. 5) {
        calc("Nmax$i", "${Nmax[i - 1]}")
        calc("difqpr$i", "(Nmax$i - qpr$i) / Nmax$i")
        calc("cpr${i}1", "cpri")
        calc("difselfcost${i}1", "difqpr$i * kp")
        calc("Nyear${i}1", "qpr$i")

        calc("difgrowqpr$i", "(Nmax$i - qpr$i) / qpr$i")
        calc("diffallcpr$i", "difgrowqpr$i / 2")
        calc("cpr${i}2", "cpri * (1 - diffallcpr$i)")
        calc("difselfcost${i}2", "0")
        calc("Nyear${i}2", "Nmax$i")

        calc("Nyear$i", "Nyear${i}1")
    }


    serVar("M", "8.965")
    serVar("lh", "112")
    serVar("alpha", "0.15")
    serVar("beta", "0.30")
    serVar("kc", "1.50")
    serVar("kop", "0.25")
    serVar("kvp", "0.05")

    for (i in 1 .. 5) {
        calc("L$i", "Tavg$i * lh / 1000")
        calc("Savg$i", "( M + L$i*(1+kc+kop) + L$i*alpha + (L$i+L$i*alpha)*beta ) * (1+kvp)")
        val postfixes = mutableListOf<String>()
        if (i == 4 || i == 5) {
            postfixes.add("1")
            postfixes.add("2")
        } else {
            postfixes.add("")
        }
        for (p in postfixes) {
            calc("Syear$i$p", "Savg$i * Nyear$i$p * (1+difselfcost$i$p)")
            calc("Wyear$i$p", "cpr$i$p * Nyear$i$p")
            calc("Pyear$i$p", "Wyear$i$p - Syear$i$p")
        }
    }
    for (i in 1 .. 2) {
        calc("credit$i", "Kb * (1 + p/100)^$i")
    }

    val Nplan = mutableListOf(81, 243, 423, 500, 450)
    for (i in 1 until Nplan.size) {
        Nplan[i] = Nplan[i] + Nplan[i - 1]
    }
    println(Nplan)
    val NplanMN = listOf(1 to 81, 82 to 324, 325 to 747, 748 to 1247, 1248 to 1697)
    for (i in 1..5) {
        val Nm = NplanMN[i - 1].second
        val Nn = NplanMN[i - 1].first
        calc("Nplan$i", "Nyear$i")
        calc("Tsum${i}1", "Tsum$i")
        calc("Tplansum$i", "Tn/(1-b)*(($Nm)^(1-b) - ($Nn)^(1-b))")
        calc("Tplanavg$i", "Tplansum$i / ($Nm - $Nn + 1)")
        calc("Tplanavg${i}1", "Tplansum$i / (Nplan$i)")
        calc("Cavg$i", "Tplansum$i / 1935")

        calc("Fopcom$i", "lh / 1000 * Tplansum$i * (1 + alpha)")
        calc("Foptar$i", "lh / 1000 * Tplansum$i")
    }

//    variables().forEach {
//        println(it.key + " = " + it.value)
//    }
    //println(evaluate("1186/(27*12)"))
    calcedVars.forEach {
        println(it.key + " = " + it.value)
    }

    println(evaluate("6077-385"))

    if (true) return









    println(ClassUtils.nearestSuperClass(BigInteger::class.java, BigDecimal::class.java))
    ExpressionUtils.setVariable("true", true)
    ExpressionUtils.setVariable("false", false)
    ExpressionUtils.registerVariable("x", BigInteger::class.java)
    val res = ExpressionUtils.compile("true | true & false")
    //println(res.content())
    ExpressionUtils.registerVariable("y", BigInteger::class.java)
    ExpressionUtils.setVariableAsExpression("x", "y + 1", BigInteger::class.java)
    ExpressionUtils.setVariable("y", BigInteger.valueOf(1))
    println(ExpressionUtils.evaluate(res))
    ExpressionUtils.setVariable("y", BigInteger.valueOf(0))
    println(ExpressionUtils.evaluate(res))

}


fun main1() {
    val c = ExpressionsContext.createDefault()
    println(c.evaluate(""))

    if (true) return

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

        override fun resultClass(argClasses: List<Class<Any>>): Class<String> {
            return String::class.java
        }

    })
    c.register(Now, NowEvaluator)
    c.registerVariable("x", Double::class.javaObjectType)
    //c.setVariableAsExpression("y", "1 + 2 * (3 + 4) + cos(x) * sin(x) - tan(x) / now()", BigDecimal::class.java)
    val e = c.parse("1 + (2)) * 4")
    //val e1 = c.parse("(1 + 2 * (3 + 4) + cos(x) * sin(x) - tan(x) / now()) * (1 + 2 * (3 + 4) + cos(x) * sin(x) - tan(x) / now())")
    println(e.content())
    //val rr = mutableListOf<Any>()
    val start = System.currentTimeMillis()
    for (degree in 0 until 1_000_000) {
        val x = degree.toDouble() / 180.0 * Math.PI
        c.setVariable("x", x)
        val r = c.evaluate(e, BigDecimal::class.java)
//        val r1 = c.evaluate(e1, BigDecimal::class.java)
//        if ((r1 - r).abs() > BigDecimal("0.01")) {
//            throw RuntimeException("$r != $r1, x=$x")
//        }
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
package numbers

import kotlin.math.sqrt

private fun Int.isPrime(): Boolean {
    for (i in 2..this / 2) {
        if (this % i == 0)
            return false
    }
    return true
}

@Suppress("unused")
class Rational (
    val value: Long,
    val div: Long
) : Number {

    fun get() = value/div.toDouble()

    override fun toString() = "$value / $div = ${get()}"

    override fun toRational() = this

    override fun toInt(): Int = (value / div).toInt()
    override fun toReal(): Real = Real(value.toDouble(), div)

    override fun isInt() = value % div == 0L

    override fun isRational() = true

    //math
    override operator fun plus(other: Number): Number = if (!other.isRational()) this.toReal().plus(other) else add(other.toRational())
    operator fun plus(other: Rational): Rational = add(other)


    override operator fun minus(other: Number): Number = if (!other.isRational()) this.toReal().plus(other) else sub(other.toRational())
    operator fun minus(other: Rational): Rational = sub(other)

    override operator fun times(other: Number): Number = if (!other.isRational()) this.toReal().times(other) else mul(other.toRational())
    operator fun times(other: Rational): Rational = mul(other.toRational())

    override operator fun div(other: Number): Number = if (!other.isRational()) this.toReal().div(other) else divide(other.toRational())

    operator fun div(other: Rational): Rational = divide(other.toRational())

    override fun power(other: Number): Rational = power(other.toRational())

    override fun compareTo(other: Number): Int = other.toRational().let {
        when {
            this == other -> 0
            value * it.div > 0 -> 1
            else -> -1
        }
    }

    override fun equals(other: Any?): Boolean = when(other) {
        is Rational -> {
            (value == other.value && div == other.div) || (value == -1*other.value && div == -1*other.div)
        }
        else -> error("")
    }

    override fun sqrt(): Rational = Rational(sqrt(value.toDouble()).toLong(), sqrt(div.toDouble()).toLong())

    private fun biggest(value: Int, div: Int) = if (div > value) div else value

    internal fun simplify(): Rational {
        var newValue = value
        var newDiv = div
        var count = 0
        while (!(newValue == 1L || newDiv == 1L || count == 100)) {
            count++
            for (i in 2 until 100) {
                if (newValue % i == 0L && newDiv % i == 0L) {
                    newValue/=i
                    newDiv/=i
                    break
                }
            }
        }
        return when {
            newValue % newDiv == 0L -> Rational(newValue / newDiv, 1)
            newDiv % newValue == 0L -> Rational(1, newDiv / newValue)
            else -> Rational(newValue, newDiv)
        }
    }

    private fun power(other: Rational): Rational {
        var i = 0
        var number: Number = this
        while (i < other.get()) {
            number = (number*number)
            i++
        }
        return number as Rational
    }

    private fun add(other: Rational) =
        if (other.div == div)
            Rational(value+other.value, div).simplify()
        else
            Rational(value*other.div+other.value*div, (div*other.div)).simplify()

    private fun sub(other: Rational): Rational =
        if (other.div == div)
            Rational(value-other.value, div).simplify()
        else
            Rational(value*other.div-other.value*div, (div*other.div)).simplify()

    private fun mul(other: Rational): Rational = Rational(value*other.value, div*other.div).simplify()

    private fun divide(other: Rational): Rational = Rational(value*other.div, div*other.value).simplify()

}
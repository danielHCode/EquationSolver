package numbers

private fun Int.isPrime(): Boolean {
    for (i in 2..this / 2) {
        if (this % i == 0)
            return false
    }
    return true
}

interface Number {
    operator fun plus(other: Number): Number
    operator fun minus(other: Number): Number
    operator fun times(other: Number): Number
    operator fun div(other: Number): Number
    operator fun unaryMinus(): Number = this*(-1).toNumber()
    fun power(other: Number): Number

    operator fun compareTo(other: Number): Int

    fun sqrt(): Number

    fun isInt(): Boolean
    fun isPrime(): Boolean = isInt() && toInt().isPrime()
    fun isRational(): Boolean

    fun toInt(): Int
    fun toReal(): Real
    fun toRational(): Rational
}
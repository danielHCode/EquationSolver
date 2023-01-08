package numbers

fun Double.isInt() = this.toInt().toDouble() == this

class Real(
    val value: Double,
    val div: Long
) : Number {
    fun get() = value/div.toDouble()

    override fun toString() = "$value / $div = ${get()}"

    override fun toRational() = if (!value.isInt()) throw IllegalStateException() else Rational(value.toLong(), div)

    override fun toInt(): Int = if (!isInt()) throw IllegalStateException() else value.toInt() / div.toInt()

    override fun isInt() = isRational() && value % div.toDouble() == 0.0

    override fun isRational() = value.isInt()

    override fun toReal(): Real = this

    //math
    override operator fun plus(other: Number): Real = add(other.toReal())

    override operator fun minus(other: Number): Real = sub(other.toReal())

    override operator fun times(other: Number): Real = mul(other.toReal())

    override operator fun div(other: Number): Real = div(other.toReal())

    override fun power(other: Number): Real = power(other.toRational())
    override fun compareTo(other: Number): Int = this.toRational().compareTo(other.toRational())
    override fun sqrt(): Number = this.toRational().sqrt()

    private fun simplify(): Real = if (isRational()) this.toRational().simplify().toReal() else this

    private fun power(other: Real): Real {
        var i = 0
        var number: Number = this
        while (i < other.get()) {
            number = (number*number)
            i++
        }
        return number as Real
    }

    private fun add(other: Real) =
        if (other.div == div)
            Real(value+other.value, div).simplify()
        else
            Real(value*other.div+other.value*div, (div*other.div)).simplify()

    private fun sub(other: Real) =
        if (other.div == div)
            Real(value-other.value, div).simplify()
        else
            Real(value*other.div-other.value*div, (div*other.div)).simplify()

    private fun mul(other: Real) = Real(value*other.value, div*other.div).simplify()

    private fun div(other: Real) = other.refactorTillRational().let{
        Real(value*it.div, div*it.value).simplify()
    }

    fun refactorTillRational(): Rational = value.toString().filter { it.isDigit() }.let {
        return Rational(it.toLong(), "1${"0".times(it.length-2)}".toLong())
    }
}

private fun String.times(num: Int): String {
    var new = ""
    repeat(num) {new+=this}
    return new
}
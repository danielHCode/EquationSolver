package numbers

fun Int.toNumber(): Number = Rational(this.toLong(), 1)
fun Long.toNumber(): Number = Rational(this, 1)
fun Double.toNumber(): Number = Real(this, 1).refactorTillRational().simplify()
val Int.num get() = Rational(this.toLong(), 1)
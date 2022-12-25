package equation

import numbers.Rational
import numbers.num
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class QuadraticVarExpressionTest {
    @Test
    fun testSimple() {
        assertEquals(listOf(Rational(-1, 5), -(1).num), quadratic(5, 6, 1).solve())
        assertEquals(listOf(Rational(-1, 5), -(1).num), quadratic(6, 4, 3).simplify(quadratic(1, -2, 2)).solve())
    }
}
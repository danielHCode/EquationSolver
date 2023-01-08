package equation

import numbers.num

fun quadratic(a: Int, b: Int, c: Int) = QuadraticVarExpression(a.num, LinearVarExpression(b.num, ConstExpression(c.num)))
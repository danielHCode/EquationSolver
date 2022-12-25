package parse

import math.*
import numbers.toNumber

private val keywords = listOf(
    "+", "-", "*", "/", "²", "³", "(", ")"
)

private fun<Y, T> List<T>.collect(acc: Y, func: (acc: Y, value: T) -> Y): Y {
    var accMut = acc
    Regex
    for (t in this) {
        accMut=func(accMut, t)
    }
    return accMut
}

private fun String.trimRedundantSpaces(): String {
    var mut = this
    while (mut.contains("  ")) mut=mut.replace("  ", " ")
    return mut
}

private fun optimize(code: String) = keywords.collect(code) { acc, str -> acc.replace(str, " $str ")}.trimRedundantSpaces()

class Parser {

    private fun parseRec(iter: ListIterator<String>, endLn: List<String> = listOf("=")): MathToken {
        val first = parseMonomial(iter)
        return when(val keyword = iter.next()) {
            "+" -> {
                val second = parseRec(iter, endLn)
                AddToken(first, second as Token)
            }
            "-" -> {
                val second = parseRec(iter, endLn)
                SubToken(first, second as Token)
            }
            
            !in keywords -> error("Illegal word $keyword")
            else -> error("Keyword not available yet")
        }
    }

    private fun parseMonomial(iter: ListIterator<String>): Monomial {
        val expressions = mutableListOf<Token>()
        while (iter.visitNext() !in keywords) {
            expressions+=parseExpression(iter)
        }
        return Monomial(expressions)
    }

    private fun parseExpression(iter: ListIterator<String>): Token {
        val next = iter.next()
        next.toIntOrNull()?.let {
            return NumberTk(it.toNumber())
        }
        if (next in keywords)
            error("Keyword $next cannot be at this position")
        return Variable(next)
    }


}
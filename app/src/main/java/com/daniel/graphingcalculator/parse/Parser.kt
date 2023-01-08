package parse

import equation.VarExpression
import java.math.BigDecimal
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

enum class ParseType {
    FUNCTION,
    EQUATION,
    CONST
}

class Parser {

    fun parse(code: String): VarExpression {

        //------------------
        //first parsing step.
        //this analysis the basic structure of the equation
        //and parses it
        //-----------------

        val iter = optimize(code).split(" ").listIterator()
        val firstPart = parseRec(iter)
        iter.expect("=")
        val secondPart = parseRec(iter)

        //-----------------
        //Refactoring
        //the equation will now be refactored and stored in its base form
        //-----------------

        TODO()
    }

    /*
    The goal is to create a var expression for every variable in the equation
    so we have
        Left:                      | Right:
        Map<String, VarExpression> | Map<String, VarExpression>

    now we can search for equal variables on both sides and subtract those var expressions from each other
    so that we bring the variables on one side.
     */
    private fun refactor(left: MathToken, right: MathToken) {
        val variablesLeft = variableAnalysis(left)
        val variablesRight = variableAnalysis(right)

        if (variablesLeft.isEmpty() && variablesRight.isEmpty())
            error("Equation does not contain any variables")

        TODO()
    }

    private fun variableAnalysis(tk: MathToken): List<String> = analyseToken(tk.first)+analyseToken(tk.second)

    private fun analyseToken(tk: Token): List<String> =
        when(tk) {
            is Monomial -> analyseMonomial(tk)
            is MathToken -> variableAnalysis(tk)
            else -> error("token $tk should not be there")
        }

    private fun analyseMonomial(monomial: Monomial): List<String> = monomial.tokens.filterIsInstance<Variable>().map { it.name }

    private fun parseRec(iter: ListIterator<String>, endLn: List<String> = listOf("=")): MathToken {
        val first = parseMonomial(iter)
        if (!iter.hasNext())
            return AddToken(first, Monomial(listOf(NumberTk(0.toNumber()))))
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
        //numbers
        next.toDoubleOrNull()?.let {
            return NumberTk(it.toNumber())
        }
        next.toIntOrNull()?.let {
            return NumberTk(it.toNumber())
        }
        //keyword should not apply in an expression
        //An Exception would be ( ) but those are not implemented yet
        if (next in keywords)
            error("Keyword $next cannot be at this position")
        return Variable(next)
    }


}
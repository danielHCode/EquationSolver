package math

import numbers.Number

sealed interface Token

data class NumberTk(val value: Number) : Token

data class Variable(val name: String) : Token

data class Monomial(val tokens: List<Token>) : Token

sealed interface MathToken : Token {
    val first: Token
    val second: Token
}

data class AddToken(override val first: Token, override val second: Token) : Token, MathToken

data class SubToken(override val first: Token, override val second: Token) : Token, MathToken

data class MulToken(override val first: Token, override val second: Token) : Token, MathToken

data class DivToken(override val first: Token, override val second: Token) : Token, MathToken

data class Container(val tokens: Token) : Token
package equation

import numbers.Number
import numbers.num
import kotlin.math.sqrt


interface Expression {
}

typealias NumType = Number

interface VarExpression : Expression {
    val num: NumType
    val child: VarExpression?
    fun solve(): List<NumType>
    val isSolvable: Boolean
    val tier: Int

    fun toConstExpression(): ConstExpression
    fun toLinearExpression(): LinearVarExpression
    fun toQuadraticExpression(): QuadraticVarExpression
    fun simplify(other: VarExpression): VarExpression
}

class ConstExpression(override val num: NumType) : VarExpression {
    //consts
    override val child: VarExpression? = null
    override val isSolvable: Boolean = true
    override val tier: Int = 0

    //functions
    override fun solve(): List<NumType> = listOf(num)

    override fun simplify(other: VarExpression) = when (other.tier) {
        tier -> simplify(other as ConstExpression)
        else -> other.simplify(this)
    }

    fun simplify(other: ConstExpression) = ConstExpression(num-other.num)

    override fun toConstExpression(): ConstExpression = this

    override fun toLinearExpression(): LinearVarExpression = LinearVarExpression(0.num, this)

    override fun toQuadraticExpression(): QuadraticVarExpression = QuadraticVarExpression(0.num, toLinearExpression())
}

class LinearVarExpression(override val num: NumType, override val child: ConstExpression) : VarExpression {
    //consts
    override val isSolvable: Boolean = true
    override val tier: Int = 1

    //functions
    override fun solve(): List<NumType> = listOf(solveLinear())

    private fun solveLinear(): NumType = (-child.num) / num

    override fun simplify(other: VarExpression) = when {
        other.tier == tier -> simplify(other as LinearVarExpression)
        other.tier < tier -> LinearVarExpression(num, child.simplify(other as ConstExpression))
        else -> other.simplify(this)
    }

    fun simplify(other: LinearVarExpression) = LinearVarExpression(num-other.num, child.simplify(other.child))

    override fun toConstExpression(): ConstExpression = error("Cannot convert linear to const")

    override fun toLinearExpression(): LinearVarExpression = this

    override fun toQuadraticExpression(): QuadraticVarExpression = QuadraticVarExpression(0.num, this)

}

class QuadraticVarExpression(override val num: NumType, override val child: LinearVarExpression) : VarExpression {
    //consts
    override val tier: Int = 2

    //functions
    override fun solve() = mutableListOf<NumType>().apply {
        if (num == 0.num) {
            addAll(child.solve())
            return@apply
        }
        val a = num
        val b = child.num
        val c = child.child.num
        val dis = (b * b) - 4.num * a * c
        when {
            dis < 0.num -> return@apply
            dis == 0.num -> add((-b)/(2.num*a))
            dis > 0.num -> {
                add((-b+dis.sqrt())/(2.num*a))
                add((-b-dis.sqrt())/(2.num*a))
            }
        }
    }
    override val isSolvable: Boolean
        get() = ((child.num* child.num) - 4.num * num * child.child.num) >= 0.num

    override fun simplify(other: VarExpression): VarExpression = when {
        other.tier < tier -> QuadraticVarExpression(num, child.simplify(other).toLinearExpression())
        other.tier == tier -> QuadraticVarExpression(num-other.num, child.simplify(other.child ?: error("invalid")).toLinearExpression())
        else -> other.simplify(this)
    }

    override fun toConstExpression(): ConstExpression = error("cannot convert quadratic to const")

    override fun toLinearExpression(): LinearVarExpression = error("cannot convert quadratic to linear")

    override fun toQuadraticExpression(): QuadraticVarExpression = this
}
package math

import equation.*
import numbers.Number

class VarExpressionWrapper(val obj: VarExpression,

) : VarExpression {

    override val num: NumType = obj.num
    override val child: VarExpression? = obj.child
    override val isSolvable: Boolean = obj.isSolvable
    override val tier: Int = obj.tier

    private val solutions = obj.solve()
    private  val values = mutableMapOf<NumType, NumType>()


    override fun solve(): List<NumType> = solutions

    override fun toConstExpression(): ConstExpression = obj.toConstExpression()

    override fun toLinearExpression(): LinearVarExpression = obj.toLinearExpression()

    override fun toQuadraticExpression(): QuadraticVarExpression = obj.toQuadraticExpression()

    override fun simplify(other: VarExpression): VarExpression = obj.simplify(other)

    override fun forX(x: Number): Number = values[x] ?: obj.forX(x).also { values[x] = it }

}
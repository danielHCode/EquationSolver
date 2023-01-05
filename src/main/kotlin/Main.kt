import equation.quadratic
import ui.CanvasWrapper
import ui.FunctionCanvas
import ui.Window
import javax.swing.JTextField

fun main(args: Array<String>) {
    if (args.isEmpty())
        println("Provide Arguments to the program")

    args.forEach { arg ->
        when(arg) {
            "info" -> println("Equation Solver V0.9")
            "shell" -> console()
            "ui" -> {
                Window(600, 800, CanvasWrapper(FunctionCanvas(quadratic(1, 0, 0))), name= "Graph")
            }
        }
    }

}
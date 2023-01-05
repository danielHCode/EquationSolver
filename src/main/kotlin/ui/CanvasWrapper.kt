package ui

import javax.swing.JPanel

class CanvasWrapper(canvas: FunctionCanvas) : JPanel() {
    init {
        add(canvas)
    }
}
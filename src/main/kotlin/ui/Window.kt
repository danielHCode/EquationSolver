package ui

import javax.swing.JComponent
import javax.swing.JFrame

class Window(width: Int, height: Int, child: JComponent) : JFrame() {
    var child = child
        set(value) = run {contentPane = value }

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        setBounds(0, 0, width, height)
        contentPane = child
        isVisible = true
    }
}
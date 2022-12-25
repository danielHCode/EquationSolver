package ui

import javax.swing.JPanel


class Graph(private val window: Window): JPanel() {

    init {
        window.child = this
    }

}
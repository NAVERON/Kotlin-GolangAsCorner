package org.eron.fxkotlin.library

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage

class LibraryLauncher : Application() {
    override fun start(primaryStage: Stage) {
        val root : Pane = createContent()
        val scene = Scene(root)

        primaryStage.scene = scene
        primaryStage.show()

    }

    fun createContent() : Pane {
        return Pane()
    }

}

fun main(args : Array<String>) {
    Application.launch(LibraryLauncher::class.java, *args)
}
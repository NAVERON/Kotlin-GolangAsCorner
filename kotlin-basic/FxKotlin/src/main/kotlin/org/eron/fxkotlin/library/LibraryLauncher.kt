package org.eron.fxkotlin.library

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import javafx.stage.Stage

class LibraryLauncher : Application() {

    override fun start(primaryStage: Stage) {
        val root : Pane = this.createContent()
        val scene = Scene(root)

        primaryStage.scene = scene
        primaryStage.show()
    }

    private val library: Library = Library(DerbyBookDAO())

    private fun createContent() : Pane {
        val root : GridPane = GridPane()

        val keyWords : TextField = TextField().also {
            it.promptText = "search key"
        }
        val searchType : ChoiceBox<BookSearchTypeEnum> = ChoiceBox<BookSearchTypeEnum>().also {
            for(type in BookSearchTypeEnum.values()) {
                it.items.add(type)
            }
            it.selectionModel.selectFirst()
        }
        val booksList : ListView<Book> = ListView<Book>().also {
            // 初始化 list 所有书
            val books : List<Book> = this.library.allBooks()
            println("initial book list --> $books")
            it.items.addAll(books)
        }
        val doLoan : Button = Button("loan").also {
            it.setOnAction {
                // 借书
                val selected : Long? = booksList.selectionModel.selectedItem?.uniqueID
                println("current selected id --> $selected")
                if (selected != null) {
                    this.library.loanBook(selected)
                }

                this.refreshList(booksList)
            }
        }
        val doReturn : Button = Button("return").also {
            it.setOnAction {
                // 还书
                val selected : Long? = booksList.selectionModel.selectedItem?.uniqueID
                println("current selected id --> $selected")
                if (selected != null) {
                    val returned = this.library.returnBackBook(selected)
                }

                this.refreshList(booksList)
            }
        }
        val dataDemoBTN : Button = Button("newDemo").also {
            it.setOnAction {
                this.library.insertDemoBook()
                this.refreshList(booksList)
            }
        }

        keyWords.setOnAction {
            val keyString : String = keyWords.text
            if (keyString.isBlank()) {
                this.refreshList(booksList)
                return@setOnAction
            }

            val keyType : BookSearchTypeEnum = searchType.selectionModel.selectedItem
            val queryBooks : List<Book> = this.library.search(keyType, keyString)

            booksList.items.setAll(queryBooks)
        }

        // 整理所有界面变量 布局
        root.add(keyWords, 0, 0)
        root.add(searchType, 1, 0)
        root.add(booksList, 0, 1, 2, 1)
        root.add(doLoan, 0, 2)
        root.add(doReturn, 1, 2)
        root.add(dataDemoBTN, 0, 3)

        root.hgap = 20.0
        root.vgap = 10.0

        return root
    }

    private fun refreshList(bookView : ListView<Book>) {
        bookView.items.setAll(this.library.allBooks())
    }

}

fun main(args : Array<String>) {
    Application.launch(LibraryLauncher::class.java, *args)
}
package org.eron.fxkotlin.library

class Library(bookDAO : BookDAO) {

    init {
        bookDAO.setup()
    }

    fun addNewBook(name : String, authors : List<String>, year : String) : Unit {

    }

    fun loanBook(id : Long) {

    }

    fun returnBackBook(id : Long) {

    }

    fun search(type : BookSearchTypeEnum, value : Any) {

    }
}
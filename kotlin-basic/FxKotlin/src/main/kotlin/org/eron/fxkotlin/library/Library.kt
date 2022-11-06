package org.eron.fxkotlin.library

import kotlin.random.Random

class Library(bookDAO: BookDAO) {

    private val _bookDAO : BookDAO
    init {
        this._bookDAO = bookDAO
        this._bookDAO.setup()
    }

    fun addNewBook(name : String, authors : List<String>, year : String) : Unit {
        val newBook = Book().apply {
            this.name = name
            this.authors = authors
            this.publishedDate = year
            this.available = true
        }

        this._bookDAO.insertBook(newBook)
    }

    // just for demo
    fun insertDemoBook() {
        val randNum = Random.nextInt(100)
        val newBook = Book().apply {
            this.name = "demo$randNum"
            this.authors = listOf("author1", "author2", "author_$randNum")
            this.publishedDate = Random.nextInt(1000, 2000).toString()
            this.available = true
        }

        this._bookDAO.insertBook(newBook)
    }

    fun loanBook(id : Long) : List<Book> {
        val canLoan = this._bookDAO.findBookByProperty(BookSearchTypeEnum.ID, id).filter { it.available }
        if(canLoan.isEmpty()){
            println("this book is not available")
            return listOf()
        }

        canLoan.forEach {
            it.available = false
            this._bookDAO.updateBook(it)
        }
        return canLoan
    }

    fun returnBackBook(id : Long) : List<Book> {
        val needReturn = this._bookDAO.findBookByProperty(BookSearchTypeEnum.ID, id).filter { !it.available }
        if(needReturn.isEmpty()){
            println("this book not return, it's available")
            return listOf()
        }

        needReturn.forEach {
            it.available = true
            this._bookDAO.updateBook(it)
        }

        return needReturn
    }

    fun search(type : BookSearchTypeEnum, value : Any) : List<Book> {
        return this._bookDAO.findBookByProperty(type, value)
    }

    fun allBooks() : List<Book> {
        return this._bookDAO.findAll()
    }
}
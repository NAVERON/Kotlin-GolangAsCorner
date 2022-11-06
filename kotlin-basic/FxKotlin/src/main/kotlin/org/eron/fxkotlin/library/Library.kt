package org.eron.fxkotlin.library

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

    fun loanBook(id : Long) {
        this._bookDAO.findBookByProperty(BookSearchTypeEnum.ID, id)
                    .forEach {
                        it.available = false
                        this._bookDAO.updateBook(it)
                    }
    }

    fun returnBackBook(id : Long) {
        this._bookDAO.findBookByProperty(BookSearchTypeEnum.ID, id)
            .forEach {
                it.available = true
                this._bookDAO.updateBook(it)
            }
    }

    fun search(type : BookSearchTypeEnum, value : Any) : List<Book> {
        return this._bookDAO.findBookByProperty(type, value)
    }

    fun allBooks() : List<Book> {
        return this._bookDAO.findAll()
    }
}
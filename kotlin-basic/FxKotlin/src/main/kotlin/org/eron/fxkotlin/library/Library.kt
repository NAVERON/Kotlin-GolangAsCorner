package org.eron.fxkotlin.library

class Library(bookDAO : BookDAO) {

    lateinit var bookDAO : BookDAO
    init {
        this.bookDAO.setup()
    }

    fun addNewBook(name : String, authors : List<String>, year : String) : Unit {
        val newBook = Book().apply {
            this.name = name
            this.authors = authors
            this.publishedDate = year
            this.available = true
        }

        this.bookDAO.insertBook(newBook)
    }

    fun loanBook(id : Long) {
        this.bookDAO.findBookByProperty(BookSearchTypeEnum.ID, id)
                    .forEach {
                        it.available = false
                        this.bookDAO.updateBook(it)
                    }
    }

    fun returnBackBook(id : Long) {
        this.bookDAO.findBookByProperty(BookSearchTypeEnum.ID, id)
            .forEach {
                it.available = true
                this.bookDAO.updateBook(it)
            }
    }

    fun search(type : BookSearchTypeEnum, value : Any) : List<Book> {
        return this.bookDAO.findBookByProperty(type, value)
    }

    fun allBooks() : List<Book> {
        return this.bookDAO.findAll()
    }
}
package org.eron.fxkotlin.library

sealed interface BookDAO : DAO {

    fun insertBook(book : Book)
    fun updateBook(book : Book)
    fun deleteBook(book : Book)
    fun findBookByProperty(searchType : BookSearchTypeEnum, value : Any) : List<Book>
    fun findAll() : List<Book>

}




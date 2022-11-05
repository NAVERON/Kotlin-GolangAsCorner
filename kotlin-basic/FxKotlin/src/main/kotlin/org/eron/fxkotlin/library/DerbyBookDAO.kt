package org.eron.fxkotlin.library

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.Statement

class DerbyBookDAO : BookDAO {

    val JDBC_URL : String = "jdbc:derby:library.db;create=true"
    val DRIVER_CLASS : String = "org.apache.derby.jdbc.EmbeddedDriver"
    val USRE : String = "root"
    val PASSWORD : String = "root"
    val DERBY_SHUTDOWN_URL : String = "jdbc:derby:;shutdown=true"

    val connection : Connection = DriverManager.getConnection(JDBC_URL)
    var statement : Statement = connection.createStatement()

    val createTableStatement : String = """
        create table if not exists library (
            id bigint not null generated always as identity (start with 1, increment by 1), 
            name varchar(30), 
            authors varchar(100), 
            publisher_year integer, 
            available boolean
        )
    """.trimIndent()

    init {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver").getDeclaredConstructor().newInstance()
        // 对象初始化 直接执行一次
        this.setup()
    }

    override fun setup() {
        println("initial derbyDB setup")
        statement.executeUpdate(createTableStatement)
    }

    override fun connect() {
        println("derby connect")
    }

    override fun close() {
        statement.close()

        DriverManager.getConnection(DERBY_SHUTDOWN_URL)
    }

    override fun insertBook(book: Book) {
        statement = connection.createStatement()
        val insert : String = "insert into library(name, authors, publisher_year, available) " +
                "values(${book.name}, ${book.authors.toString()}, ${book.publishedDate}, ${book.available})"
        statement.execute(insert)
        statement.close()
    }

    override fun updateBook(book: Book) {
        statement = connection.createStatement()
        val update : String = "update library set " +
                "name = ${book.name}, authors = ${book.authors.toString()}, " +
                "publisher_year = ${book.publishedDate}, available = ${book.available} " +
                "where id = ${book.uniqueID}"
        statement.executeUpdate(update)
        statement.close()
    }

    override fun deleteBook(book: Book) {
        statement = connection.createStatement()
        val delete : String = "delete from library where id = ${book.uniqueID}"

        statement.execute(delete)
        statement.close()
    }

    override fun findBookByProperty(searchType: BookSearchTypeEnum, value : Any): List<Book> {
        var whereClause : String = ""
        var books : List<Book> = listOf()

        whereClause = when(searchType) {
            BookSearchTypeEnum.ID -> "id = ${value.toString()}"
            BookSearchTypeEnum.NAME -> "name like %${value.toString()}%"
            BookSearchTypeEnum.AUTHOR -> "authors like %${value.toString()}%"
            BookSearchTypeEnum.PUBLISHED_YEAR -> "publisher_year = ${value.toString()}"
            BookSearchTypeEnum.AVAILABLE -> "available = ${value.toString()}"
        }

        val queryBy : String = "select * from library where $whereClause"
        statement = connection.createStatement()
        val res : ResultSet = statement.executeQuery(queryBy)
        books = transferToBook(res)
        statement.close()

        return books
    }

    override fun findAll(): List<Book> {
        statement = connection.createStatement()
        val all : String = ""
        val res = statement.executeQuery(all)

        val allBooks : List<Book> = transferToBook(res)
        statement.close()

        return allBooks
    }

    fun transferToBook(result : ResultSet) : List<Book> {
        val meta : ResultSetMetaData = result.metaData
        println("table column count --> ${meta.columnCount}")

        result.close()
        return listOf()
    }
}
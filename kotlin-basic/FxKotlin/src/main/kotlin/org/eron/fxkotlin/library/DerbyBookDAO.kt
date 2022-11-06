package org.eron.fxkotlin.library

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.Statement

class DerbyBookDAO : BookDAO {

    private val JDBC_URI : String = "jdbc:derby:library.db;create=true" //
    private val DRIVER_CLASS : String = "org.apache.derby.iapi.jdbc.DRDAServerStarter"
                                      //"org.apache.derby.jdbc.EmbeddedDriver"
    private val USRE : String = "root"
    private val PASSWORD : String = "root"
    private val DERBY_SHUTDOWN_URL : String = "jdbc:derby:;shutdown=true"

    private lateinit var connection : Connection
    private lateinit var statement : Statement

    private val dropTableStatement : String = """
        drop table library
    """.trimIndent()
    private val createTableStatement : String = """
        create table library (
            id bigint not null generated always as identity (start with 1, increment by 1), 
            name varchar(30), 
            authors varchar(100), 
            publisher_year varchar(50), 
            available boolean, 
            primary key(id)
        )
    """.trimIndent()
    private val initDatas : String = """
        insert into library(name, authors, publisher_year, available) values('aa', 'a,b,c', '1990', false)
    """.trimIndent()
//    insert into library(name, authors, publisher_year, available) values('bb', 'c,b,a', '1991', 0)
//    insert into library(name, authors, publisher_year, available) values('cc', 'b,c,a', '1992', 0)
    init {
        // Class.forName(DRIVER_CLASS).getDeclaredConstructor().newInstance()
        // 对象初始化 直接执行一次
        // this.setup()
    }

    override fun setup() {
        println("initial derby db setup")
        this.connection = DriverManager.getConnection(JDBC_URI)
        this.statement = connection.createStatement()

        // 表的初始化不能使用 if not exists 需要上层写逻辑判断是否存在表
        statement.executeUpdate(dropTableStatement)
        statement.executeUpdate(createTableStatement)  // 表初始化

        // 数据
        this.statement.executeUpdate(initDatas)
        this.statement.close()
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
                "values('${book.name}', '${book.authors.joinToString(",")}', '${book.publishedDate}', ${book.available})"

        println("insert book sql : $insert")
        statement.execute(insert)
        statement.close()
    }

    override fun updateBook(book: Book) {
        statement = connection.createStatement()
        val update : String = "update library set " +
                "name = '${book.name}', authors = '${book.authors.joinToString(",")}', " +
                "publisher_year = '${book.publishedDate}', available = ${book.available} " +
                "where id = ${book.uniqueID}"

        println("update book sql : $update")
        statement.executeUpdate(update)
        statement.close()
    }

    override fun deleteBook(book: Book) {
        statement = connection.createStatement()
        val delete : String = "delete from library where id = ${book.uniqueID}"

        println("delete sql : $delete")
        statement.execute(delete)
        statement.close()
    }

    override fun findBookByProperty(searchType: BookSearchTypeEnum, value : Any): List<Book> {
        var books : List<Book>

        var whereClause : String = when(searchType) {
            BookSearchTypeEnum.ID -> if(value.toString().isBlank()) "id = -1" else "id = ${value.toString()}"
            BookSearchTypeEnum.NAME -> "name like '%${value.toString()}%'"
            BookSearchTypeEnum.AUTHOR -> "authors like '%${value.toString()}%'"
            BookSearchTypeEnum.PUBLISHED_YEAR -> "publisher_year = '${value.toString()}' "
            BookSearchTypeEnum.AVAILABLE -> "available = ${value.toString()}"
        }

        val queryBy : String = "select * from library where $whereClause "
        println("query books sql : $queryBy")

        statement = connection.createStatement()
        val res : ResultSet = statement.executeQuery(queryBy)
        books = transferToBook(res)
        statement.close()

        println("query result --> $books")
        return books
    }

    override fun findAll(): List<Book> {
        statement = connection.createStatement()
        val all : String = "select * from library"
        val res = statement.executeQuery(all)

        val allBooks : List<Book> = transferToBook(res)
        statement.close()

        return allBooks
    }

    /**
     * transfer string to book
     * ORM object relationship to map
     */
    private fun transferToBook(result : ResultSet) : List<Book> {
        var transferRes : MutableList<Book> = mutableListOf()

        val meta : ResultSetMetaData = result.metaData
        (1..meta.columnCount).forEach{
            print("${meta.getColumnName(it)}, ")
        }

        while (result.next()){
            // for suto list save
//            var tmp : MutableList<String> = mutableListOf()
//            (1..meta.columnCount).forEach{
//                val colName = meta.getColumnName(it)
//                tmp.add(it - 1, result.getString(colName))
//            }
//            println("array save all --> $tmp")

            val id : Long = result.getString(1).toLong()
            val name : String = result.getString(2)
            val authors : String = result.getString(3)
            val publish : String = result.getString(4)
            val available : Boolean = result.getString(5).toBoolean()
            println("get result data --> $id, $name, $authors, $publish, $available")

            // create new book
            val book : Book = Book().apply {
                this.uniqueID = id
                this.name = name
                this.setAuthors(authors)
                this.publishedDate = publish
                this.available = available
            }
            transferRes.add(book)
        }

        result.close()
        return transferRes
    }
}
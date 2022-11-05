package org.eron.fxkotlin.library

class Book {

    var uniqueID : Long = 0
    var name : String = ""
    var authors : List<String> = listOf()
    var publishedDate : String = ""
    var available : Boolean = true

    constructor(){}
    constructor(name: String) {
        this.name = name
    }

    fun getAuthors() : String {
        return buildString {
            for(author in authors){
                append(author)
                append(", ")
            }
        }

//        return authors.joinToString(", ")
    }

    fun setAuthors(authors : String){
        this.authors = authors.split(",").map { it.trim() }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Book

        if (uniqueID != other.uniqueID) return false
        if (name != other.name) return false
        if (authors != other.authors) return false
        if (publishedDate != other.publishedDate) return false
        if (available != other.available) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uniqueID.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + authors.hashCode()
        result = 31 * result + publishedDate.hashCode()
        result = 31 * result + available.hashCode()
        return result
    }

    override fun toString(): String {
        return "Book(" +
                "uniqueID=$uniqueID, " +
                "name='$name', " +
                "authors=$authors, " +
                "publishedDate='$publishedDate', " +
                "available=$available" +
                ")"
    }

}




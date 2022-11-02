package org.eron.springkotlin.simple

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface SimpleMessageRepository : CrudRepository<SimpleMessage, Long> {

    @Query("select * from messages")
    fun findMessages() : List<SimpleMessage>

}









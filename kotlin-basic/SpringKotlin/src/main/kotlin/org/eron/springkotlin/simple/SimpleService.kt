package org.eron.springkotlin.simple

import org.springframework.stereotype.Service

@Service
class SimpleService(val db : SimpleMessageRepository) {

    fun list() : List<SimpleMessage> {
        return db.findMessages()
    }

    fun post(message : SimpleMessage) : Unit {
        db.save(message)
    }

}





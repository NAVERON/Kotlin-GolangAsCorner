package org.eron.springkotlin.simple

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("simple")
class SimpleAPI(val service : SimpleService) {

    @GetMapping("index")
    fun index() : List<SimpleMessage> {
        return listOf(
            SimpleMessage(0L, ""),
            SimpleMessage(1L, ""),
            SimpleMessage(2L, "")
        )
    }

    @GetMapping("list")
    fun list() : List<SimpleMessage> {
        return service.list()
    }

    @PostMapping("save")
    fun save(@RequestBody message : SimpleMessage) {
        return service.post(message)
    }

    @PostMapping("delete/{id}")
    fun delete(@PathVariable(name = "id") id : Long) {

    }

}










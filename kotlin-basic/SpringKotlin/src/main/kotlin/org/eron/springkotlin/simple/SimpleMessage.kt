package org.eron.springkotlin.simple

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table


@Table("messages")
data class SimpleMessage(@Id val id : Long?, val text : String)



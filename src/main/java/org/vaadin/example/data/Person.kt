package org.vaadin.example.data

import java.util.*

data class Person(
    var id: String=UUID.randomUUID().toString(),
    var lastName: String,
    var firstName: String,
    var midName: String?,
    var phone: String?
)

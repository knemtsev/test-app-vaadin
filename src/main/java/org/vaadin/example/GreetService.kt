package org.vaadin.example

class GreetService {
    fun greet(name: String?): String {
        return if (name.isNullOrEmpty()) {
            "Hello anonymous user"
        } else {
            "Hello $name"
        }
    }
}
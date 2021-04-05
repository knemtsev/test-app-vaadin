package org.vaadin.example

class GreetService {
    fun greet(name: String?): String {
        return if (name == null || name.isEmpty()) {
            "Hello anonymous user"
        } else {
            "Hello $name"
        }
    }
}
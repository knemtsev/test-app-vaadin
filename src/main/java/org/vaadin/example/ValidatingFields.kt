package org.vaadin.example

import org.vaadin.example.view.BorderedField

class ValidatingFields(val lastName: BorderedField,
                       val firstName: BorderedField
) {
    var lastNameOk: Boolean=false
    var firstNameOk: Boolean=false

    fun verify(): Boolean {
        lastNameOk  = !lastName.value.isNullOrEmpty()
        firstNameOk = !firstName.value.isNullOrEmpty()

        return lastNameOk && firstNameOk
    }

    fun markField(){
        if(!lastNameOk){

        }

        if(!firstNameOk){

        }
    }

    fun errorMessage(): String {
        return (if(!lastNameOk) "Last Name is required\n" else "")+
               (if(!firstNameOk) "First Name is required" else "")

    }
}
package org.vaadin.example.data

import java.sql.Connection
import java.sql.DriverManager
import java.util.*

class Db {
    var connection: Connection?=null
        get() {
            if(field ==null) {
                val url = "jdbc:postgresql://localhost:5432/vaadin"
                val props = Properties()
                props.setProperty("user", "vaadin");
                props.setProperty("password", "slovo")
                try {
                    field=DriverManager.getConnection(url, props)
                } catch (e: Throwable){
                    println(e.message)
                }

            }
        return field
    }

    fun close(){
        connection?.close()
    }
}
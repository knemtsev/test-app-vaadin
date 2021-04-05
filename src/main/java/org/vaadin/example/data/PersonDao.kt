package org.vaadin.example.data

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class PersonDao(private val db: Db) {
    private val createStatement: String by lazy {
        """
CREATE TABLE IF NOT EXISTS persons (
  id varchar(45) NOT NULL,
  last_name varchar(45) NOT NULL,
  first_name varchar(45) NOT NULL,
  mid_name varchar(45),
  phone varchar(45),
  PRIMARY KEY (id)
);    
    """
    }

    fun createTable() {
        var stmt: PreparedStatement? = null
        val connection = db.connection
        try {
            if (connection != null) {
                stmt = connection.prepareStatement(createStatement)
                if (stmt.execute()) {
                    println("Create table OK")
                }
            }
        } catch (e: Throwable) {
            println(e.message)
        } finally {
            stmt?.close()
        }
    }

    fun insert(person: Person) {
        var stmt: PreparedStatement? = null
        val connection = db.connection
        try {
            if (connection != null) {
                val sql ="INSERT INTO persons (id, last_name, first_name, mid_name, phone) VALUES (?, ?, ?, ?, ?);"
                stmt = connection.prepareStatement(sql)
                stmt.setString(1, person.id)
                stmt.setString(2, person.lastName)
                stmt.setString(3, person.firstName)
                stmt.setString(4, person.midName)
                stmt.setString(5, person.phone)
                connection.autoCommit = false
                stmt.execute()
                connection.commit()
            }
        } catch (e: Throwable) {
            connection?.rollback()
            println(e.message)
        } finally {
            stmt?.close()
        }
    }

    fun delete(person: Person) {
        var stmt: PreparedStatement? = null
        val connection = db.connection
        try {
            if (connection != null) {
                val sql = "DELETE FROM persons WHERE id=?"
                stmt = connection.prepareStatement(sql)
                stmt.setString(1, person.id)
                connection.autoCommit = false
                stmt.execute()
                connection.commit()
            }
        } catch (e: Throwable) {
            connection?.rollback()
            println(e.message)
        } finally {
            stmt?.close()
        }
    }

    private fun mapFromResult(result: ResultSet): Person {
        return Person(
            id = result.getString(1),
            lastName = result.getString(2),
            firstName = result.getString(3),
            midName = result.getString(4),
            phone = result.getString(5)
        )
    }

    fun getById(id: String): Person? {
        var stmt: PreparedStatement? = null
        val connection = db.connection
        var person: Person? = null
        try {
            if (connection != null) {
                val sql = "SELECT * FROM persons WHERE id=?"
                stmt = connection.prepareStatement(sql)
                stmt.setString(1, id)
                val result = stmt.executeQuery(sql)
                if (result.next()) {
                    person = Person(
                        id = result.getString(1),
                        lastName = result.getString(2),
                        firstName = result.getString(3),
                        midName = result.getString(4),
                        phone = result.getString(5)
                    )
                }
                result?.close()
            }
        } catch (e: Throwable) {
            println(e.message)
        } finally {
            stmt?.close()
        }
        return person
    }

    fun all(): List<Person> {
        var stmt: PreparedStatement? = null
        val connection = db.connection
        val list = ArrayList<Person>()
        try {
            if (connection != null) {
                val sql = "SELECT * FROM persons"
                stmt = connection.prepareStatement(sql)
                val result = stmt.executeQuery()
                while (result.next()){
                    list.add(mapFromResult(result))
                }
            }
        } catch (e: Throwable) {
            println(e.message)
        } finally {
            stmt?.close()
        }
        return list
    }
}
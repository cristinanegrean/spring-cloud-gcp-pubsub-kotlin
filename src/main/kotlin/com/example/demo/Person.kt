package com.example.demo

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Person(
        val firstName: String,
        val lastName: String,
        val email: String,
        @Id @GeneratedValue
        var id: Long? = 0) {
    private constructor() : this("", "", "")
}
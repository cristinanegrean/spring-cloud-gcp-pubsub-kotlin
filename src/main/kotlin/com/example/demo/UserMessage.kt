package com.example.demo

import java.time.LocalDateTime

data class UserMessage(
    val body: String, 
    val username: String, 
    var createdAt: LocalDateTime? = LocalDateTime.now())

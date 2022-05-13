package com.example.myquizzkotlin.model

data class Question(
    val question: String,
    val response1: String,
    val response2: String,
    val response3: String,
    val response4: String,
    val correctResponse: String
)

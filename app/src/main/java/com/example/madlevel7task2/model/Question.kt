package com.example.madlevel7task2.model

data class Question(
    val question: String? = null,
    val correctAnswer: String? = null,
    val answerOptions: List<String>? = null,
    val buildingUrl: String = "",
    val buildingName: String = ""
)
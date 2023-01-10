package com.example.quizzapp

data class Question(
    var id: Int,
    var content: String,
    var image: Int,
    var option1: String,
    var option2: String,
    var option3: String,
    var option4: String,
    var answer: Int,
)


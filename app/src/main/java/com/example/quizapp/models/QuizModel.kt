package com.example.quizapp.models

data class QuizModel(
    val category: String,
    val correct_answer: String,
    val difficulty: String,
    val incorrect_answers: List<String>,
    val question: String,
    val type: String
){
    constructor(): this("","","", emptyList(),"","")
}





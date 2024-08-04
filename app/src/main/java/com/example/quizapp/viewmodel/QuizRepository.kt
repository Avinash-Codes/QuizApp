package com.example.quizapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class QuizRepository{
    val _navigateToResult = mutableStateOf<Int?>(null)
    val navigateToResult : State<Int?> = _navigateToResult

    fun navigateToResult(score: Int){
        _navigateToResult.value = score
    }

    fun resetNavigateToResult(){
        _navigateToResult.value = null
    }

}
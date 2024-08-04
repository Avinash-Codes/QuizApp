package com.example.quizapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.asIntState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quizapp.models.QuizModel
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle, val quizRepository: QuizRepository): ViewModel() {


    private val _questions = mutableStateListOf<QuizModel>()
    var questions: List<QuizModel> = _questions

    private val _selectedOption = mutableStateMapOf<QuizModel,String?>()
    val selectedOption: Map<QuizModel,String?>
        get() = _selectedOption

    private val _currentQuestionIndex = mutableStateOf(0)
    val currentQuestionIndex: State<Int> = _currentQuestionIndex.asIntState()

    private val _answerCheckedStates = mutableStateMapOf<QuizModel,Boolean>()
    val answerCheckedStates: Map<QuizModel,Boolean> = _answerCheckedStates

    private val _showDialog = mutableStateOf(false)
    val showDialog: State<Boolean> = _showDialog

    private val _timeRemaining = mutableStateOf(600)
    val timeRemaining: State<Int> = _timeRemaining.asIntState()


    //Clock timer
    private var timerJob: Job? = null


    init{
        val category = savedStateHandle.get<String>("category")
        val difficulty = savedStateHandle.get<String>("difficulty")
        if(category != null && difficulty != null){
            loadQuestions(category, difficulty)
        }

        startTimer()
    }

    private fun startTimer(){
        timerJob = viewModelScope.launch {
            while(_timeRemaining.value >0 ){
                delay(1000)
                _timeRemaining.value--
            }

            autoSubmitQuiz()
        }
    }

    private fun autoSubmitQuiz(){
        val result = calculateResult()
        quizRepository.navigateToResult(result.correctAnswers)
    }
    fun cancelTimer(){
        timerJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        cancelTimer()
    }
    //End of Clock timer

    fun loadQuestions(category: String?, difficulty: String?) {
        viewModelScope.launch {
            try{
                FirebaseDatabase.getInstance().reference
                    .get()
                    .addOnSuccessListener { dataSnapshot ->
                        if (dataSnapshot.exists()) {
                            val questionsList = mutableListOf<QuizModel>()
                            for (snapshot in dataSnapshot.children) {
                                val quizModel = snapshot.getValue(QuizModel::class.java)
                                if (quizModel != null) {
                                    questionsList.add(quizModel)
                                }
                            }

                           _questions.addAll( questionsList.filter {
                                it.category == category && it.difficulty == difficulty
                            }.shuffled().take(10)
                            )
                        }
                    }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

    }

    fun calculateResult(): QuizResult {
        var correctAnswers = 0
        for (question in _questions) {
            if (_selectedOption[question] == question.correct_answer) {
                correctAnswers++
            }
        }
        return QuizResult(correctAnswers, _questions.size)
    }

    data class QuizResult(
        val correctAnswers: Int,
        val totalQuestions: Int
    )

    fun resetQuiz(){
        _currentQuestionIndex.value = 0
        _selectedOption.clear()
        _answerCheckedStates.clear()
        _questions.clear()
    }

    fun setShowDialog(show: Boolean){
        _showDialog.value = show
    }

    fun setAnswerChecked(question: QuizModel, isChecked: Boolean){
        _answerCheckedStates[question] = isChecked
    }

    fun selectOption(currentQuestion: QuizModel, option: String) {
        _selectedOption[currentQuestion] = option
    }

    fun nextQuestion(){
        if(_currentQuestionIndex.value < _questions.size - 1){
            _currentQuestionIndex.value++
        }
    }

    fun previousQuestion(){
        if(_currentQuestionIndex.value > 0){
            _currentQuestionIndex.value--
        }
    }



}


package com.example.quizapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.quizapp.presentation.sign_in.SignInState
import com.example.quizapp.presentation.sign_in.SignInResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update

class SignInVIewModel: ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asSharedFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update {
            SignInState(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
    }

    fun resetState(){
        _state.update {
            SignInState()
        }
    }
}
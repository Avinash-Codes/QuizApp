package com.example.quizapp.module

import com.example.quizapp.viewmodel.QuizRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuizModule {

    @Provides
    fun provideQuizRepository() : QuizRepository {
        return QuizRepository()
    }

}
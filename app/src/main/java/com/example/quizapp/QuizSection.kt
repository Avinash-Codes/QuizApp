package com.example.quizapp

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun QuizSectionUi(){
    Scaffold (
        topBar ={
            AppTollBar(toolbarTitle = "Quiz Section")
        }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    GradientBackground(isVertical = true, color = gradientColors2)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier,
                text = "Quiz Section",
                color = Color.Black,
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTollBar(toolbarTitle: String){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(60.dp)
            .background(GradientBackground(isVertical = true, color = gradientColors2))

    ) {
        TopAppBar(
            title = {
                Text(text = toolbarTitle, fontWeight = FontWeight.Bold)
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxSize().border(
                width = 0.5.dp,
                color = Color.Black,
                shape = RoundedCornerShape(0.dp)
        )
        )
    }
}
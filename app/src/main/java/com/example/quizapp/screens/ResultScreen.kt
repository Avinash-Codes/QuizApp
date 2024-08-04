package com.example.quizapp.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quizapp.models.QuizModel
import com.example.quizapp.viewmodel.QuizViewModel


@Composable
fun ResultScreen(
    score : Int,
    onPlayAgain :() -> Unit,
    onExit : () -> Unit
) {
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(key1 = score) {
        animatedProgress.animateTo(
            targetValue = score / 10f,
            animationSpec = tween(durationMillis = 1000)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF2196F3), Color(0xFF00BCD4))
                )
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Congratulations!",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(20.dp))

        CircularProgressIndicator(
            progress = animatedProgress.value,
            modifier = Modifier.size(200.dp),
            strokeWidth = 10.dp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Your Score: $score/10",
            fontSize = 24.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = onPlayAgain,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text(text = "Play Again", color = Color.White)
            }

            Button(
                onClick = onExit,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336))
            ) {
                Text(text = "Exit", color = Color.White)
            }
        }
    }
}

@Composable
fun Result(navController: NavController,score: Int,category: String?,difficulty: String?){
    val viewModel: QuizViewModel = hiltViewModel()
    ResultScreen(
        score = score,
        onPlayAgain = {
            viewModel.resetQuiz()
            viewModel.loadQuestions(category, difficulty)
            navController.navigate("questions/$difficulty/$category")
        },
        onExit = {
            navController.navigate("quizSection")
        },
    )
}
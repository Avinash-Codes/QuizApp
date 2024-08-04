package com.example.quizapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quizapp.models.QuizModel
import com.example.quizapp.viewmodel.QuizViewModel
import com.google.firebase.database.FirebaseDatabase


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Questions(difficulty: String?,navController: NavController,category: String?) {

    //Get ViewModel Instance
    val viewModel : QuizViewModel = hiltViewModel()

    val navigateToResult = viewModel.quizRepository.navigateToResult


    LaunchedEffect(key1 = navigateToResult.value) {
        if(navigateToResult.value!=null){
            val score = viewModel.calculateResult()
            navController.navigate("result/${score.correctAnswers}/$category/$difficulty")
            viewModel.quizRepository.resetNavigateToResult()
        }
    }
    Scaffold(
        topBar = {
            AppTollBar1(
                toolbarTitle = "Questions",
                navController = navController,
                currentIndex = viewModel.currentQuestionIndex.value,
            )
        },
        containerColor = Color(0xFF101010)
    ) {
        val questions = viewModel.questions
        val currentQuestionIndex = viewModel.currentQuestionIndex.value
        if (questions.isNotEmpty() && currentQuestionIndex < questions.size) {
            val currentQuestion = questions[currentQuestionIndex]
            val selectedOption = viewModel.selectedOption[currentQuestion]


            QuestionDisplay(question = currentQuestion,
                selectedOption = selectedOption ?: "",
                onOptionSelected = { option ->
                   viewModel.selectOption(currentQuestion, option)

                },
                onNextClick = {
                    if(viewModel.currentQuestionIndex.value < viewModel.questions.size - 1) {
                        viewModel.nextQuestion()
                    }else{
                        val score = viewModel.calculateResult()
                        navController.navigate("result/${score.correctAnswers}/$category/$difficulty")
                    }
            }, onBackClick = {
                    viewModel.previousQuestion()
            },
                isLastQuestion = viewModel.currentQuestionIndex.value == viewModel.questions.size - 1
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTollBar1(toolbarTitle: String, navController: NavController, currentIndex: Int) {

    val viewModel: QuizViewModel = hiltViewModel()
    // Add your code here

    val timeRemaining = viewModel.timeRemaining.value

    //Formating tie remaining in minutes and seconds
    val minutes = timeRemaining / 60
    val seconds = timeRemaining % 60
    val formattedTime = String.format("%02d:%02d", minutes, seconds)


    if(viewModel.showDialog.value){
        AlertDialog(
            onDismissRequest = {viewModel.setShowDialog(false)},
            title = { Text("Quit Quiz?", color = Color.White) },
            text = { Text("Are you sure you want to quit the quiz?", color = Color.White) },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.setShowDialog(false)
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xffff9580),
                        contentColor = Color.White
                    )
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = { viewModel.setShowDialog(false) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xffff9580),
                        contentColor = Color.White
                    )
                ) {
                    Text("No")
                }
            },
            containerColor = Color(0xFF101010),
        )
    }


    val questionProgressIndicator = ((currentIndex + 1).toFloat()) / 10
    TopAppBar(
        navigationIcon = @Composable {
            val icon = Icons.Filled.ArrowBack

            IconButton(onClick = {
                viewModel.setShowDialog(true)
            }) {
                Icon(imageVector = icon, contentDescription = "Back", tint = Color(0xFFFFFFFF))
            }
        },
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${currentIndex + 1}/10 $toolbarTitle", color = Color(0xFFFFFFFF))
                LinearProgressIndicator(
                    progress = questionProgressIndicator,
                    trackColor = Color(0xFFFFFFFF),
                    color = Color(0xFFEE7233),
                    modifier = Modifier.border(0.5.dp, Color(0xFFEE7233))
                )

                Text(text = formattedTime, color = Color(0xFFFFFFFF))
            }

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
    )
}

@Composable
fun QuestionDisplay(
    question: QuizModel,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    isLastQuestion: Boolean
) {
    val viewModel:QuizViewModel = hiltViewModel()
    val options = question.incorrect_answers + question.correct_answer
    //Use Question as the key for remember

    val shuffledOptions = remember(question) { options.shuffled() }

    val answerChecked = viewModel.answerCheckedStates[question] ?: false



    Column(
        modifier = Modifier
            .padding(top = 150.dp, start = 10.dp, end = 10.dp)
            .clip(RoundedCornerShape(15.dp))
            .size(500.dp, 790.dp)
            .background(Color(0xffff9580)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(15.dp))
                .size(400.dp, 250.dp)
                .background(Color(0xFF171616))
        ) {
            Text(
                text = question.question,
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.Center)
                    .wrapContentSize(),
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center,
                color = Color(0xFFFFFFFF)
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 40.dp, start = 10.dp, end = 10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            shuffledOptions.forEach { option ->
                Button(
                    onClick = {
                        onOptionSelected(option)
                        viewModel.setAnswerChecked(question, true)
                    },
                    enabled = !answerChecked || selectedOption == option,
                    modifier = Modifier
                        .aspectRatio(6f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =  when{
                            selectedOption == option && answerChecked -> {
                            if (option == question.correct_answer) Color.Green else Color.Red
                        }
                    else -> Color(0xFF171818)
            },
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(17.dp)
                ) {
                    Text(
                        text = option,
                        modifier = Modifier,
                        fontSize = 20.sp,
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                onBackClick()
            },
                modifier = Modifier
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF171818),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(17.dp),
            ) {
                Text(text = "Back", fontSize = 20.sp, )
            }

            Button(onClick = {
                onNextClick()
            },
                modifier = Modifier
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF171818),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(17.dp)
            ) {
                Text(text = if(isLastQuestion) "Submit" else "Next", fontSize = 20.sp, )
            }
        }
    }
}
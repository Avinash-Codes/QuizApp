package com.example.quizapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.quizapp.models.QuizModel
import com.example.quizapp.ui.theme.GradientBackground
import com.example.quizapp.ui.theme.gradientColors2
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun QuizSectionUi(navController: NavController){
    val quizModelList = remember { mutableStateOf<List<QuizModel>>(emptyList()) }

    Scaffold (
        topBar ={
            AppTollBar(toolbarTitle = "Quiz Section")
        }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color(0xFF101010)
                ),
            contentAlignment = Alignment.Center
        ) {
            LaunchedEffect(Unit) {
                val databaseRef = FirebaseDatabase.getInstance().reference
                val snapshot = databaseRef.get().await()

                if(snapshot.exists()){
                    val list = snapshot.children.mapNotNull { data ->
                        data.getValue(QuizModel::class.java)
                    }
                    quizModelList.value = list
                }
            }

            val distinctCategories = quizModelList.value
                .map { it.category }
                .distinct()

            val questions = quizModelList.value
                .shuffled()
                .map { it.question }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(top = 110.dp)
            ) {
                items(distinctCategories){ category ->
                    QuizCard(category = category, navController = navController)
                }
            }
        }
    }
}

@Composable
fun QuizCard(category: String, navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .size(80.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color(0xFF101010))
            .clickable {
                navController.navigate("difficultySelection/$category")
            }
            .border(
                width = 1.dp,
                color = Color(0xffff9580),
                shape = RoundedCornerShape(15.dp)
            ),
    ) {
        Text(
            text = category,
            fontSize = 25.sp,
            color = Color(0xffff9580),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterStart).padding(start = 20.dp),
        )


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTollBar(toolbarTitle: String){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(100.dp),

    ) {
        TopAppBar(
            title = {
                Text(text = toolbarTitle, fontWeight = FontWeight.Bold, color = Color(0xffff9580))
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
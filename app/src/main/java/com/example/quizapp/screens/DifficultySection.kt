package com.example.quizapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quizapp.ui.theme.GradientBackground
import com.example.quizapp.ui.theme.gradientColors2

@Composable
fun ChooseDiff(category:String?,navController: NavController,) {
    val category = category
    val difficulty1 = "easy"
    val difficulty2 = "medium"
    val difficulty3 = "hard"

    Box(
        modifier = Modifier.fillMaxSize().background(color = Color(0xFF101010)),
        contentAlignment = Alignment.Center,


    ){
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Text(text = "Difficulty",modifier = Modifier.padding(bottom = 20.dp), fontSize = 60.sp, textAlign = TextAlign.Center, fontWeight = FontWeight(700), color = Color(0xffffffff), fontFamily = androidx.compose.ui.text.font.FontFamily.Serif)
            Column(modifier = Modifier.padding(top = 20.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Button(onClick = {navController.navigate("questions/$difficulty1/$category")},modifier = Modifier.size(350.dp,60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xffff9580),contentColor = Color.Black)
                ) {
                    Text(text = "Easy",modifier = Modifier, textAlign = TextAlign.Center, fontSize = 20.sp)
                }
                Button(onClick = {navController.navigate("questions/$difficulty2/$category")},modifier = Modifier.size(350.dp,60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xffff9580),contentColor = Color.Black)
                    ) {
                    Text(text = "Medium", textAlign = TextAlign.Center, fontSize = 20.sp)
                }
                Button(onClick = {navController.navigate("questions/$difficulty3/$category")},modifier = Modifier.size(350.dp,60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xffff9580),contentColor = Color.Black)
                    ) {
                    Text(text = "Hard", textAlign = TextAlign.Center, fontSize = 20.sp)
                }
            }
        }
    }
}
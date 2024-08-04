package com.example.quizapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val gradientColors = listOf(
    Color(0xFFaec8e1),
    Color(0xFF91afd0),
    Color(0xFF7298c4),
    Color(0xFF5c7dbf),
)

val gradientColors2 = listOf(
    Color(0xFFaec8e1),
    Color(0xFF91afd0),
    Color(0xFF7298c4),
    Color(0xFF5c7dbf),
)

@Composable
fun GradientBackground(isVertical: Boolean,color: List<Color>): Brush {

    val endOffset = if(isVertical){
        Offset(0f, Float.POSITIVE_INFINITY)
    }
    else{
        Offset(Float.POSITIVE_INFINITY, 0f)
    }

    return Brush.linearGradient(
        colors = color,
        start = Offset.Zero,
        end = endOffset
    )

}
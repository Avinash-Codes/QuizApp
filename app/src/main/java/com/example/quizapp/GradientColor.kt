package com.example.quizapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val gradientColors = listOf(
    Color(0xFFaa4b6b),
    Color(0xFF6b6b83),
    Color(0xFF33b8d99),
)

val gradientColors2 = listOf(
    Color(0xFFFFEFBA),
    Color(0xFFFFFFFF),
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
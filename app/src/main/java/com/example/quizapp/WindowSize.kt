package com.example.quizapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration

data class WindowSize(val width:WindowType, val height:WindowType) {
}

enum class WindowType {
    SMALL,
    MEDIUM,
    LARGE
}

@Composable
fun rememberWindowSize():WindowSize {
    val configuration = LocalConfiguration.current

    return WindowSize(
        width = when {
            configuration.screenWidthDp < 400 -> WindowType.SMALL
            configuration.screenWidthDp < 800 -> WindowType.MEDIUM
            else -> WindowType.LARGE
        },
        height =  when {
            configuration.screenHeightDp < 480 -> WindowType.SMALL
            configuration.screenHeightDp < 900 -> WindowType.MEDIUM
            else -> WindowType.LARGE
        }
    )
}


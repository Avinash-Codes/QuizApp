    package com.example.quizapp

    import androidx.compose.animation.Crossfade
    import androidx.compose.animation.core.animateFloatAsState
    import androidx.compose.animation.core.tween
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.size
    import androidx.compose.foundation.shape.CircleShape
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.LaunchedEffect
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.saveable.rememberSaveable
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.draw.rotate
    import androidx.compose.ui.draw.scale
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.unit.dp
    import androidx.navigation.NavController
    import kotlinx.coroutines.delay
    import com.example.quizapp.SignUpPage

    @Composable
    fun AppContent(state: SignInState,onGoogleSignIn: () -> Unit ){
        var showSplashScreen by rememberSaveable { mutableStateOf(true) }

        LaunchedEffect(key1 = showSplashScreen) {
            delay(2000)
            showSplashScreen = false
        }

        Crossfade(targetState = showSplashScreen) { isSplashScreenVisible ->
            if (isSplashScreenVisible) {
                SplashScreen {
                    showSplashScreen = false
                }
            } else {
                SignUpPage(state = state, onGoogleSignIn = onGoogleSignIn)
            }

        }
    }

        @Composable
        fun SplashScreen(navigateToSignUp: () -> Unit){
            LaunchedEffect(key1 = true) {
                delay(2000)
                navigateToSignUp()

            }
            // Splash screen UI with transitions
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GradientBackground(isVertical = true, color = gradientColors)),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = R.drawable.hdlogo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(300.dp)
                        .clip(CircleShape)
                )
            }


        }

    package com.example.quizapp.screens

    import androidx.compose.animation.Crossfade
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
    import androidx.compose.runtime.saveable.rememberSaveable
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.unit.dp
    import androidx.navigation.NavHostController
    import androidx.navigation.NavType
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.rememberNavController
    import androidx.navigation.navArgument
    import com.example.quizapp.R
    import com.example.quizapp.presentation.sign_in.SignInState
    import kotlinx.coroutines.delay
    import com.example.quizapp.ui.theme.GradientBackground
    import com.example.quizapp.ui.theme.gradientColors

    @Composable
    fun AppContent(state: SignInState, onGoogleSignIn: () -> Unit ){
        var showSplashScreen by rememberSaveable { mutableStateOf(true) }
        val navController = rememberNavController()


        LaunchedEffect(key1 = showSplashScreen) {
            delay(2000)
            showSplashScreen = false
        }

//        Crossfade(targetState = showSplashScreen) { isSplashScreenVisible ->
//            if (isSplashScreenVisible) {
//                SplashScreen {
//                    showSplashScreen = false
//                }
//            } else {
////                SignUpPage(state = state, onGoogleSignIn = onGoogleSignIn)
//                navController.navigate("quizSection")
//
//            }
//
//        }
        Navigation(navController = navController, showSplashScreen = showSplashScreen)
    }

    @Composable
    fun Navigation(navController: NavHostController, showSplashScreen: Boolean) {
        NavHost(navController = navController, startDestination = "splash") {
            composable("splash") {
                SplashScreen {
                    navController.navigate("quizSection") // Navigate inside NavHost
                }
            }
            composable("quizSection") { QuizSectionUi(navController) }


            composable(route = "difficultySelection/{category}",
                arguments = listOf(navArgument("category") {
                    type = NavType.StringType })
            ){ backStackEntry ->
                ChooseDiff(category = backStackEntry.arguments?.getString("category") ?: "", navController = navController)
            }

            composable(route = "questions/{difficulty}/{category}",
                arguments = listOf(navArgument("difficulty") {
                    type = NavType.StringType },
                    navArgument("category") {
                        type = NavType.StringType })
            ){ backStackEntry ->
                Questions(difficulty = backStackEntry.arguments?.getString("difficulty") ?: "", navController = navController, category = backStackEntry.arguments?.getString("category") ?: "")
            }

            composable(route = "result/{score}/{category}/{difficulty}",
                arguments = listOf(
                    navArgument("score"){
                    type = NavType.IntType
                },
                    navArgument("category"){
                        type = NavType.StringType
                    },
                    navArgument("difficulty"){
                        type = NavType.StringType
                    }
                )
            ){ backStackEntry ->
                val score = backStackEntry.arguments?.getInt("score") ?: 0
                val category = backStackEntry.arguments?.getString("category")
                val difficulty = backStackEntry.arguments?.getString("difficulty")

                Result(navController = navController, score = score, category = category, difficulty = difficulty)
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

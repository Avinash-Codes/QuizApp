package com.example.quizapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quizapp.models.QuizModel
import com.example.quizapp.presentation.sign_in.GoogleAuthUiClient
import com.example.quizapp.presentation.sign_in.SignInState
import com.example.quizapp.screens.AppContent
import com.example.quizapp.screens.ChooseDiff
import com.example.quizapp.screens.Questions
import com.example.quizapp.screens.QuizSectionUi
import com.example.quizapp.screens.Result
import com.example.quizapp.viewmodel.SignInVIewModel
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
         GoogleAuthUiClient(
             context = applicationContext,
             oneTapClient = Identity.getSignInClient(applicationContext)
         )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModels = ViewModelProvider(this)[SignInVIewModel::class.java]
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "sign_up" ) {
                composable("sign_up"){
                    val viewModel = viewModel<SignInVIewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle(initialValue = SignInState())

                    LaunchedEffect(key1 = Unit) {
                            if(googleAuthUiClient.getSignedInUser() != null){
                                navController.navigate("quizSection")
                            }
                    }
                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartIntentSenderForResult(),
                        onResult = { result ->
                            if(result.resultCode == RESULT_OK){
                                lifecycleScope.launch {
                                    val signInResult = googleAuthUiClient.SignInWithIntent(
                                        intent = result.data ?: return@launch
                                    )
                                    viewModel.onSignInResult(signInResult)
                                }
                            }
                        }
                    )

                    LaunchedEffect(key1 = state.isSignInSuccessful) {
                        if(state.isSignInSuccessful){
                            Toast.makeText(
                                applicationContext,
                                "Sign in successful",
                                Toast.LENGTH_LONG
                            ).show()
                            navController.navigate("quizSection")
                            viewModel.resetState()
                        }
                    }

                    AppContent(
                        state = state,
                        onGoogleSignIn = {
                            lifecycleScope.launch {
                                val signInIntentSender = googleAuthUiClient.signIn()
                                    launcher.launch(
                                        IntentSenderRequest.Builder(
                                            signInIntentSender ?: return@launch
                                        ).build()
                                    )
                            }
                        }
                    )
                }

                composable(route = "quizSection"){
                    QuizSectionUi(navController = navController)
                }

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
                    arguments = listOf(navArgument("score"){
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
    }
}

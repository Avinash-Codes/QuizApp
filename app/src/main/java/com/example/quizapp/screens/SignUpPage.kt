package com.example.quizapp.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.R
import com.example.quizapp.presentation.sign_in.SignInState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpPage(state: SignInState, onGoogleSignIn :() -> Unit){

    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    var fname by rememberSaveable { mutableStateOf("") }
    var lname by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var pass by rememberSaveable { mutableStateOf("") }

    var passVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF000000)),
    ){
        Column(modifier = Modifier
            .fillMaxHeight()
            .size(413.dp)
            .padding(start = 19.dp, end = 19.dp),
        )
        {
            Text(text = "Sign Up Account", modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 50.dp, bottom = 56.dp)
                ,fontSize = 28.sp
                , fontWeight = FontWeight.Medium
                , color = Color(0xFFFFFFFF)
            )
            Row (modifier = Modifier.align(Alignment.CenterHorizontally)){
                Button(onClick = onGoogleSignIn,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF000000)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .size(165.dp, 50.dp)
                        .border(
                            width = 2.dp, color = Color(0x40B0ADAC),
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.googlenew),
                            contentDescription = "Google",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Unspecified
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(text = "Google")
                    }

                }
                Spacer(modifier = Modifier.width(13.dp))
                Button(onClick =  {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF000000)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .size(165.dp, 50.dp)
                        .border(
                            width = 2.dp,
                            color = Color(0x40B0ADAC),
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {

                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.facebook),
                            contentDescription = "Facebook",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Unspecified
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(text = "Facebook")
                    }
                }
            }

            Spacer(modifier = Modifier.height(36.dp))
            Row (modifier = Modifier){
                Column(modifier = Modifier
                    .size(165.dp, 1.dp)
                    .align(Alignment.CenterVertically)){
                    Divider(modifier = Modifier,thickness = 1.dp,)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Or",fontSize = 16.sp, color = Color(0xFFB0ADAC), fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(10.dp))
                Divider(modifier = Modifier.align(Alignment.CenterVertically),thickness = 1.dp,)
            }

            Spacer(modifier = Modifier.height(36.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(text = "First Name", fontSize = 16.sp, modifier = Modifier.padding(bottom = 10.dp), color = Color(0xFFFFFFFF), fontWeight = FontWeight.Bold)
                    TextField(value = fname, onValueChange = {fname = it} , modifier = Modifier.size(175.dp, 55.dp), placeholder = {Text(text = "eg. Avinash", color = Color(0xFF969696))},
                        colors = TextFieldDefaults.textFieldColors(containerColor = Color(0xFF1a1a1a ), focusedIndicatorColor = Color.Transparent,unfocusedIndicatorColor = Color.Transparent, focusedTextColor = Color(0xFF8b8b8b),unfocusedTextColor = Color(0xFF8b8b8b)), shape = RoundedCornerShape(10.dp),
                        textStyle = TextStyle(fontSize = 18.sp)
                    )
                }

                Spacer(modifier = Modifier.width(13.dp))

                Column {
                    Text(text = "Last Name", fontSize = 16.sp, modifier = Modifier.padding(bottom = 10.dp), color = Color(0xFFFFFFFF), fontWeight = FontWeight.Bold)
                    TextField(value = lname, onValueChange = {lname = it} , modifier = Modifier.size(175.dp, 55.dp), placeholder = {Text(text = "eg. Sharma", color = Color(0xFF969696))},
                        colors = TextFieldDefaults.textFieldColors(containerColor = Color(0xFF1a1a1a ), focusedIndicatorColor = Color.Transparent,unfocusedIndicatorColor = Color.Transparent, focusedTextColor = Color(0xFF8b8b8b) ,unfocusedTextColor = Color(0xFF8b8b8b)), shape = RoundedCornerShape(10.dp),
                        textStyle = TextStyle(fontSize = 18.sp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(13.dp))

            Column {
                Text(text = "Email", fontSize = 16.sp, modifier = Modifier.padding(bottom = 10.dp), color = Color(0xFFFFFFFF), fontWeight = FontWeight.Bold)
                TextField(value = email, onValueChange = {email = it} , modifier = Modifier.fillMaxWidth(), placeholder = {Text(text = "eg. avinash07@gmail.com", color = Color(0xFF969696))},
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color(0xFF1a1a1a ), focusedIndicatorColor = Color.Transparent,unfocusedIndicatorColor = Color.Transparent, focusedTextColor = Color(0xFF8b8b8b),unfocusedTextColor = Color(0xFF8b8b8b)), shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(fontSize = 18.sp)
                )
            }
            Spacer(modifier = Modifier.height(13.dp))

            Column {
                Text(text = "Password", fontSize = 16.sp, modifier = Modifier.padding(bottom = 10.dp), color = Color(0xFFFFFFFF), fontWeight = FontWeight.Bold)
                TextField(value = pass, onValueChange = {pass = it} , modifier = Modifier.fillMaxWidth(), placeholder = {Text(text = "Enter your password", color = Color(0xFF969696))},
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color(0xFF1a1a1a ), focusedIndicatorColor = Color.Transparent,unfocusedIndicatorColor = Color.Transparent, focusedTextColor = Color(0xFF8b8b8b),unfocusedTextColor = Color(0xFF8b8b8b)), shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(fontSize = 18.sp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val imageIcon = if(passVisible){
                            Icons.Filled.Add
                        }
                        else{
                            Icons.Filled.Add
                        }

                        val description = if(passVisible){
                            "Hide Password"
                        }
                        else{
                            "Show Password"
                        }

                        IconButton(onClick = {passVisible = !passVisible}) {
                            Icon(imageVector = imageIcon, contentDescription = description)
                        }
                    },

                    visualTransformation = if(passVisible) VisualTransformation.None
                    else PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(13.dp))
                Text(text = "Must be at least 8 characters", color = Color(0xFF969696), fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(29.dp))

                Button(onClick = {} , modifier = Modifier
                    .fillMaxWidth()
                    .size(175.dp, 55.dp), colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFFFFF)), shape = RoundedCornerShape(10.dp),
                ) {
                    Text(text = "Sign Up", modifier = Modifier
                        .fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp ,
                        color = Color(0xFF000000))

                }

                Spacer(modifier = Modifier.height(26.dp))
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text(text = "Already have an account?", modifier = Modifier,
                        color = Color(0xFFB0ADAC),
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "Log In", modifier = Modifier
                        .padding(start = 2.dp)
                        .clickable { }
                        , color = Color(0xFFFFFFFF),
                        fontWeight = FontWeight.Bold,
                    )

                }
            }
        }
    }
}
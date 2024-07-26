package com.example.quizapp

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage() {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    var passVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF000000)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .size(413.dp)
                .padding(start = 19.dp, end = 19.dp),
        )
        {
            Text(
                text = "Login Account",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 50.dp, bottom = 56.dp),
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFFFFFFF)
            )

            Spacer(modifier = Modifier.height(13.dp))

            Image(painter = painterResource(id = R.drawable.hdlogo),
                contentDescription = "logo",
                modifier = Modifier.size(250.dp,250.dp).align(Alignment.CenterHorizontally)
                )

            Column {
                Text(
                    text = "Email",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 10.dp),
                    color = Color(0xFFFFFFFF),
                    fontWeight = FontWeight.Bold
                )
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = "eg. avinash07@gmail.com",
                            color = Color(0xFF969696)
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFF1a1a1a),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color(0xFF8b8b8b),
                        unfocusedTextColor = Color(0xFF8b8b8b)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(fontSize = 18.sp)
                )
            }
            Spacer(modifier = Modifier.height(13.dp))

            Column {
                Text(
                    text = "Password",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 10.dp),
                    color = Color(0xFFFFFFFF),
                    fontWeight = FontWeight.Bold
                )
                TextField(
                    value = pass,
                    onValueChange = { pass = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "Enter your password", color = Color(0xFF969696)) },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFF1a1a1a),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color(0xFF8b8b8b),
                        unfocusedTextColor = Color(0xFF8b8b8b)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(fontSize = 18.sp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val imageIcon = if (passVisible) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        }

                        val description = if (passVisible) {
                            "Hide Password"
                        } else {
                            "Show Password"
                        }

                        IconButton(onClick = { passVisible = !passVisible }) {
                            Icon(imageVector = imageIcon, contentDescription = description)
                        }
                    },

                    visualTransformation = if (passVisible) VisualTransformation.None
                    else PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(13.dp))
                Text(
                    text = "Must be at least 8 characters",
                    color = Color(0xFF969696),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(29.dp))

                Button(onClick = {} , modifier = Modifier
                    .fillMaxWidth()
                    .size(175.dp, 55.dp), colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFFFFF)), shape = RoundedCornerShape(10.dp),
                ) {
                    Text(text = "Login ", modifier = Modifier
                        .fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp ,
                        color = Color(0xFF000000))

                }

            }
        }
    }
}
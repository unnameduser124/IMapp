package com.example.imapp.loginAndRegisterUI

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.imapp.MainActivity
import com.example.imapp.MainUser
import com.example.imapp.messageAPI.StatusCode
import com.example.imapp.messageAPI.dataClasses.UserAPIData
import com.example.imapp.messageAPI.services.UserAPIService
import kotlin.concurrent.thread

class RegisterLayout : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GenerateLayout()
        }
    }

    @Composable
    fun GenerateLayout() {
        val username = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val confirmPassword = remember { mutableStateOf("") }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            TextFieldPlusLabel(username, "Username")
            TextFieldPlusLabel(
                password,
                "Password",
                PasswordVisualTransformation(),
                KeyboardType.Password
            )
            TextFieldPlusLabel(
                confirmPassword,
                "Confirm Password",
                PasswordVisualTransformation(),
                KeyboardType.Password
            )
            RegisterButton(username, password, confirmPassword)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TextFieldPlusLabel(
        username: MutableState<String>,
        label: String,
        visualTransformation: VisualTransformation = VisualTransformation.None,
        keyboardType: KeyboardType = KeyboardType.Text
    ) {
        Text(
            text = label,
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp, bottom = 5.dp, top = 20.dp)
                .fillMaxWidth(),
            fontSize = 15.sp
        )
        OutlinedTextField(
            value = username.value, onValueChange = { username.value = it },
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp, bottom = 20.dp, top = 5.dp)
                .fillMaxWidth(),
            label = { Text(label) },
            textStyle = TextStyle.Default.copy(fontSize = 20.sp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = visualTransformation
        )
    }


    @Composable
    fun RegisterButton(
        username: MutableState<String>,
        password: MutableState<String>,
        confirmPassword: MutableState<String>
    ) {
        Button(
            onClick = {
                if (password.value == confirmPassword.value && password.value != "" && username.value != "") {
                    MainUser.username = username.value
                    val salt = MainUser.generateSalt()
                    val passwordHash = MainUser.hashPassword(password.value, salt)
                    thread {
                        val response = UserAPIService().createUser(
                            UserAPIData(
                                username.value,
                                salt,
                                "",
                                passwordHash
                            )
                        )
                        if (response.first == StatusCode.Created) {
                            if(UserAPIService().getUser(response.second) == StatusCode.OK) {
                                val intent = Intent(this@RegisterLayout, MainActivity::class.java)
                                this@RegisterLayout.startActivity(intent)
                            }
                            else{
                                Toast.makeText(
                                    this@RegisterLayout,
                                    "Something went wrong",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                this@RegisterLayout,
                                "Something went wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(
                        this@RegisterLayout,
                        "Invalid credentials!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp, bottom = 20.dp, top = 5.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Register", fontSize = 20.sp, color = Color.Black)
        }
    }
    override fun onBackPressed() {

    }
}

@Preview
@Composable
fun PreviewRegisterLayout() {
    RegisterLayout().GenerateLayout()
}
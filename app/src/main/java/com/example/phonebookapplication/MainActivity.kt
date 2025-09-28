package com.example.phonebookapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.example.phonebookapplication.data.remote.RetrofitBuilder
import com.example.phonebookapplication.data.repository.UserRepository
import com.example.phonebookapplication.ui.screens.home.HomeScreen
import com.example.phonebookapplication.ui.screens.home.HomeViewModel
import com.example.phonebookapplication.ui.theme.AppBackgroundColor
import com.example.phonebookapplication.ui.theme.PhoneBookApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhoneBookApplicationTheme {
                Surface(
                    color = AppBackgroundColor
                ) {
                    //SuccessScreen()
                    HomeScreen(HomeViewModel(UserRepository(RetrofitBuilder.apiService)))
                }
            }
        }
    }
}
package com.example.devble

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.devble.ui.FocusScreen
import com.example.devble.viewmodel.FocusViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    private val viewModel: FocusViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FocusScreen(viewModel = viewModel)
        }
    }
}
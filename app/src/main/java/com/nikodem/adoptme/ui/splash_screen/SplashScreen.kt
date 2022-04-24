package com.nikodem.adoptme.ui.splash_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nikodem.adoptme.ui.theme.BFS25BOLD

@Composable
fun SplashScreen(
    viewModel: SplashScreenFragmentViewModel
) {
    Scaffold(
        modifier = Modifier.padding(all = 10.dp)
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Splash Screen", style = MaterialTheme.typography.BFS25BOLD)
        }
    }
}
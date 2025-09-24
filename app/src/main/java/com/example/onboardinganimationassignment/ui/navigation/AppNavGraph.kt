package com.example.onboardinganimationassignment.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.onboardinganimationassignment.ui.onboarding.OnboardingScreenUi

@Composable
fun AppNavGraph(start: String = "onboarding") {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = start) {
        composable("onboarding") {
            OnboardingScreenUi(onFinish = {
                navController.navigate("landing") {
                    popUpTo("onboarding") { inclusive = true }
                }
            })
        }
        composable("landing") {
            androidx.compose.foundation.layout.Box(modifier = androidx.compose.ui.Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                androidx.compose.material3.Text("Landing page", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
            }
        }
    }
}

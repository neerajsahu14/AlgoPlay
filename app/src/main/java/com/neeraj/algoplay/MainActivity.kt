package com.neeraj.algoplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.neeraj.algoplay.core.theme.AlgoPlayTheme
import com.neeraj.algoplay.feature.sudoku.domain.model.Difficulty
import com.neeraj.algoplay.feature.sudoku.presentation.screen.SudokuHomeScreen
import com.neeraj.algoplay.feature.sudoku.presentation.screen.SudokuScreen
import com.neeraj.algoplay.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            AlgoPlayTheme {
                AppRoot(windowSizeClass)
            }
        }
    }
}

@Composable
fun AppRoot(windowSizeClass: androidx.compose.material3.windowsizeclass.WindowSizeClass) {
    val navController = rememberNavController()
    var selectedDifficulty by rememberSaveable { mutableStateOf(Difficulty.MEDIUM) }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen { algorithm ->
                algorithm.route?.let { navController.navigate(it) }
            }
        }
        composable("sudoku_home") {
            SudokuHomeScreen(
                windowSizeClass = windowSizeClass,
                selectedDifficulty = selectedDifficulty,
                onDifficultyChange = { selectedDifficulty = it },
                onStartGame = {
                    navController.navigate("game/${selectedDifficulty.name}/${System.currentTimeMillis()}")
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = "game/{difficulty}/{seed}",
            arguments = listOf(
                navArgument("difficulty") { type = NavType.StringType },
                navArgument("seed") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val difficultyName = backStackEntry.arguments?.getString("difficulty") ?: Difficulty.MEDIUM.name
            val seed = backStackEntry.arguments?.getLong("seed") ?: 0L
            SudokuScreen(
                windowSizeClass = windowSizeClass,
                initialDifficulty = Difficulty.valueOf(difficultyName),
                gameSeed = seed,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

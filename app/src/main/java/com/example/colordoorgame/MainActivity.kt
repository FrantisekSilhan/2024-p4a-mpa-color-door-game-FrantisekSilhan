package com.example.colordoorgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.colordoorgame.ui.theme.ColordoorgameTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    private val gameVM = GameViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ColordoorgameTheme {
                GameScreen(gameVM)
            }
        }
    }
}

@Composable
fun GameScreen(viewModel: GameViewModel) {
    val uiState = viewModel.uiState.collectAsState().value
    val backgroundColor = remember { mutableStateOf(randomColor()) }
    val doorColors = remember { List(2) { mutableStateOf(randomColor()) } }

    if (uiState.gameOver) {
        GameOverScreen(viewModel)
    } else {
        Box(modifier = Modifier.clickable { backgroundColor.value = randomColor(); doorColors.forEach { it.value = randomColor() } }.fillMaxSize().background(backgroundColor.value)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Remaining Health: ${uiState.health}")
                Text("Score: ${uiState.score}")
                Text(uiState.message)
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Door(onClick = { backgroundColor.value = randomColor(); doorColors.forEach { it.value = randomColor() }; viewModel.onDoorClicked() }, color = doorColors[0].value)
                    Spacer(modifier = Modifier.width(32.dp))
                    Door(onClick = { backgroundColor.value = randomColor(); doorColors.forEach { it.value = randomColor() }; viewModel.onDoorClicked() }, color = doorColors[1].value)
                }
            }
        }
    }
}

@Composable
fun GameOverScreen(viewModel: GameViewModel) {
    val uiState = viewModel.uiState.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Game Over")
        Text("Final score: ${uiState.score}")
        Text("High score: ${uiState.highScore}")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = viewModel::resetGame) {
            Text("Reset Game")
        }
    }
}

@Composable
fun Door(color: Color, onClick: () -> Unit) {
    Box(modifier = Modifier.clickable { onClick() }.size(100.dp), contentAlignment = Alignment.Center) {
        Box(modifier = Modifier.fillMaxSize().background(color))
    }
}

fun randomColor(): Color {
    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)
    return Color(red, green, blue)
}
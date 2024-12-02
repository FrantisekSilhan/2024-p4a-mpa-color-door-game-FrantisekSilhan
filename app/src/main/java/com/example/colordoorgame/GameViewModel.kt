package com.example.colordoorgame

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameState())
    val uiState: StateFlow<GameState> = _uiState

    fun onDoorClicked() {
        when (Random.nextInt(3)) {
            0 -> _uiState.update { it.copy(message = "You went through the door and found nothing.") }
            1 -> {
                val reward = Random.nextInt(0, 100)
                _uiState.update { it.copy(score = it.score + reward, message = "You found a chest and got $reward points.") }
            }
            else -> _uiState.update { it.copy(health = it.health - 1, message = "You found a monster and lost 1 health point.") }
        }

        if (_uiState.value.highScore < _uiState.value.score) _uiState.update { it.copy(highScore = it.score) }

        if (_uiState.value.health <= 0) _uiState.update { it.copy(gameOver = true) }
    }

    fun resetGame() {
        _uiState.update { it.copy(health = GameState().health, score = GameState().score, message = GameState().message, gameOver = GameState().gameOver) }
    }
}
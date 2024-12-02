package com.example.colordoorgame

data class GameState(
    val health: Int = 3,
    val score: Int = 0,
    val message: String = "Click on a door to continue",
    val gameOver: Boolean = false,
    val highScore: Int = 0
)

package com.example.sach.game

data class Settings(
    val rotateBoard: Boolean,
    val rotatePieces: Boolean,
    val timerEnabled: Boolean,
    val whiteTimeMinutes: Int,
    val blackTimeMinutes: Int
)

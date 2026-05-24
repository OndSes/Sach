package com.example.sach.history

data class GameEntity(
    val gameId: String,
    val gameType: String,
    val winner: String,
    val date: Long,
    val moveCount: Int
)

package com.example.sach.history

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameEntity(
    @PrimaryKey
    val gameId: String,

    val gameType: String,
    val winner: String,
    val date: Long,
    val moveCount: Int
)

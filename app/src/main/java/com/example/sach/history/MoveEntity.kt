package com.example.sach.history

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoveEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val gameId: String,
    val moveNumber: Int,
    val moveData: String
)
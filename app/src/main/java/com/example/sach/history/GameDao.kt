package com.example.sach.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface GameDao {

    @Insert
    suspend fun insertGame(game: GameEntity)

    @Insert
    suspend fun insertMoves(moves: List<MoveEntity>)

    @Query("SELECT * FROM GameEntity")
    suspend fun getAllGames(): List<GameEntity>

    @Query("SELECT * FROM MoveEntity WHERE gameId = :gameId ORDER BY moveNumber")
    suspend fun getMovesForGame(gameId: String): List<MoveEntity>
}
package com.example.sach.history.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameDao {
    @Insert
    suspend fun insertGame(game: GameEntity)

    @Insert
    suspend fun insertMoves(moves: List<MoveEntity>)

    @Query("SELECT * FROM GameEntity ORDER BY date DESC")
    suspend fun getAllGames(): List<GameEntity>

    @Query("SELECT * FROM MoveEntity WHERE gameId = :gameId ORDER BY moveNumber")
    suspend fun getMovesForGame(gameId: String): List<MoveEntity>

    @Query("DELETE FROM GameEntity WHERE gameId = :gameId")
    suspend fun deleteGame(gameId: String)

    @Query("DELETE FROM MoveEntity WHERE gameId = :gameId")
    suspend fun deleteMoves(gameId: String)
}
package com.example.sach.history.database

import com.example.sach.history.database.MoveEntity

class GameRepository(private val gameDao: GameDao) {

    suspend fun insertGame(game: GameEntity) {
        gameDao.insertGame(game)
    }

    suspend fun insertMoves(moves: List<MoveEntity>) {
        gameDao.insertMoves(moves)
    }

    suspend fun getAllGames(): List<GameEntity> {
        return gameDao.getAllGames()
    }

    suspend fun getMovesForGame(gameId: String): List<MoveEntity> {
        return gameDao.getMovesForGame(gameId)
    }

    suspend fun deleteGame(gameId: String) {
        gameDao.deleteMoves(gameId)
        gameDao.deleteGame(gameId)
    }
}
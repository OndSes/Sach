package com.example.sach.history

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
}
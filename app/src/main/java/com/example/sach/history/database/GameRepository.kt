package com.example.sach.history.database

/**
 * slúži na komunikáciu a úpravu databázy
 */
class GameRepository(private val gameDao: GameDao) {

    /**
     * vloží hru do databázy
     */
    suspend fun insertGame(game: GameEntity) {
        gameDao.insertGame(game)
    }

    /**
     * vloží ťahy do databázy
     */
    suspend fun insertMoves(moves: List<MoveEntity>) {
        gameDao.insertMoves(moves)
    }

    /**
     * vráti všetky hry
     */
    suspend fun getAllGames(): List<GameEntity> {
        return gameDao.getAllGames()
    }

    /**
     * vráti ťahy pre konkrétnu hru
     */
    suspend fun getMovesForGame(gameId: String): List<MoveEntity> {
        return gameDao.getMovesForGame(gameId)
    }

    /**
     * odstráni hru z databázy
     */
    suspend fun deleteGame(gameId: String) {
        gameDao.deleteMoves(gameId)
        gameDao.deleteGame(gameId)
    }
}
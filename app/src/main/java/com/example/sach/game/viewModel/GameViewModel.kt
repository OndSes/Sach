package com.example.sach.game.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sach.game.Settings
import com.example.sach.game.games.Checkers
import com.example.sach.game.games.Chess
import com.example.sach.game.games.Game
import com.example.sach.history.database.AppDatabase
import com.example.sach.history.database.GameEntity
import com.example.sach.history.database.GameRepository
import com.example.sach.history.database.MoveEntity
import kotlinx.coroutines.launch

/**
 * ViewModel, ktorý ucháva stav hry a komunikuje s databázov
 */
class GameViewModel(application: Application) : AndroidViewModel(application) {
    var game: Game? = null
    private val repository: GameRepository

    init {
        val database = AppDatabase.getDatabase(application)

        repository = GameRepository(database.gameDao())
    }
    fun createGame(gameType: String, settings: Settings) {
        if (game != null) return

        game = when (gameType) {
            "chess" -> Chess(settings)
            else -> Checkers(settings)
        }
    }

    /**
     * uloží hrú do databázy
     */
    fun saveGame() {
        val gameType = if (game is Chess) {"chess"} else {"checkers"}
        val winner = game!!.turnColor.opposite.toString()
        val date = System.currentTimeMillis()
        val moveCount = game!!.moves.size
        val gameEntity = GameEntity(game!!.gameId, gameType, winner, date, moveCount)

        val moveEntities = game!!.moves.mapIndexed { index, move ->
            MoveEntity(gameId = game!!.gameId, moveNumber = index + 1, moveData = move)
        }

        viewModelScope.launch {
            repository.insertGame(gameEntity)
            repository.insertMoves(moveEntities)
        }
    }

    /**
     * vymaže hru z databázy
     */
    fun deleteGame(gameId: String) {
        viewModelScope.launch {
            repository.deleteGame(gameId)
        }
    }

    /**
     * získa všetky hry z databázy
     */
    suspend fun getGames(): List<GameEntity> {
        return repository.getAllGames()
    }

    /**
     * získa ťahu z databázy pre danú hru
     */
    suspend fun getMoves(gameId: String): List<MoveEntity> {
        return repository.getMovesForGame(gameId)
    }
}
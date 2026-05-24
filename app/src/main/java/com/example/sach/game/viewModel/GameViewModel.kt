package com.example.sach.game.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sach.game.Settings
import com.example.sach.game.games.Checkers
import com.example.sach.game.games.Chess
import com.example.sach.game.games.Game
import com.example.sach.history.AppDatabase
import com.example.sach.history.GameEntity
import com.example.sach.history.GameRepository
import com.example.sach.history.MoveEntity
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {
    var game: Game? = null
    private val repository: GameRepository

    init {

        val database =
            AppDatabase.getDatabase(application)

        repository =
            GameRepository(database.gameDao())
    }
    fun createGame(
        gameType: String,
        settings: Settings
    ) {
        if (game != null) return

        game =
            when (gameType) {
                "chess" -> Chess(settings)
                else -> Checkers(settings)
            }
    }

    fun saveGame(
    ) {
        val gameType = if (game is Chess) {"chess"} else {"checkers"}
        val winner = game!!.turnColor.opposite.toString()
        val date = System.currentTimeMillis()
        val moveCount = game!!.moves.size
        val gameEntity = GameEntity(game!!.gameId, gameType, winner, date, moveCount)

        val moveEntities = game!!.moves.mapIndexed { index, move ->
            MoveEntity(
                gameId = game!!.gameId,
                moveNumber = index + 1,
                moveData = move
            )
        }
        viewModelScope.launch {

            repository.insertGame(gameEntity)
            repository.insertMoves(moveEntities)
        }
    }
}
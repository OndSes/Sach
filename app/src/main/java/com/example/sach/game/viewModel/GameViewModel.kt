package com.example.sach.game.viewModel

import androidx.lifecycle.ViewModel
import com.example.sach.game.Settings
import com.example.sach.game.games.Checkers
import com.example.sach.game.games.Chess
import com.example.sach.game.games.Game

class GameViewModel : ViewModel() {
    var game: Game? = null

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
}
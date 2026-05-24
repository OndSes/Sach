package com.example.sach.gameActivity.viewModel

import androidx.lifecycle.ViewModel
import com.example.sach.gameActivity.Settings
import com.example.sach.gameActivity.games.Checkers
import com.example.sach.gameActivity.games.Chess
import com.example.sach.gameActivity.games.Game

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
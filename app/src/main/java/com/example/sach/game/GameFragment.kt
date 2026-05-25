package com.example.sach.game

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.sach.R
import com.example.sach.game.ui.BoardRenderer
import com.example.sach.game.viewModel.GameViewModel

class GameFragment : Fragment(R.layout.fragment_game) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val gameType =
            requireArguments().getString("game_type")!!

        val preferences =
            requireContext().getSharedPreferences(
                "settings",
                Context.MODE_PRIVATE
            )

        val settings =
            Settings(
                preferences.getBoolean(
                    "rotate_board",
                    false
                ),

                preferences.getBoolean(
                    "rotate_pieces",
                    false
                ),
                preferences.getBoolean(
                    "timer_enabled",
                    false
                ),
                preferences.getInt(
                    "white_time",
                    10),
                preferences.getInt(
                    "black_time",
                    10
                )
            )

        val viewModel: GameViewModel by viewModels()

        viewModel.createGame(
            gameType,
            settings
        )
        val game = viewModel.game!!
        val renderer =BoardRenderer(
            view.findViewById(R.id.chessBoard),
            requireContext(),
            game,
            view.findViewById(R.id.whiteTimer),
            view.findViewById(R.id.blackTimer)
        )

        renderer.onSaveGameRequested = { viewModel.saveGame() }

        game.onBoardChanged = { renderer.refreshBoard() }
        game.onPieceSelected = { moves ->
            renderer.resetViews()
            renderer.highlightSquare()
            renderer.showLegalMoves(moves)
        }
        game.onPromotionRequested = { pawn -> renderer.requestPromotion(pawn) }
        game.onGameOver = { color, state -> renderer.showGameOverMessage(color, state) }
        game.onTimerChanged = { whiteTime, blackTime -> renderer.updateTimers(whiteTime, blackTime) }

        game.startTimer()
    }
}

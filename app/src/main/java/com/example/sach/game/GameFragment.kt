package com.example.sach.game

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.sach.R
import com.example.sach.game.ui.BoardRenderer
import com.example.sach.game.viewModel.GameViewModel

class GameFragment : Fragment(R.layout.activity_game) {

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
                )
            )

        val viewModel: GameViewModel by viewModels()

        viewModel.createGame(
            gameType,
            settings
        )

        BoardRenderer(
            view.findViewById(R.id.chessBoard),
            requireContext(),
            viewModel.game!!
        )
    }
}
package com.example.sach.gameActivity

import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.sach.R
import com.example.sach.gameActivity.games.Checkers
import com.example.sach.gameActivity.games.Chess
import com.example.sach.gameActivity.games.Game
import com.example.sach.gameActivity.ui.BoardRenderer
import com.example.sach.gameActivity.viewModel.GameViewModel

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val gameType = intent.getStringExtra("game_type")!!

        val preferences =
            getSharedPreferences(
                "settings",
                Context.MODE_PRIVATE
            )

        val settings =
            Settings(
                preferences.getBoolean("rotate_board", false),
                preferences.getBoolean("rotate_pieces", false)
            )

        val viewModel: GameViewModel by viewModels()

        viewModel.createGame(
            gameType,
            settings
        )

        BoardRenderer(
            findViewById(R.id.chessBoard),
            this,
            viewModel.game!!
        )
    }
}
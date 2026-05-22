package com.example.sach.gameActivity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sach.R
import com.example.sach.gameActivity.games.Checkers
import com.example.sach.gameActivity.games.Chess
import com.example.sach.gameActivity.games.Game

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val gameType = intent.getStringExtra("game_type")

        val preferences =
            getSharedPreferences(
                "settings",
                Context.MODE_PRIVATE
            )

        val rotateBoard =
            preferences.getBoolean(
                "rotate_board",
                false
            )

        val rotatePieces =
            preferences.getBoolean(
                "rotate_pieces",
                false
            )

        when (gameType) {

            "chess" -> {
                Chess(findViewById(R.id.chessBoard), this, Settings(rotateBoard, rotatePieces))
            }

            "checkers" -> {
                Checkers(findViewById(R.id.checkersBoard), this, Settings(rotateBoard, rotatePieces))
            }
        }
    }
}
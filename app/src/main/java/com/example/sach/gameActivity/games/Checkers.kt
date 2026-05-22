package com.example.sach.gameActivity.games

import android.content.Context
import android.widget.GridLayout
import com.example.sach.gameActivity.Settings
import com.example.sach.gameActivity.board.Board
import com.example.sach.gameActivity.board.CheckersBoard

class Checkers(boardView: GridLayout, context: Context, settings: Settings): Game(settings) {
    override val board: Board = CheckersBoard(boardView, context, this)

    init {
        activateSquares()
    }
}
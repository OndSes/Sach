package com.example.sach.gameActivity.games

import android.content.Context
import android.widget.GridLayout
import com.example.sach.gameActivity.Settings
import com.example.sach.gameActivity.board.ChessBoard
import com.example.sach.gameActivity.board.StateOfGame
import com.example.sach.gameActivity.pieces.PieceColor

class Chess(boardView: GridLayout, context: Context, settings: Settings): Game(settings) {
    override val board: ChessBoard = ChessBoard(boardView, context, this)

    init {
        activateSquares()
    }

    override fun nextMove() {
        turnColor = turnColor.opposite
        val state: StateOfGame = board.checkState(turnColor)
        if (state == StateOfGame.CHECK_MATE) {
            when (turnColor) {
                PieceColor.WHITE -> showGameOverMessage("Checkmate! Black Wins")
                PieceColor.BLACK -> showGameOverMessage("Checkmate! White Wins")
            }
        } else if (state == StateOfGame.STALEMATE) {
            showGameOverMessage("Draw by stalemate")
        }
        super.nextMove()
    }
}
package com.example.sach.gameActivity.games

import android.content.Context
import android.widget.GridLayout
import com.example.sach.gameActivity.Settings
import com.example.sach.gameActivity.board.Board
import com.example.sach.gameActivity.board.CheckersBoard
import com.example.sach.gameActivity.board.StateOfGame
import com.example.sach.gameActivity.pieces.PieceColor

class Checkers(boardView: GridLayout, context: Context, settings: Settings): Game(settings) {
    override val board: CheckersBoard = CheckersBoard(boardView, context, this)

    init {
        activateSquares()
    }

    override fun nextMove() {
        val wasCaptured = board.isCaptureAvailable
        board.isCaptureAvailable(turnColor)
        if (!wasCaptured || board.lastMovedPiece!!.getCaptures().isEmpty()) {
            turnColor = turnColor.opposite
            board.isCaptureAvailable(turnColor)
        }
        val state: StateOfGame = board.checkState(turnColor)
        if (state == StateOfGame.CHECK_MATE) {
            when (turnColor) {
                PieceColor.WHITE -> showGameOverMessage("Black Wins")
                PieceColor.BLACK -> showGameOverMessage("White Wins")
            }
        }
        super.nextMove()
    }
}
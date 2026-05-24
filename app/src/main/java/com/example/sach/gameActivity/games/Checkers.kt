package com.example.sach.gameActivity.games

import com.example.sach.gameActivity.Settings
import com.example.sach.gameActivity.board.CheckersBoard
import com.example.sach.gameActivity.board.StateOfGame
import com.example.sach.gameActivity.pieces.PieceColor

class Checkers(settings: Settings): Game(settings) {
    init {
        board = CheckersBoard(this)
    }

    override fun nextMove() {
        val chBoard = board as CheckersBoard
        val wasCaptured = chBoard.isCaptureAvailable
        chBoard.isCaptureAvailable(turnColor)
        if (!wasCaptured || chBoard.lastMovedPiece!!.getCaptures().isEmpty()) {
            turnColor = turnColor.opposite
            chBoard.isCaptureAvailable(turnColor)
        }
        val state: StateOfGame = board.checkState(turnColor)
        if (state == StateOfGame.CHECK_MATE) {
            when (turnColor) {
                PieceColor.WHITE -> onGameMessageRequested?.invoke("Black Wins")
                PieceColor.BLACK -> onGameMessageRequested?.invoke("White Wins")
            }
        }
        super.nextMove()
    }
}
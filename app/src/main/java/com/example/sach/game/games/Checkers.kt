package com.example.sach.game.games

import com.example.sach.game.Settings
import com.example.sach.game.board.CheckersBoard
import com.example.sach.game.board.StateOfGame
import com.example.sach.game.pieces.PieceColor

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
            onGameOver?.invoke(turnColor.opposite, state)
        }
        super.nextMove()
    }
}
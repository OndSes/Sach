package com.example.sach.gameActivity.board

import android.content.Context
import android.widget.GridLayout
import com.example.sach.gameActivity.games.Game
import com.example.sach.gameActivity.pieces.Piece
import com.example.sach.gameActivity.pieces.PieceColor
import com.example.sach.gameActivity.pieces.checkers.CheckersPiece

class CheckersBoard(boardView: GridLayout, context: Context, game: Game): Board(boardView, context, game) {
    override val whitePieces: MutableList<Piece> = PieceGenerator.generateWhiteCheckersPieces(this)
    override val blackPieces: MutableList<Piece> = PieceGenerator.generateBlackCheckersPieces(this)
    var isCaptureAvailable: Boolean = false
    var lastMovedPiece: CheckersPiece? = null

    init {
        for (row in 0..7) {
            for (col in 0..7) {
                boardView.addView(squares[row][col].container)
            }
        }
    }

    fun isCaptureAvailable(color: PieceColor) {
        val pieces = if (color == PieceColor.WHITE) {whitePieces} else {blackPieces}

        for (p in pieces) {
            val piece = p as CheckersPiece
            if (!piece.getCaptures().isEmpty()) {
                isCaptureAvailable = true
                return
            }
        }

        isCaptureAvailable = false
    }

    override fun checkState(color: PieceColor): StateOfGame {
        val pieces = if (color == PieceColor.WHITE) {whitePieces} else {blackPieces}

        if (pieces.isEmpty()) {
            return StateOfGame.CHECK_MATE
        }

        for (piece in pieces) {
            if (!piece.getLegalMoves().isEmpty()) {
                return StateOfGame.NOT_MATE
            }
        }

        return StateOfGame.CHECK_MATE
    }
}
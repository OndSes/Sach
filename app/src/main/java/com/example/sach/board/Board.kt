package com.example.sach.board

import android.content.Context
import android.widget.GridLayout
import com.example.sach.Game
import com.example.sach.pieces.King
import com.example.sach.pieces.Piece
import com.example.sach.pieces.PieceGenerator

class Board(boardView: GridLayout, context: Context, game: Game) {
    val whitePieces: MutableList<Piece>
    val blackPieces: MutableList<Piece>

    val squares: Array<Array<Square>> =
        Array(8) { row ->
            Array(8) { col ->
                Square(row, col, context, game)
            }
        }

    init {
        for (row in 0..7) {
            for (col in 0..7) {
                boardView.addView(squares[row][col].container)
            }
        }
        whitePieces = PieceGenerator.generateWhitePieces(this)
        blackPieces = PieceGenerator.generateBlackPieces(this)
    }

    fun getSquare(row: Int, col: Int): Square {
        return squares[row][col]
    }

    fun isValidPosition(row: Int, col: Int): Boolean {
        return row in 0..7 && col in 0..7
    }

    fun activateSquares(whitesTurn: Boolean) {
        for (piece in whitePieces + blackPieces) {
            squares[piece.row][piece.col].isActive = piece.isWhite == whitesTurn
        }
    }

    fun capture(piece: Piece) {
        if (piece.isWhite) {
            whitePieces.remove(piece)
        } else {
            blackPieces.remove(piece)
        }
    }

    fun isSquareInCheck(squareToCheck: Square, whiteCheck: Boolean): Boolean {
        val pieces: MutableList<Piece> = if (whiteCheck) { whitePieces } else { blackPieces }

        for (piece in pieces) {
            for (square in piece.getAttackMoves()) {
                if (squareToCheck == square) {
                    return true
                }
            }
        }

        return false
    }
}
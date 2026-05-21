package com.example.sach.board

import android.R
import android.content.Context
import android.widget.GridLayout
import com.example.sach.Game
import com.example.sach.pieces.King
import com.example.sach.pieces.Piece
import com.example.sach.pieces.PieceColor
import com.example.sach.pieces.PieceGenerator

class Board(boardView: GridLayout,val context: Context, game: Game) {
    val whitePieces: MutableList<Piece>
    val blackPieces: MutableList<Piece>
    var whiteKing: Piece
    var blackKing: Piece

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
        var x = 0
        whitePieces = PieceGenerator.generateWhitePieces(this)
        for (piece in whitePieces) {
            if (piece is King) {
                break
            }
            x++
        }
        whiteKing = whitePieces[x]

        blackPieces = PieceGenerator.generateBlackPieces(this)
        blackKing = blackPieces[x]
    }

    fun getSquare(row: Int, col: Int): Square {
        return squares[row][col]
    }

    fun isValidPosition(row: Int, col: Int): Boolean {
        return row in 0..7 && col in 0..7
    }

    fun activateSquares(color: PieceColor) {
        for (piece in whitePieces + blackPieces) {
            squares[piece.row][piece.col].isActive = piece.color == color
        }
    }

    fun capture(piece: Piece) {
        if (piece.color == PieceColor.WHITE) {
            whitePieces.remove(piece)
        } else {
            blackPieces.remove(piece)
        }
    }

    fun isSquareInCheck(squareToCheck: Square, attackingColor: PieceColor, pieceToSkip: Piece?): Boolean {
        val pieces: MutableList<Piece> = if (attackingColor == PieceColor.WHITE) { whitePieces } else { blackPieces }

        for (piece in pieces) {
            if (piece == pieceToSkip) {
                continue
            }
            for (square in piece.getAttackMoves()) {
                if (squareToCheck == square) {
                    return true
                }
            }
        }

        return false
    }

    fun isKingInCheck(kingColor: PieceColor, pieceToSkip: Piece?) : Boolean{
        val king: Piece = if (kingColor == PieceColor.WHITE) { whiteKing } else { blackKing }

        return isSquareInCheck(squares[king.row][king.col], kingColor.oppostie, pieceToSkip)
    }

    fun checkForMate(color: PieceColor): StateOfGame {
        val pieces: MutableList<Piece> = if (color == PieceColor.WHITE) { whitePieces } else { blackPieces }
        for (piece in pieces) {
            if (!piece.getLegalMoves().isEmpty()) {
                return StateOfGame.NOT_MATE
            }
        }

        if (isKingInCheck(color, null)) {
            return StateOfGame.CHECK_MATE
        }
        return StateOfGame.STALEMATE
    }
}
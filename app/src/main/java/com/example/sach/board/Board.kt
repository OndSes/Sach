package com.example.sach.board

import android.content.Context
import android.widget.GridLayout
import com.example.sach.Game
import com.example.sach.pieces.Piece
import com.example.sach.pieces.PieceGenerator

class Board(boardView: GridLayout, context: Context, game: Game) {
    val whitePieces: MutableList<Piece>
    val blackPieces: MutableList<Piece>
    var whiteKing: Piece
    val blackKing: Piece

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
        whiteKing = whitePieces[9]
        blackPieces = PieceGenerator.generateBlackPieces(this)
        blackKing = blackPieces[9]
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

    fun isSquareInCheck(squareToCheck: Square, whiteIsAttacking: Boolean, pieceToSkip: Piece?): Boolean {
        val pieces: MutableList<Piece> = if (whiteIsAttacking) { whitePieces } else { blackPieces }

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

    fun isKingInCheck(kingIsWhite: Boolean, pieceToSkip: Piece?) : Boolean{
        val king: Piece = if (kingIsWhite) { whiteKing } else { blackKing }

        return isSquareInCheck(squares[king.row][king.col], !kingIsWhite, pieceToSkip)
    }

    fun checkForMate(checkWhite: Boolean): Mate {
        val pieces: MutableList<Piece> = if (checkWhite) { whitePieces } else { blackPieces }
        for (piece in pieces) {
            if (!piece.getLegalMoves().isEmpty()) {
                return Mate.NOT_MATE
            }
        }

        if (isKingInCheck(checkWhite, null)) {
            return Mate.CHECK_MATE
        }
        return Mate.STALEMATE
    }
}
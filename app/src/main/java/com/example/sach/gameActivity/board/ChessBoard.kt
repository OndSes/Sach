package com.example.sach.gameActivity.board

import android.content.Context
import android.widget.GridLayout
import com.example.sach.gameActivity.games.Game
import com.example.sach.gameActivity.pieces.Piece
import com.example.sach.gameActivity.pieces.PieceColor
import com.example.sach.gameActivity.pieces.chess.ChessPiece
import com.example.sach.gameActivity.pieces.chess.King

class ChessBoard(boardView: GridLayout, context: Context, game: Game): Board(boardView, context, game) {
    override val whitePieces: MutableList<Piece>
    override val blackPieces: MutableList<Piece>
    var whiteKing: Piece
    var blackKing: Piece

    init {
        for (row in 0..7) {
            for (col in 0..7) {
                boardView.addView(squares[row][col].container)
            }
        }
        var x = 0
        whitePieces = PieceGenerator.generateWhiteChessPieces(this)
        for (piece in whitePieces) {
            if (piece is King) {
                break
            }
            x++
        }
        whiteKing = whitePieces[x]

        blackPieces = PieceGenerator.generateBlackChessPieces(this)
        blackKing = blackPieces[x]
    }

    fun isSquareInCheck(squareToCheck: Square, attackingColor: PieceColor, pieceToSkip: Piece?): Boolean {
        val pieces: MutableList<Piece> = if (attackingColor == PieceColor.WHITE) { whitePieces } else { blackPieces }

        for (p in pieces) {
            val piece = p as ChessPiece
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

        return isSquareInCheck(king.square, kingColor.opposite, pieceToSkip)
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
package com.example.sach.game.board

import com.example.sach.game.games.Game
import com.example.sach.game.pieces.Piece
import com.example.sach.game.pieces.PieceColor
import com.example.sach.game.pieces.chess.ChessPiece
import com.example.sach.game.pieces.chess.King

class ChessBoard(game: Game): Board(game) {
    override val whitePieces = PieceGenerator.generateWhiteChessPieces(this)

    override val blackPieces = PieceGenerator.generateBlackChessPieces(this)
    var whiteKing: Piece
    var blackKing: Piece

    init {
        var x = 0
        for (piece in whitePieces) {
            if (piece is King) {
                break
            }
            x++
        }
        whiteKing = whitePieces[x]
        blackKing = blackPieces[x]
    }
    fun isSquareInCheck(squareToCheck: BoardSquare, attackingColor: PieceColor, pieceToSkip: Piece?): Boolean {
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

    override fun checkState(color: PieceColor): StateOfGame {
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
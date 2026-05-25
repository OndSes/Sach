package com.example.sach.game.pieces

import com.example.sach.game.board.Board
import com.example.sach.game.board.BoardSquare

/**
 * abstraktná trieda figúrky
 */
abstract class Piece(open val board: Board, val color: PieceColor,var row: Int,var col: Int) {
    abstract val type: PieceType
    val square: BoardSquare
        get() = board.getSquare(row, col)

    /**
     * vráti všetky legáne ťahy figúrky
     */
    abstract fun getLegalMoves(): List<BoardSquare>

    fun squareContainsPiece(square: BoardSquare): Boolean {
        return square.piece != null
    }
    fun squareContainsAlliedPiece(square: BoardSquare): Boolean {
        return squareContainsPiece(square) && square.piece!!.color == this.color
    }
    fun squareContainsEnemyPiece(square: BoardSquare): Boolean {
        return squareContainsPiece(square) && square.piece!!.color != this.color
    }
    open fun move(targetSquare: BoardSquare) {
        recordMove(row, col, targetSquare.row, targetSquare.col)
    }

    /**
     * zapíše ťah
     */
    protected fun recordMove(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int) {
        board.game.recordMove("$fromRow,$fromCol->$toRow,$toCol")
    }
}
package com.example.sach.game.pieces

import com.example.sach.game.board.Board
import com.example.sach.game.board.BoardSquare

abstract class Piece(open val board: Board, val color: PieceColor,var row: Int,var col: Int) {
    abstract val type: PieceType
    val square: BoardSquare
        get() = board.getSquare(row, col)

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
    abstract fun move(targetSquare: BoardSquare)
}
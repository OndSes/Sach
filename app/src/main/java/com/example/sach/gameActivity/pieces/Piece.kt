package com.example.sach.gameActivity.pieces

import com.example.sach.gameActivity.board.Board
import com.example.sach.gameActivity.board.Square

abstract class Piece(open val board: Board, val color: PieceColor,var row: Int,var col: Int) {
    var hasMoved: Boolean = false
    val square: Square
        get() = board.getSquare(row, col)
    abstract fun getResourceId(): Int
    abstract fun getLegalMoves(): Array<Square>

    fun squareContainsPiece(square: Square): Boolean {
        return square.piece != null
    }
    fun squareContainsAlliedPiece(square: Square): Boolean {
        return squareContainsPiece(square) && square.piece!!.color == this.color
    }
    fun squareContainsEnemyPiece(square: Square): Boolean {
        return squareContainsPiece(square) && square.piece!!.color != this.color
    }
    open fun move(targetSquare: Square) {
        hasMoved = true

        if (targetSquare.piece != null) {
            board.capture(targetSquare.piece!!)
        }
        square.piece = null
        targetSquare.piece = this
    }
}
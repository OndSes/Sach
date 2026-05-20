package com.example.sach.pieces

import com.example.sach.board.Board
import com.example.sach.board.Square

abstract class Piece(val board: Board, val isWhite: Boolean, var row: Int, var col: Int) {
    var hasMoved: Boolean = false
    abstract fun getResourceId(): Int
    abstract fun getPossibleMoves(): Array<Square>
    abstract fun getAttackMoves(): Array<Square>

    fun squareContainsPiece(square: Square): Boolean {
        return square.piece != null
    }
    fun squareContainsAlliedPiece(square: Square): Boolean {
        return squareContainsPiece(square) && square.piece!!.isWhite == this.isWhite
    }
    fun squareContainsEnemyPiece(square: Square): Boolean {
        return squareContainsPiece(square) && square.piece!!.isWhite != this.isWhite
    }
    fun move(square: Square) {
        hasMoved = true

        if (square.piece != null) {
            board.capture(square.piece!!)
        }
        board.getSquare(row, col).piece = null
        square.piece = this
    }
}
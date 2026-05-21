package com.example.sach.pieces

import com.example.sach.board.Board
import com.example.sach.board.Square

abstract class Piece(val board: Board, val isWhite: Boolean, var row: Int, var col: Int) {
    var hasMoved: Boolean = false
    abstract fun getResourceId(): Int
    abstract fun getPossibleMoves(): Array<Square>
    abstract fun getAttackMoves(): Array<Square>

    fun getLegalMoves(): Array<Square> {

        val legalMoves = mutableListOf<Square>()

        for (target in getPossibleMoves()) {

            val originalSquare =
                board.getSquare(row, col)

            val capturedPiece = target.piece

            // simulate move
            target.piece = this
            originalSquare.piece = null

            val kingInCheck =
                board.isKingInCheck(isWhite, capturedPiece)

            // undo move
            originalSquare.piece = this
            target.piece = capturedPiece

            if (!kingInCheck) {
                legalMoves.add(target)
            }
        }

        return legalMoves.toTypedArray()
    }
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
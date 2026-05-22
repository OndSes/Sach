package com.example.sach.pieces

import com.example.sach.board.Board
import com.example.sach.board.Square

abstract class Piece(val board: Board, val color: PieceColor, row: Int, col: Int) {
    var hasMoved: Boolean = false
    var square: Square = board.getSquare(row, col)
    abstract fun getResourceId(): Int
    abstract fun getPossibleMoves(): Array<Square>
    abstract fun getAttackMoves(): Array<Square>

    fun getLegalMoves(): Array<Square> {

        val legalMoves = mutableListOf<Square>()

        for (target in getPossibleMoves()) {

            val originalSquare = square

            val capturedPiece = target.piece

            // simulate move
            target.piece = this
            originalSquare.piece = null

            val kingInCheck =
                board.isKingInCheck(color, capturedPiece)

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
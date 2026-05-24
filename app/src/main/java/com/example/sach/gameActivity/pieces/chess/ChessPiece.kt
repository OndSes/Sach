package com.example.sach.gameActivity.pieces.chess

import com.example.sach.gameActivity.board.BoardSquare
import com.example.sach.gameActivity.board.ChessBoard
import com.example.sach.gameActivity.pieces.Piece
import com.example.sach.gameActivity.pieces.PieceColor
import kotlin.Int

abstract class ChessPiece(override val board: ChessBoard, color: PieceColor, row: Int, col: Int): Piece(board, color, row, col) {
    var hasMoved: Boolean = false
    abstract fun getPossibleMoves(): List<BoardSquare>
    abstract fun getAttackMoves(): List<BoardSquare>

    override fun getLegalMoves(): List<BoardSquare> {
        val legalMoves = mutableListOf<BoardSquare>()

        for (target in getPossibleMoves()) {

            val originalSquare = square

            val capturedPiece = target.piece

            target.piece = this
            originalSquare.piece = null

            val kingInCheck =
                board.isKingInCheck(color, capturedPiece)

            originalSquare.piece = this
            target.piece = capturedPiece

            if (!kingInCheck) {
                legalMoves.add(target)
            }
        }

        return legalMoves
    }

    override fun move(targetSquare: BoardSquare) {
        hasMoved = true

        if (targetSquare.piece != null) {
            board.capture(targetSquare.piece!!)
        }
        square.piece = null
        targetSquare.piece = this
        row = targetSquare.row
        col = targetSquare.col
    }
}
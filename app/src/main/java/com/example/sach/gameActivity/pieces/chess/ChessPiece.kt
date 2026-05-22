package com.example.sach.gameActivity.pieces.chess

import com.example.sach.gameActivity.board.ChessBoard
import com.example.sach.gameActivity.board.Square
import com.example.sach.gameActivity.pieces.Piece
import com.example.sach.gameActivity.pieces.PieceColor
import kotlin.Int

abstract class ChessPiece(override val board: ChessBoard, color: PieceColor, row: Int, col: Int): Piece(board, color, row, col) {
    abstract fun getPossibleMoves(): Array<Square>
    abstract fun getAttackMoves(): Array<Square>

    override fun getLegalMoves(): Array<Square> {
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
}
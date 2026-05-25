package com.example.sach.game.pieces.chess

import com.example.sach.game.board.BoardSquare
import com.example.sach.game.board.ChessBoard
import com.example.sach.game.pieces.Piece
import com.example.sach.game.pieces.PieceColor
import kotlin.Int

/**
 * abstraktná figúrka šachovej figúrky, vyhodnocuje možnosti pohybu
 */
abstract class ChessPiece(override val board: ChessBoard, color: PieceColor, row: Int, col: Int): Piece(board, color, row, col) {
    var hasMoved: Boolean = false

    /**
     * vráti všetky možné ťahy figúrky
     */
    abstract fun getPossibleMoves(): List<BoardSquare>

    /**
     * vráti všetky políčka, na ktoré figúrka útočí
     */
    abstract fun getAttackMoves(): List<BoardSquare>

    /**
     * vráti všetky legálne ťahy figúrky
     * na zistenie legálnych ťahov sa odsimulujú všetky možné ťahy a skontroluje sa
     * či je po danom ťahu kráľ v šachu
     */

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
        super.move(targetSquare)

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
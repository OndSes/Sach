package com.example.sach.gameActivity.pieces.checkers

import com.example.sach.R
import com.example.sach.gameActivity.board.CheckersBoard
import com.example.sach.gameActivity.board.Square
import com.example.sach.gameActivity.pieces.Direction
import com.example.sach.gameActivity.pieces.PieceColor
import com.example.sach.gameActivity.pieces.chess.Knight
import com.example.sach.gameActivity.pieces.chess.sliding.Bishop
import com.example.sach.gameActivity.pieces.chess.sliding.Queen
import com.example.sach.gameActivity.pieces.chess.sliding.Rook

class Man(board: CheckersBoard, color: PieceColor, row: Int, col: Int) : CheckersPiece(board, color, row, col) {
    override val maxMovement = 1
    override val directions = when (color) {
        PieceColor.WHITE -> listOf(Direction.UP_LEFT, Direction.UP_RIGHT)
        PieceColor.BLACK -> listOf(Direction.DOWN_LEFT, Direction.DOWN_RIGHT)
    }

    val promotionRow = if (color == PieceColor.WHITE) 0 else 7

    override fun getResourceId(): Int {
        return when (color) {
            PieceColor.WHITE -> R.drawable.white_man
            PieceColor.BLACK -> R.drawable.black_man
        }
    }

    override fun move(targetSquare: Square) {
        super.move(targetSquare)

        if (targetSquare.row == promotionRow) {
            promote(targetSquare)
        }
    }

    fun promote(square: Square) {
        val newPiece = CheckersKing(board, color, square.row, square.col)

        square.piece = newPiece

        when (color) {
            PieceColor.WHITE -> {
                board.whitePieces.remove(this)
                board.whitePieces.add(newPiece)
            }

            PieceColor.BLACK -> {
                board.blackPieces.remove(this)
                board.blackPieces.add(newPiece)
            }
        }

        square.updateView()
    }
}
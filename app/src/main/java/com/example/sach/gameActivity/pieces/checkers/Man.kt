package com.example.sach.gameActivity.pieces.checkers

import com.example.sach.R
import com.example.sach.gameActivity.board.CheckersBoard
import com.example.sach.gameActivity.board.Square
import com.example.sach.gameActivity.pieces.PieceColor

class Man(board: CheckersBoard, color: PieceColor, row: Int, col: Int) : CheckersPiece(board, color, row, col) {
    override fun getResourceId(): Int {
        return when (color) {
            PieceColor.WHITE -> R.drawable.white_man
            PieceColor.BLACK -> R.drawable.black_man
        }
    }

    override fun getLegalMoves(): Array<Square> {
        val moves = mutableListOf<Square>()
        moves.add(square)
        return moves.toTypedArray()
    }
}
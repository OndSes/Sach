package com.example.sach.gameActivity.pieces.checkers

import com.example.sach.R
import com.example.sach.gameActivity.board.CheckersBoard
import com.example.sach.gameActivity.pieces.Direction
import com.example.sach.gameActivity.pieces.PieceColor

class CheckersKing(board: CheckersBoard, color: PieceColor, row: Int, col: Int) : CheckersPiece(board, color, row, col) {
    override val maxMovement = 7
    override val directions = listOf(
        Direction.UP_LEFT,
        Direction.UP_RIGHT,
        Direction.DOWN_LEFT,
        Direction.DOWN_RIGHT
    )

    override fun getResourceId(): Int {
        return when (color) {
            PieceColor.WHITE -> R.drawable.white_checkers_king
            PieceColor.BLACK -> R.drawable.black_checkers_king
        }
    }
}
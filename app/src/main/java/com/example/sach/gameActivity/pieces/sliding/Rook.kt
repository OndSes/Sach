package com.example.sach.gameActivity.pieces.sliding

import com.example.sach.R
import com.example.sach.gameActivity.board.Board
import com.example.sach.gameActivity.pieces.Direction
import com.example.sach.gameActivity.pieces.PieceColor

class Rook(board: Board, color: PieceColor, row: Int, col: Int) : SlidingPiece(board, color, row, col)  {
    override val directions = listOf(
        Direction.UP,
        Direction.DOWN,
        Direction.LEFT,
        Direction.RIGHT
    )

    override fun getResourceId(): Int {
        return when (color) {
            PieceColor.WHITE -> R.drawable.white_rook
            PieceColor.BLACK -> R.drawable.black_rook
        }
    }
}
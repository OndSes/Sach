package com.example.sach.pieces.sliding

import com.example.sach.R
import com.example.sach.board.Board
import com.example.sach.board.Square
import com.example.sach.pieces.Direction
import com.example.sach.pieces.Piece

class Queen(board: Board, isWhite: Boolean, row: Int, col: Int) : SlidingPiece(board, isWhite, row, col)  {
    override val directions = listOf(
        Direction.UP,
        Direction.DOWN,
        Direction.LEFT,
        Direction.RIGHT,
        Direction.UP_LEFT,
        Direction.UP_RIGHT,
        Direction.DOWN_LEFT,
        Direction.DOWN_RIGHT
    )

    override fun getResourceId(): Int {
        return if (isWhite) {
            R.drawable.white_queen
        } else {
            R.drawable.black_queen
        }
    }
}
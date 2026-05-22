package com.example.sach.gameActivity.pieces.chess.sliding

import com.example.sach.R
import com.example.sach.gameActivity.board.ChessBoard
import com.example.sach.gameActivity.pieces.Direction
import com.example.sach.gameActivity.pieces.PieceColor

class Queen(board: ChessBoard, color: PieceColor, row: Int, col: Int) : SlidingPiece(board, color, row, col)  {
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
        return when (color) {
            PieceColor.WHITE -> R.drawable.white_queen
            PieceColor.BLACK -> R.drawable.black_queen
        }
    }
}
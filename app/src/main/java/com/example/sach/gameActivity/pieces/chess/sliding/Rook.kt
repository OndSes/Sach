package com.example.sach.gameActivity.pieces.chess.sliding

import com.example.sach.R
import com.example.sach.gameActivity.board.ChessBoard
import com.example.sach.gameActivity.pieces.Direction
import com.example.sach.gameActivity.pieces.PieceColor
import com.example.sach.gameActivity.pieces.PieceType

class Rook(board: ChessBoard, color: PieceColor, row: Int, col: Int) : SlidingPiece(board, color, row, col)  {
    override val type = PieceType.ROOK
    override val directions = listOf(
        Direction.UP,
        Direction.DOWN,
        Direction.LEFT,
        Direction.RIGHT
    )
}
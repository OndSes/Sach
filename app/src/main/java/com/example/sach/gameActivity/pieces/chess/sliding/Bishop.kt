package com.example.sach.gameActivity.pieces.chess.sliding

import com.example.sach.R
import com.example.sach.gameActivity.board.ChessBoard
import com.example.sach.gameActivity.pieces.Direction
import com.example.sach.gameActivity.pieces.PieceColor
import com.example.sach.gameActivity.pieces.PieceType

class Bishop(board: ChessBoard, color: PieceColor, row: Int, col: Int) : SlidingPiece(board, color, row, col) {
    override val type = PieceType.BISHOP
    override val directions = listOf(
        Direction.UP_LEFT,
        Direction.UP_RIGHT,
        Direction.DOWN_LEFT,
        Direction.DOWN_RIGHT
    )
}
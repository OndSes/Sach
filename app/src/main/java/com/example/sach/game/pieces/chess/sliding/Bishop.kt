package com.example.sach.game.pieces.chess.sliding

import com.example.sach.game.board.ChessBoard
import com.example.sach.game.pieces.Direction
import com.example.sach.game.pieces.PieceColor
import com.example.sach.game.pieces.PieceType

class Bishop(board: ChessBoard, color: PieceColor, row: Int, col: Int) : SlidingPiece(board, color, row, col) {
    override val type = PieceType.BISHOP
    override val directions = listOf(
        Direction.UP_LEFT,
        Direction.UP_RIGHT,
        Direction.DOWN_LEFT,
        Direction.DOWN_RIGHT
    )
}
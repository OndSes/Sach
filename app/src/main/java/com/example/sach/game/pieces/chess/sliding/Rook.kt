package com.example.sach.game.pieces.chess.sliding

import com.example.sach.game.board.ChessBoard
import com.example.sach.game.pieces.Direction
import com.example.sach.game.pieces.PieceColor
import com.example.sach.game.pieces.PieceType

class Rook(board: ChessBoard, color: PieceColor, row: Int, col: Int) : SlidingPiece(board, color, row, col)  {
    override val type = PieceType.ROOK
    override val directions = listOf(
        Direction.UP,
        Direction.DOWN,
        Direction.LEFT,
        Direction.RIGHT
    )
}
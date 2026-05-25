package com.example.sach.game.pieces.checkers

import com.example.sach.game.board.CheckersBoard
import com.example.sach.game.pieces.Direction
import com.example.sach.game.pieces.PieceColor
import com.example.sach.game.pieces.PieceType

/**
 * dámovy kráľ, ktorý sa pohybuje po diagonálach vo všetkých smeroch
 */
class CheckersKing(board: CheckersBoard, color: PieceColor, row: Int, col: Int) : CheckersPiece(board, color, row, col) {
    override val type = PieceType.CHECKERS_KING
    override val maxMovement = 7
    override val directions = listOf(
        Direction.UP_LEFT,
        Direction.UP_RIGHT,
        Direction.DOWN_LEFT,
        Direction.DOWN_RIGHT
    )
}
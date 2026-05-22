package com.example.sach.pieces.sliding

import com.example.sach.board.Board
import com.example.sach.board.Square
import com.example.sach.pieces.Direction
import com.example.sach.pieces.Piece
import com.example.sach.pieces.PieceColor

abstract class SlidingPiece(board: Board, color: PieceColor, row: Int, col: Int) : Piece(board, color, row, col) {
    abstract val directions: List<Direction>

    override fun getPossibleMoves(): Array<Square> {
        val moves = mutableListOf<Square>()
        var squareToCheck: Square
        var x = 1
        for(direction in directions) {
            while (board.isValidPosition(square.row + x * direction.rowDelta, square.col + x * direction.colDelta)) {
                squareToCheck = board.getSquare(square.row + x * direction.rowDelta, square.col + x * direction.colDelta)
                if (squareContainsAlliedPiece(squareToCheck)) {
                    break
                }
                if (squareContainsEnemyPiece(squareToCheck)) {
                    moves.add(squareToCheck)
                    break
                }
                moves.add(squareToCheck)
                x++
            }
            x = 1
        }
        return moves.toTypedArray()
    }

    override fun getAttackMoves(): Array<Square> {
        val moves = mutableListOf<Square>()
        var squareToCheck: Square
        var x = 1
        for(direction in directions) {
            while (board.isValidPosition(square.row + x * direction.rowDelta, square.col + x * direction.colDelta)) {
                squareToCheck = board.getSquare(square.row + x * direction.rowDelta, square.col + x * direction.colDelta)
                if (squareContainsPiece(squareToCheck)) {
                    moves.add(squareToCheck)
                    break
                }
                moves.add(squareToCheck)
                x++
            }
            x = 1
        }
        return moves.toTypedArray()
    }
}
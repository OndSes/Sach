package com.example.sach.pieces.sliding

import com.example.sach.board.Board
import com.example.sach.board.Square
import com.example.sach.pieces.Direction
import com.example.sach.pieces.Piece

abstract class SlidingPiece(board: Board, isWhite: Boolean, row: Int, col: Int) : Piece(board, isWhite, row, col) {
    abstract val directions: List<Direction>

    override fun getPossibleMoves(): Array<Square> {
        val moves = mutableListOf<Square>()
        var squareToCheck: Square
        var x: Int = 1
        for(direction in directions) {
            while (board.isValidPosition(row + x * direction.rowDelta, col + x * direction.colDelta)) {
                squareToCheck = board.getSquare(row + x * direction.rowDelta, col + x * direction.colDelta)
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
        var x: Int = 1
        for(direction in directions) {
            while (board.isValidPosition(row + x * direction.rowDelta, col + x * direction.colDelta)) {
                squareToCheck = board.getSquare(row + x * direction.rowDelta, col + x * direction.colDelta)
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
package com.example.sach.pieces

import com.example.sach.R
import com.example.sach.board.Board
import com.example.sach.board.Square

class King(board: Board, isWhite: Boolean, row: Int, col: Int) : Piece(board, isWhite, row, col)  {
    override fun getResourceId(): Int {
        return if (isWhite) {
            R.drawable.white_king
        } else {
            R.drawable.black_king
        }
    }

    override fun getPossibleMoves(): Array<Square> {
        val moves = mutableListOf<Square>()
        var squareToCheck: Square

        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) {
                    continue
                }
                if (!board.isValidPosition(row + i, col + j)) {
                    continue
                }

                squareToCheck = board.getSquare(row + i, col + j)
                if (squareContainsAlliedPiece(squareToCheck)) {
                    continue
                }
                if (board.isSquareInCheck(squareToCheck, !isWhite)) {
                    continue
                }

                moves.add(squareToCheck)
            }
        }

        return moves.toTypedArray()
    }

    override fun getAttackMoves(): Array<Square> {
        val moves = mutableListOf<Square>()

        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) {
                    continue
                }
                if (!board.isValidPosition(row + i, col + j)) {
                    continue
                }
                moves.add(board.getSquare(row + i, col + j))
            }
        }

        return moves.toTypedArray()
    }
}
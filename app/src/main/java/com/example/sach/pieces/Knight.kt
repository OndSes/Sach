package com.example.sach.pieces

import com.example.sach.R
import com.example.sach.board.Board
import com.example.sach.board.Square

class Knight(board: Board, isWhite: Boolean, row: Int, col: Int) : Piece(board, isWhite, row, col)  {
    override fun getResourceId(): Int {
        return if (isWhite) {
            R.drawable.white_knight
        } else {
            R.drawable.black_knight
        }
    }

    override fun getPossibleMoves(): Array<Square> {
        val moves = mutableListOf<Square>()
        var squareToCheck: Square

        for (i in listOf(-2,-1,1,2)) {
            for (j in listOf(-2,-1,1,2)) {
                if (i*i == j*j) {
                    continue
                }
                if (!board.isValidPosition(row+i,col+j)) {
                    continue
                }
                squareToCheck = board.getSquare(row+i,col+j)
                if (squareContainsAlliedPiece(squareToCheck)) {
                    continue
                }
                moves.add(squareToCheck)
            }
        }
        return moves.toTypedArray()
    }

    override fun getAttackMoves(): Array<Square> {
        val moves = mutableListOf<Square>()

        for (i in listOf(-2,-1,1,2)) {
            for (j in listOf(-2,-1,1,2)) {
                if (i*i == j*j) {
                    continue
                }
                if (!board.isValidPosition(row+i,col+j)) {
                    continue
                }

                moves.add(board.getSquare(row+i,col+j))
            }
        }
        return moves.toTypedArray()
    }
}
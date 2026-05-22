package com.example.sach.gameActivity.pieces

import com.example.sach.R
import com.example.sach.gameActivity.board.Board
import com.example.sach.gameActivity.board.Square

class Knight(board: Board, color: PieceColor, row: Int, col: Int) : Piece(board, color, row, col)  {
    override fun getResourceId(): Int {
        return when (color) {
            PieceColor.WHITE -> R.drawable.white_knight
            PieceColor.BLACK -> R.drawable.black_knight
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
                if (!board.isValidPosition(square.row + i,square.col + j)) {
                    continue
                }
                squareToCheck = board.getSquare(square.row + i,square.col + j)
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
                if (!board.isValidPosition(square.row + i,square.col + j)) {
                    continue
                }

                moves.add(board.getSquare(square.row + i,square.col + j))
            }
        }
        return moves.toTypedArray()
    }
}
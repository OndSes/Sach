package com.example.sach.pieces

import com.example.sach.R
import com.example.sach.board.Board
import com.example.sach.board.Square
import com.example.sach.pieces.sliding.Rook

class King(board: Board, color: PieceColor, row: Int, col: Int, val leftRook: Rook, val rightRook: Rook) : Piece(board, color, row, col)  {
    override fun getResourceId(): Int {
        return when (color) {
            PieceColor.WHITE -> R.drawable.white_king
            PieceColor.BLACK -> R.drawable.black_king
        }
    }

    override fun getPossibleMoves(): Array<Square> {
        val moves = mutableListOf<Square>()
        var squareToCheck: Square

        var castleSquare = checkLeftCastle()
        if (castleSquare != null) {
            moves.add(castleSquare)
        }
        castleSquare = checkRightCastle()
        if (castleSquare != null) {
            moves.add(castleSquare)
        }

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
                if (board.isSquareInCheck(squareToCheck, color.oppostie, null)) {
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

    override fun move(square: Square) {
        if (square.col == col - 2) {
            leftRook.move(board.getSquare(row, col - 1))
        } else if (square.col == col + 2) {
            rightRook.move(board.getSquare(row, col + 1))
        }

        super.move(square)
    }

    private fun checkLeftCastle(): Square? {
        if (hasMoved || leftRook.hasMoved) {
            return null
        }
        var squareToCheck: Square
        for (i in -3..-1) {
            squareToCheck = board.getSquare(row, col + i)
            if (squareContainsPiece(squareToCheck)) {
                return null
            }
            if (i == -3) {
                continue
            }
            if (board.isSquareInCheck(squareToCheck, color.oppostie, null)) {
                return null
            }
        }
        return board.getSquare(row, col - 2)
    }


    private fun checkRightCastle(): Square? {
        if (hasMoved || rightRook.hasMoved) {
            return null
        }
        var squareToCheck: Square
        for (i in 1..2) {
            squareToCheck = board.getSquare(row, col + i)
            if (squareContainsPiece(squareToCheck)) {
                return null
            }
            if (board.isSquareInCheck(squareToCheck, color.oppostie, null)) {
                return null
            }
        }
        return board.getSquare(row, col + 2)
    }

}
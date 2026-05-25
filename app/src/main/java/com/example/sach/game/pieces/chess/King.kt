package com.example.sach.game.pieces.chess

import com.example.sach.game.board.ChessBoard
import com.example.sach.game.board.BoardSquare
import com.example.sach.game.pieces.PieceColor
import com.example.sach.game.pieces.PieceType
import com.example.sach.game.pieces.chess.sliding.Rook

/**
 * kráľ, ktorý sa pohybuje o jedno políčko vo všetkých smeroch
 */
class King(board: ChessBoard, color: PieceColor, row: Int, col: Int, val leftRook: Rook, val rightRook: Rook) : ChessPiece(board, color, row, col)  {
    override val type = PieceType.KING

    override fun getPossibleMoves(): List<BoardSquare> {
        val moves = mutableListOf<BoardSquare>()
        var squareToCheck: BoardSquare

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
                if (!board.isValidPosition(square.row + i, square.col + j)) {
                    continue
                }

                squareToCheck = board.getSquare(square.row + i, square.col + j)
                if (squareContainsAlliedPiece(squareToCheck)) {
                    continue
                }
                if (board.isSquareInCheck(squareToCheck, color.opposite, null)) {
                    continue
                }

                moves.add(squareToCheck)
            }
        }

        return moves
    }

    override fun getAttackMoves(): List<BoardSquare> {
        val moves = mutableListOf<BoardSquare>()

        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) {
                    continue
                }
                if (!board.isValidPosition(square.row + i, square.col + j)) {
                    continue
                }
                moves.add(board.getSquare(square.row + i, square.col + j))
            }
        }

        return moves
    }

    override fun move(targetSquare: BoardSquare) {
        if (targetSquare.col == square.col - 2) {
            leftRook.move(board.getSquare(square.row, square.col - 1))
        } else if (targetSquare.col == square.col + 2) {
            rightRook.move(board.getSquare(square.row, square.col + 1))
        }

        super.move(targetSquare)
    }

    /**
     * skontroluje, či je možná lavá rošáda
     */
    private fun checkLeftCastle(): BoardSquare? {
        if (hasMoved || leftRook.hasMoved) {
            return null
        }
        var squareToCheck: BoardSquare
        for (i in -3..-1) {
            squareToCheck = board.getSquare(square.row, square.col + i)
            if (squareContainsPiece(squareToCheck)) {
                return null
            }
            if (i == -3) {
                continue
            }
            if (board.isSquareInCheck(squareToCheck, color.opposite, null)) {
                return null
            }
        }
        return board.getSquare(square.row, square.col - 2)
    }

    /**
     * skontroluje, či je možná pravá rošáda
     */
    private fun checkRightCastle(): BoardSquare? {
        if (hasMoved || rightRook.hasMoved) {
            return null
        }
        var squareToCheck: BoardSquare
        for (i in 1..2) {
            squareToCheck = board.getSquare(square.row, square.col + i)
            if (squareContainsPiece(squareToCheck)) {
                return null
            }
            if (board.isSquareInCheck(squareToCheck, color.opposite, null)) {
                return null
            }
        }
        return board.getSquare(square.row, square.col + 2)
    }

}
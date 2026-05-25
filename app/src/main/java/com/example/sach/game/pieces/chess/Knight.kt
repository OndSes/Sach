package com.example.sach.game.pieces.chess

import com.example.sach.game.board.ChessBoard
import com.example.sach.game.board.BoardSquare
import com.example.sach.game.pieces.PieceColor
import com.example.sach.game.pieces.PieceType

/**
 * jazdec, ktorý sa pohybuje do L-ka a môže preskakovať figúrky
 */
class Knight(board: ChessBoard, color: PieceColor, row: Int, col: Int) : ChessPiece(board, color, row, col)  {
    override val type = PieceType.KNIGHT

    override fun getPossibleMoves(): List<BoardSquare> {
        val moves = mutableListOf<BoardSquare>()
        var squareToCheck: BoardSquare

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
        return moves
    }

    override fun getAttackMoves(): List<BoardSquare> {
        val moves = mutableListOf<BoardSquare>()

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
        return moves
    }
}
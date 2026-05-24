package com.example.sach.game.pieces.chess

import com.example.sach.game.board.ChessBoard
import com.example.sach.game.board.BoardSquare
import com.example.sach.game.pieces.PieceColor
import com.example.sach.game.pieces.PieceType

class Pawn(board: ChessBoard, color: PieceColor, row: Int, col: Int) : ChessPiece(board, color, row, col)  {
    override val type = PieceType.PAWN
    val promotionRow = if (color == PieceColor.WHITE) 0 else 7

    override fun getPossibleMoves(): List<BoardSquare> {
        val moves = mutableListOf<BoardSquare>()
        val movement = if (color == PieceColor.WHITE) { -1 } else { 1 }

        var squareToCheck: BoardSquare

        if (board.isValidPosition(square.row + movement, square.col + 1)) {
            squareToCheck = board.getSquare(square.row + movement, square.col + 1)
            if (squareContainsEnemyPiece(squareToCheck)) {
                moves.add(squareToCheck)
            }
        }
        if (board.isValidPosition(square.row + movement, square.col - 1)) {
            squareToCheck = board.getSquare(square.row + movement, square.col - 1)
            if (squareContainsEnemyPiece(squareToCheck)) {
                moves.add(squareToCheck)
            }
        }

        squareToCheck = board.getSquare(square.row + movement, square.col)
        if (squareContainsPiece(squareToCheck)) {
            return moves
        }
        moves.add(squareToCheck)
        if(hasMoved) {
            return moves
        }
        squareToCheck = board.getSquare(square.row + 2 * movement, square.col)
        if (!squareContainsPiece(squareToCheck)) {
            moves.add(squareToCheck)
        }
        return moves
    }

    override fun getAttackMoves(): List<BoardSquare> {
        val moves = mutableListOf<BoardSquare>()
        val movement = if (color == PieceColor.WHITE) { -1 } else { 1 }

        if (board.isValidPosition(square.row + movement, square.col + 1)) {
            moves.add(board.getSquare(square.row + movement, square.col + 1))
        }
        if (board.isValidPosition(square.row + movement, square.col - 1)) {
            moves.add(board.getSquare(square.row + movement, square.col - 1))
        }

        return moves
    }

    override fun move(targetSquare: BoardSquare) {
        super.move(targetSquare)

        if (targetSquare.row == promotionRow) {
            board.game.onPromotionRequested?.invoke(this)
        }
    }
}
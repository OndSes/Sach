package com.example.sach.pieces

import android.app.AlertDialog
import com.example.sach.R
import com.example.sach.board.Board
import com.example.sach.board.Square
import com.example.sach.pieces.sliding.Bishop
import com.example.sach.pieces.sliding.Queen
import com.example.sach.pieces.sliding.Rook

class Pawn(board: Board, color: PieceColor, row: Int, col: Int) : Piece(board, color, row, col)  {
    override fun getResourceId(): Int {
        return when (color) {
            PieceColor.WHITE -> R.drawable.white_pawn
            PieceColor.BLACK -> R.drawable.black_pawn
        }
    }

    override fun getPossibleMoves(): Array<Square> {
        val moves = mutableListOf<Square>()
        val movement = if (color == PieceColor.WHITE) { -1 } else { 1 }

        var squareToCheck: Square

        if (board.isValidPosition(row + movement, col + 1)) {
            squareToCheck = board.getSquare(row + movement, col + 1)
            if (squareContainsEnemyPiece(squareToCheck)) {
                moves.add(squareToCheck)
            }
        }
        if (board.isValidPosition(row + movement, col - 1)) {
            squareToCheck = board.getSquare(row + movement, col - 1)
            if (squareContainsEnemyPiece(squareToCheck)) {
                moves.add(squareToCheck)
            }
        }

        squareToCheck = board.getSquare(row + movement, col)
        if (squareContainsPiece(squareToCheck)) {
            return moves.toTypedArray()
        }
        moves.add(squareToCheck)
        if(hasMoved) {
            return moves.toTypedArray()
        }
        squareToCheck = board.getSquare(row + 2 * movement, col)
        if (!squareContainsPiece(squareToCheck)) {
            moves.add(squareToCheck)
        }
        return moves.toTypedArray()
    }

    override fun getAttackMoves(): Array<Square> {
        val moves = mutableListOf<Square>()
        val movement = if (color == PieceColor.WHITE) { -1 } else { 1 }

        if (board.isValidPosition(row + movement, col + 1)) {
            moves.add(board.getSquare(row + movement, col + 1))
        }
        if (board.isValidPosition(row + movement, col - 1)) {
            moves.add(board.getSquare(row + movement, col - 1))
        }

        return moves.toTypedArray()
    }

    override fun move(square: Square) {

        super.move(square)

        val promotionRow = if (color == PieceColor.WHITE) 0 else 7

        if (square.row == promotionRow) {
            promote(square)
        }
    }

    private fun promote(square: Square) {

        val options = arrayOf(
            "Queen",
            "Rook",
            "Bishop",
            "Knight"
        )

        AlertDialog.Builder(board.context)
            .setTitle("Promote Pawn")
            .setItems(options) { _, which ->

                val newPiece = when (which) {
                    0 -> Queen(board, color, square.row, square.col)
                    1 -> Rook(board, color, square.row, square.col)
                    2 -> Bishop(board, color, square.row, square.col)
                    else -> Knight(board, color, square.row, square.col)
                }

                square.piece = newPiece

                when (color) {
                    PieceColor.WHITE -> {
                        board.whitePieces.remove(this)
                        board.whitePieces.add(newPiece)
                    }

                    PieceColor.BLACK -> {
                        board.blackPieces.remove(this)
                        board.blackPieces.add(newPiece)
                    }
                }

                square.updateView()
            }
            .show()
    }
}
package com.example.sach.gameActivity.pieces.chess

import android.app.AlertDialog
import com.example.sach.R
import com.example.sach.gameActivity.board.ChessBoard
import com.example.sach.gameActivity.board.Square
import com.example.sach.gameActivity.pieces.PieceColor
import com.example.sach.gameActivity.pieces.chess.sliding.Bishop
import com.example.sach.gameActivity.pieces.chess.sliding.Queen
import com.example.sach.gameActivity.pieces.chess.sliding.Rook

class Pawn(board: ChessBoard, color: PieceColor, row: Int, col: Int) : ChessPiece(board, color, row, col)  {
    val promotionRow = if (color == PieceColor.WHITE) 0 else 7

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
            return moves.toTypedArray()
        }
        moves.add(squareToCheck)
        if(hasMoved) {
            return moves.toTypedArray()
        }
        squareToCheck = board.getSquare(square.row + 2 * movement, square.col)
        if (!squareContainsPiece(squareToCheck)) {
            moves.add(squareToCheck)
        }
        return moves.toTypedArray()
    }

    override fun getAttackMoves(): Array<Square> {
        val moves = mutableListOf<Square>()
        val movement = if (color == PieceColor.WHITE) { -1 } else { 1 }

        if (board.isValidPosition(square.row + movement, square.col + 1)) {
            moves.add(board.getSquare(square.row + movement, square.col + 1))
        }
        if (board.isValidPosition(square.row + movement, square.col - 1)) {
            moves.add(board.getSquare(square.row + movement, square.col - 1))
        }

        return moves.toTypedArray()
    }

    override fun move(targetSquare: Square) {
        super.move(targetSquare)

        if (targetSquare.row == promotionRow) {
            promote(targetSquare)
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
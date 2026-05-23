package com.example.sach.gameActivity.board

import android.content.Context
import android.widget.GridLayout
import com.example.sach.gameActivity.games.Game
import com.example.sach.gameActivity.pieces.Piece
import com.example.sach.gameActivity.pieces.PieceColor

abstract class Board(val boardView: GridLayout,val context: Context, game: Game) {
    abstract val whitePieces: MutableList<Piece>
    abstract val blackPieces: MutableList<Piece>


    val squares: Array<Array<Square>> =
        Array(8) { row ->
            Array(8) { col ->
                Square(row, col, context, game)
            }
        }

    abstract fun checkState(color: PieceColor): StateOfGame

    fun getSquare(row: Int, col: Int): Square {
        return squares[row][col]
    }

    fun isValidPosition(row: Int, col: Int): Boolean {
        return row in 0..7 && col in 0..7
    }

    fun activateSquares(color: PieceColor) {
        for (piece in whitePieces + blackPieces) {
            piece.square.isActive = piece.color == color
        }
    }

    fun capture(piece: Piece) {
        if (piece.color == PieceColor.WHITE) {
            whitePieces.remove(piece)
        } else {
            blackPieces.remove(piece)
        }
    }

    fun rotate(turnColor: PieceColor) {
        boardView.animate()
            .rotation(
                if (turnColor == PieceColor.WHITE) 0f else 180f
            )
            .setDuration(300)
            .start()

        rotatePieces()
    }

    fun rotatePieces() {
        for (row in squares) {
            for (square in row) {
                square.rotateView()
            }
        }
    }


}
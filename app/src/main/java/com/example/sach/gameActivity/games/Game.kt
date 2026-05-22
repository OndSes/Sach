package com.example.sach.gameActivity.games

import android.app.AlertDialog
import com.example.sach.gameActivity.Settings
import com.example.sach.gameActivity.board.Board
import com.example.sach.gameActivity.board.Square
import com.example.sach.gameActivity.pieces.PieceColor

abstract class Game(val settings: Settings) {
    abstract val board: Board
    var selectedSquare: Square? = null
    var turnColor: PieceColor = PieceColor.WHITE
    fun selectSquare(square: Square) {
        if(square.piece != null && square.piece!!.color == turnColor) {
            selectPiece(square)
        } else {
            movePiece(square)
        }
    }

    private fun selectPiece(square: Square) {
        selectedSquare?.view?.alpha = 1.0f
        if (selectedSquare?.piece != null) {
            deactivateSquares(selectedSquare!!.piece!!.getLegalMoves())
        }
        selectedSquare = square
        square.view.alpha = 0.5f
        activateSquares(square.piece!!.getLegalMoves())
    }

    private fun movePiece(square: Square) {
        deactivateSquares(selectedSquare!!.piece!!.getLegalMoves())
        selectedSquare!!.isActive = false
        selectedSquare!!.hideMoveIndicator()
        selectedSquare!!.view.alpha = 1.0f
        selectedSquare!!.piece!!.move(square)
        selectedSquare = null
        nextMove()
    }

    open fun nextMove() {
        activateSquares()
        if (settings.rotateBoard) {
            board.rotate(turnColor)
        } else if (settings.rotatePieces) {
            board.rotatePieces()
        }
    }

    protected fun activateSquares() {
        board.activateSquares(turnColor)
    }

    private fun activateSquares(squares: Array<Square>) {
        for (square in squares) {
            square.isActive = true
            square.showMoveIndicator()
        }
    }

    private fun deactivateSquares(squares: Array<Square>) {
        for (square in squares) {
            square.isActive = false
            square.hideMoveIndicator()
        }
    }

    protected fun showGameOverMessage(message: String) {

        AlertDialog.Builder(board.context)
            .setTitle("Game Over")
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("New Game") { _, _ ->

                // restart game here

            }
            .setNegativeButton("Close", null)
            .show()
    }
}
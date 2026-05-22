package com.example.sach.gameActivity

import android.app.AlertDialog
import android.content.Context
import android.widget.GridLayout
import com.example.sach.gameActivity.board.Board
import com.example.sach.gameActivity.board.Square
import com.example.sach.gameActivity.board.StateOfGame
import com.example.sach.gameActivity.pieces.PieceColor

class Game(boardView: GridLayout, context: Context) {
    val board: Board = Board(boardView, context, this)
    var selectedSquare: Square? = null
    var turnColor: PieceColor = PieceColor.WHITE

    init {
        activateSquares()
    }
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
        nextRound()
    }

    private fun nextRound() {
        turnColor = turnColor.opposite
        val state: StateOfGame = board.checkForMate(turnColor)
        if (state == StateOfGame.CHECK_MATE) {
            when (turnColor) {
                PieceColor.WHITE -> showGameOverMessage("Checkmate! Black Wins")
                PieceColor.BLACK -> showGameOverMessage("Checkmate! White Wins")
            }
        } else if (state == StateOfGame.STALEMATE) {
            showGameOverMessage("Draw by stalemate")
        }
        activateSquares()
    }


    private fun activateSquares() {
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

    private fun showGameOverMessage(message: String) {

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
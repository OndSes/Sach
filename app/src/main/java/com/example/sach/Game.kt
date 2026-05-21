package com.example.sach

import android.app.AlertDialog
import android.content.Context
import android.widget.GridLayout
import com.example.sach.board.Board
import com.example.sach.board.Square
import com.example.sach.board.StateOfGame

class Game(boardView: GridLayout, context: Context) {
    val board: Board = Board(boardView, context, this)
    var selectedSquare: Square? = null
    var whitesTurn: Boolean = true

    init {
        activateSquares()
    }
    fun selectSquare(square: Square) {
        if(square.piece != null && square.piece!!.isWhite == whitesTurn) {
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
        whitesTurn = !whitesTurn
        val state: StateOfGame = board.checkForMate(whitesTurn)
        if (state == StateOfGame.CHECK_MATE) {
            if (whitesTurn) {
                showGameOverMessage("Checkmate! Black Wins")
            } else {
                showGameOverMessage("Checkmate! White Wins")
            }
        } else if (state == StateOfGame.STALEMATE) {
            showGameOverMessage("Draw by stalemate")
        }
        activateSquares()
    }


    private fun activateSquares() {
        board.activateSquares(whitesTurn)
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
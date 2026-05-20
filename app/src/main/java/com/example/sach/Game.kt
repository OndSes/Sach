package com.example.sach

import android.content.Context
import android.widget.GridLayout
import com.example.sach.board.Board
import com.example.sach.board.Square
import com.example.sach.pieces.Piece
import com.example.sach.pieces.PieceGenerator

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
            deactivateSquares(selectedSquare!!.piece!!.getPossibleMoves())
        }
        selectedSquare = square
        square.view.alpha = 0.5f
        activateSquares(square.piece!!.getPossibleMoves())
    }

    private fun movePiece(square: Square) {
        deactivateSquares(selectedSquare!!.piece!!.getPossibleMoves())
        selectedSquare!!.view.alpha = 1.0f
        selectedSquare!!.piece!!.move(square)
        selectedSquare = null
        whitesTurn = !whitesTurn
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
}
package com.example.sach.gameActivity.games

import android.app.AlertDialog
import android.content.Context
import android.widget.GridLayout
import com.example.sach.gameActivity.Settings
import com.example.sach.gameActivity.board.ChessBoard
import com.example.sach.gameActivity.board.StateOfGame
import com.example.sach.gameActivity.pieces.Piece
import com.example.sach.gameActivity.pieces.PieceColor
import com.example.sach.gameActivity.pieces.chess.Knight
import com.example.sach.gameActivity.pieces.chess.Pawn
import com.example.sach.gameActivity.pieces.chess.sliding.Bishop
import com.example.sach.gameActivity.pieces.chess.sliding.Queen
import com.example.sach.gameActivity.pieces.chess.sliding.Rook
import com.example.sach.gameActivity.ui.BoardRenderer

class Chess(settings: Settings): Game(settings) {


    init {
        board = ChessBoard(this)
    }

    fun promotePawn(pawn: Pawn, promotedPiece: Piece) {
        val square = board.getSquare(pawn.row, pawn.col)

        square.piece = promotedPiece

        val pieces =
            if (pawn.color == PieceColor.WHITE)
                board.whitePieces
            else
                board.blackPieces

        pieces.remove(pawn)
        pieces.add(promotedPiece)
    }

    override fun nextMove() {
        turnColor = turnColor.opposite
        val state: StateOfGame = board.checkState(turnColor)
        if (state == StateOfGame.CHECK_MATE) {
            when (turnColor) {
                PieceColor.WHITE -> onGameMessageRequested?.invoke("Checkmate! Black Wins")
                PieceColor.BLACK -> onGameMessageRequested?.invoke("Checkmate! White Wins")
            }
        } else if (state == StateOfGame.STALEMATE) {
            onGameMessageRequested?.invoke("Draw by stalemate")
        }
        super.nextMove()
    }
}
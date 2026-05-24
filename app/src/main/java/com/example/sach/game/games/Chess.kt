package com.example.sach.game.games

import com.example.sach.game.Settings
import com.example.sach.game.board.ChessBoard
import com.example.sach.game.board.StateOfGame
import com.example.sach.game.pieces.Piece
import com.example.sach.game.pieces.PieceColor
import com.example.sach.game.pieces.chess.Pawn

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
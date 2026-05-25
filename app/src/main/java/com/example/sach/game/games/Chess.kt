package com.example.sach.game.games

import com.example.sach.game.Settings
import com.example.sach.game.board.ChessBoard
import com.example.sach.game.board.StateOfGame
import com.example.sach.game.pieces.Piece
import com.example.sach.game.pieces.PieceColor
import com.example.sach.game.pieces.chess.Pawn

/**
 * trieda, ktorá kontroluje chod šachu
 */
class Chess(settings: Settings): Game(settings) {


    init {
        board = ChessBoard(this)
    }

    /**
     * zmení pešiaka na danú figúrku
     */
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

    /**
     * vyhodnotí kto je další na ťahu a overí, či sa hra neskončila
     */
    override fun nextMove() {
        turnColor = turnColor.opposite
        val state: StateOfGame = board.checkState(turnColor)
        if (state != StateOfGame.NOT_MATE) {
            onGameOver?.invoke(turnColor.opposite, state)
        }
        super.nextMove()
    }
}
package com.example.sach.game.games

import com.example.sach.game.Settings
import com.example.sach.game.board.Board
import com.example.sach.game.board.BoardSquare
import com.example.sach.game.pieces.PieceColor
import com.example.sach.game.pieces.chess.Pawn

abstract class Game(val settings: Settings) {
    lateinit var board: Board
    var turnColor: PieceColor = PieceColor.WHITE
    var selectedSquare: BoardSquare? = null
    var onBoardChanged: (() -> Unit)? = null
    var onPieceSelected: ((List<BoardSquare>) -> Unit)? = null
    var onPromotionRequested: ((Pawn) -> Unit)? = null
    var onGameMessageRequested: ((String) -> Unit)? = null
    fun movePiece(square: BoardSquare) {
        selectedSquare!!.piece!!.move(square)
        nextMove()
    }

    open fun nextMove() {
        onBoardChanged?.invoke()
    }

    fun selectSquare(square: BoardSquare) {
        if(square.piece != null && square.piece!!.color == turnColor) {
            selectedSquare = square
            onPieceSelected?.invoke(square.piece!!.getLegalMoves())
        } else {
            movePiece(square)
        }
    }
}
package com.example.sach.gameActivity.games

import com.example.sach.gameActivity.Settings
import com.example.sach.gameActivity.board.Board
import com.example.sach.gameActivity.board.BoardSquare
import com.example.sach.gameActivity.pieces.Piece
import com.example.sach.gameActivity.pieces.PieceColor
import com.example.sach.gameActivity.pieces.chess.Pawn
import com.example.sach.gameActivity.ui.BoardRenderer

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
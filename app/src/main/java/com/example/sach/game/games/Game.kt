package com.example.sach.game.games

import com.example.sach.game.Settings
import com.example.sach.game.board.Board
import com.example.sach.game.board.BoardSquare
import com.example.sach.game.board.StateOfGame
import com.example.sach.game.pieces.PieceColor
import com.example.sach.game.pieces.chess.Pawn
import java.util.UUID

abstract class Game(val settings: Settings) {
    val gameId = UUID.randomUUID().toString()
    lateinit var board: Board
    var turnColor: PieceColor = PieceColor.WHITE
    val moves = mutableListOf<String>()
    var selectedSquare: BoardSquare? = null
    var onBoardChanged: (() -> Unit)? = null
    var onPieceSelected: ((List<BoardSquare>) -> Unit)? = null
    var onPromotionRequested: ((Pawn) -> Unit)? = null
    var onGameOver: ((PieceColor, StateOfGame) -> Unit)? = null
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

    fun recordMove(move: String) {
        moves.add(move)
    }
}
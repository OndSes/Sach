package com.example.sach.gameActivity.board

import android.content.Context
import android.widget.GridLayout
import com.example.sach.gameActivity.games.Game
import com.example.sach.gameActivity.pieces.Piece

class CheckersBoard(boardView: GridLayout, context: Context, game: Game): Board(boardView, context, game) {
    override val whitePieces: MutableList<Piece> = PieceGenerator.generateWhiteCheckersPieces(this)
    override val blackPieces: MutableList<Piece> = PieceGenerator.generateBlackCheckersPieces(this)

    init {
        for (row in 0..7) {
            for (col in 0..7) {
                boardView.addView(squares[row][col].container)
            }
        }
    }
}
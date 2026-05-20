package com.example.sach.pieces

import com.example.sach.R
import com.example.sach.board.Board
import com.example.sach.board.Square

class King(board: Board, isWhite: Boolean, row: Int, col: Int) : Piece(board, isWhite, row, col)  {
    override fun getResourceId(): Int {
        return if (isWhite) {
            R.drawable.white_king
        } else {
            R.drawable.black_king
        }
    }

    override fun getPossibleMoves(): Array<Square> {
        return Array(1){ board.getSquare(0,0) }
    }
}
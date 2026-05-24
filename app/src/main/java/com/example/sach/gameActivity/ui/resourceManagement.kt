package com.example.sach.gameActivity.ui

import com.example.sach.R
import com.example.sach.gameActivity.pieces.Piece
import com.example.sach.gameActivity.pieces.PieceColor
import com.example.sach.gameActivity.pieces.PieceType

fun getPieceDrawable(piece: Piece?): Int? {
    if (piece == null) {
        return null
    }
    return when (piece.type) {

        PieceType.KING -> {
            if (piece.color == PieceColor.WHITE)
                R.drawable.white_king
            else
                R.drawable.black_king
        }
        PieceType.QUEEN -> {
            if (piece.color == PieceColor.WHITE)
                R.drawable.white_queen
            else
                R.drawable.black_queen
        }
        PieceType.ROOK -> {
            if (piece.color == PieceColor.WHITE)
                R.drawable.white_rook
            else
                R.drawable.black_rook
        }
        PieceType.BISHOP -> {
            if (piece.color == PieceColor.WHITE)
                R.drawable.white_bishop
            else
                R.drawable.black_bishop
        }
        PieceType.KNIGHT -> {
            if (piece.color == PieceColor.WHITE)
                R.drawable.white_knight
            else
                R.drawable.black_knight
        }
        PieceType.PAWN -> {
            if (piece.color == PieceColor.WHITE)
                R.drawable.white_pawn
            else
                R.drawable.black_pawn
        }
        PieceType.MAN -> {
            if (piece.color == PieceColor.WHITE)
                R.drawable.white_man
            else
                R.drawable.black_man
        }
        PieceType.CHECKERS_KING -> {
            if (piece.color == PieceColor.WHITE)
                R.drawable.white_checkers_king
            else
                R.drawable.black_checkers_king
        }
    }
}
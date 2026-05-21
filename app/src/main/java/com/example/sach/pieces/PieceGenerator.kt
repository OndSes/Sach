package com.example.sach.pieces

import com.example.sach.board.Board
import com.example.sach.pieces.sliding.Bishop
import com.example.sach.pieces.sliding.Queen
import com.example.sach.pieces.sliding.Rook

class PieceGenerator {
    companion object {
        fun generateWhitePieces(board: Board): MutableList<Piece> {
            val pieces = mutableListOf<Piece>()

            val whiteLeftRook = Rook(board, true, 7, 0)
            board.getSquare(7, 0).piece = whiteLeftRook
            pieces.add(whiteLeftRook)

            val whiteRightRook = Rook(board, true, 7, 7)
            board.getSquare(7, 7).piece = whiteRightRook
            pieces.add(whiteRightRook)

            for (col in 0..7) {
                val whitePawn = Pawn(board, true, 6, col)
                board.getSquare(6, col).piece = whitePawn
                pieces.add(whitePawn)

                when (col) {

                    1, 6 -> {
                        val whiteKnight = Knight(board, true, 7, col)
                        board.getSquare(7, col).piece = whiteKnight
                        pieces.add(whiteKnight)
                    }

                    2, 5 -> {
                        val whiteBishop = Bishop(board, true, 7, col)
                        board.getSquare(7, col).piece = whiteBishop
                        pieces.add(whiteBishop)
                    }

                    3 -> {
                        val whiteQueen = Queen(board, true, 7, col)
                        board.getSquare(7, col).piece = whiteQueen
                        pieces.add(whiteQueen)
                    }

                    4 -> {
                        val whiteKing = King(board, true, 7, col, whiteLeftRook, whiteRightRook)
                        board.getSquare(7, col).piece = whiteKing
                        pieces.add(whiteKing)
                    }
                }
            }
            return pieces
        }

        fun generateBlackPieces(board: Board): MutableList<Piece> {
            val pieces = mutableListOf<Piece>()

            val blackLeftRook = Rook(board, false, 0, 0)
            board.getSquare(0, 0).piece = blackLeftRook
            pieces.add(blackLeftRook)

            val blackRightRook = Rook(board, false, 0, 7)
            board.getSquare(0, 7).piece = blackRightRook
            pieces.add(blackRightRook)

            for (col in 0..7) {
                val blackPawn = Pawn(board, false, 1, col)
                board.getSquare(1, col).piece = blackPawn
                pieces.add(blackPawn)

                when (col) {

                    1, 6 -> {
                        val blackKnight = Knight(board, false, 0, col)
                        board.getSquare(0, col).piece = blackKnight
                        pieces.add(blackKnight)
                    }

                    2, 5 -> {
                        val blackBishop = Bishop(board, false, 0, col)
                        board.getSquare(0, col).piece = blackBishop
                        pieces.add(blackBishop)
                    }

                    3 -> {
                        val blackQueen = Queen(board, false, 0, col)
                        board.getSquare(0, col).piece = blackQueen
                        pieces.add(blackQueen)
                    }

                    4 -> {
                        val blackKing = King(board, false, 0, col, blackLeftRook, blackRightRook)
                        board.getSquare(0, col).piece = blackKing
                        pieces.add(blackKing)
                    }
                }
            }

            return pieces
        }
    }
}
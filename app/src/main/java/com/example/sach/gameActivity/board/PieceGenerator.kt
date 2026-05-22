package com.example.sach.gameActivity.board

import com.example.sach.gameActivity.pieces.Piece
import com.example.sach.gameActivity.pieces.PieceColor
import com.example.sach.gameActivity.pieces.checkers.Man
import com.example.sach.gameActivity.pieces.chess.King
import com.example.sach.gameActivity.pieces.chess.Knight
import com.example.sach.gameActivity.pieces.chess.Pawn
import com.example.sach.gameActivity.pieces.chess.sliding.Bishop
import com.example.sach.gameActivity.pieces.chess.sliding.Queen
import com.example.sach.gameActivity.pieces.chess.sliding.Rook

class PieceGenerator {
    companion object {
        fun generateWhiteChessPieces(board: ChessBoard): MutableList<Piece> {
            val pieces = mutableListOf<Piece>()

            val whiteLeftRook = Rook(board, PieceColor.WHITE, 7, 0)
            board.getSquare(7, 0).piece = whiteLeftRook
            pieces.add(whiteLeftRook)

            val whiteRightRook = Rook(board, PieceColor.WHITE, 7, 7)
            board.getSquare(7, 7).piece = whiteRightRook
            pieces.add(whiteRightRook)

            for (col in 0..7) {
                val whitePawn = Pawn(board, PieceColor.WHITE, 6, col)
                board.getSquare(6, col).piece = whitePawn
                pieces.add(whitePawn)

                when (col) {

                    1, 6 -> {
                        val whiteKnight = Knight(board, PieceColor.WHITE, 7, col)
                        board.getSquare(7, col).piece = whiteKnight
                        pieces.add(whiteKnight)
                    }

                    2, 5 -> {
                        val whiteBishop = Bishop(board, PieceColor.WHITE, 7, col)
                        board.getSquare(7, col).piece = whiteBishop
                        pieces.add(whiteBishop)
                    }

                    3 -> {
                        val whiteQueen = Queen(board, PieceColor.WHITE, 7, col)
                        board.getSquare(7, col).piece = whiteQueen
                        pieces.add(whiteQueen)
                    }

                    4 -> {
                        val whiteKing =
                            King(board, PieceColor.WHITE, 7, col, whiteLeftRook, whiteRightRook)
                        board.getSquare(7, col).piece = whiteKing
                        pieces.add(whiteKing)
                    }
                }
            }
            return pieces
        }

        fun generateBlackChessPieces(board: ChessBoard): MutableList<Piece> {
            val pieces = mutableListOf<Piece>()

            val blackLeftRook = Rook(board, PieceColor.BLACK, 0, 0)
            board.getSquare(0, 0).piece = blackLeftRook
            pieces.add(blackLeftRook)

            val blackRightRook = Rook(board, PieceColor.BLACK, 0, 7)
            board.getSquare(0, 7).piece = blackRightRook
            pieces.add(blackRightRook)

            for (col in 0..7) {
                val blackPawn = Pawn(board, PieceColor.BLACK, 1, col)
                board.getSquare(1, col).piece = blackPawn
                pieces.add(blackPawn)

                when (col) {

                    1, 6 -> {
                        val blackKnight = Knight(board, PieceColor.BLACK, 0, col)
                        board.getSquare(0, col).piece = blackKnight
                        pieces.add(blackKnight)
                    }

                    2, 5 -> {
                        val blackBishop = Bishop(board, PieceColor.BLACK, 0, col)
                        board.getSquare(0, col).piece = blackBishop
                        pieces.add(blackBishop)
                    }

                    3 -> {
                        val blackQueen = Queen(board, PieceColor.BLACK, 0, col)
                        board.getSquare(0, col).piece = blackQueen
                        pieces.add(blackQueen)
                    }

                    4 -> {
                        val blackKing =
                            King(board, PieceColor.BLACK, 0, col, blackLeftRook, blackRightRook)
                        board.getSquare(0, col).piece = blackKing
                        pieces.add(blackKing)
                    }
                }
            }

            return pieces
        }

        fun generateWhiteCheckersPieces(board: CheckersBoard): MutableList<Piece> {
            val pieces = mutableListOf<Piece>()

            for (col in 0..7) {
                when (col) {
                    0, 2, 4, 6 -> {
                        var whiteMan = Man(board, PieceColor.WHITE, 5, col)
                        board.getSquare(5, col).piece = whiteMan
                        pieces.add(whiteMan)

                        whiteMan = Man(board, PieceColor.WHITE, 7, col)
                        board.getSquare(7, col).piece = whiteMan
                        pieces.add(whiteMan)
                    }
                    else -> {
                        val whiteMan = Man(board, PieceColor.WHITE, 6, col)
                        board.getSquare(6, col).piece = whiteMan
                        pieces.add(whiteMan)
                    }
                }
            }
            return pieces
        }

        fun generateBlackCheckersPieces(board: CheckersBoard): MutableList<Piece> {
            val pieces = mutableListOf<Piece>()

            for (col in 0..7) {
                when (col) {
                    1, 3, 5, 7 -> {
                        var blackMan = Man(board, PieceColor.BLACK, 0, col)
                        board.getSquare(0, col).piece = blackMan
                        pieces.add(blackMan)

                        blackMan = Man(board, PieceColor.BLACK, 2, col)
                        board.getSquare(2, col).piece = blackMan
                        pieces.add(blackMan)
                    }
                    else -> {
                        val blackMan = Man(board, PieceColor.BLACK, 1, col)
                        board.getSquare(1, col).piece = blackMan
                        pieces.add(blackMan)
                    }
                }
            }
            return pieces
        }
    }
}
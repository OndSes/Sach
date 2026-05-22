package com.example.sach.gameActivity.pieces.checkers

import com.example.sach.gameActivity.board.CheckersBoard
import com.example.sach.gameActivity.pieces.Piece
import com.example.sach.gameActivity.pieces.PieceColor

abstract class CheckersPiece(override val board: CheckersBoard, color: PieceColor, row: Int, col: Int): Piece(board, color, row, col) {
}
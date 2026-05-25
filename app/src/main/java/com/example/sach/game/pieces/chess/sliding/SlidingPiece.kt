package com.example.sach.game.pieces.chess.sliding

import com.example.sach.game.board.ChessBoard
import com.example.sach.game.board.BoardSquare
import com.example.sach.game.pieces.Direction
import com.example.sach.game.pieces.PieceColor
import com.example.sach.game.pieces.chess.ChessPiece

/**
 * typ figúrky, ktorá sa môže pohnúť v danom smere, až kým nenarazí na okraj šachovnice,
 * alebo na inú figúrku
 */
abstract class SlidingPiece(board: ChessBoard, color: PieceColor, row: Int, col: Int) : ChessPiece(board, color, row, col) {
    abstract val directions: List<Direction>

    override fun getPossibleMoves(): List<BoardSquare> {
        val moves = mutableListOf<BoardSquare>()
        var squareToCheck: BoardSquare
        var x = 1
        for(direction in directions) {
            while (board.isValidPosition(square.row + x * direction.rowDelta, square.col + x * direction.colDelta)) {
                squareToCheck = board.getSquare(square.row + x * direction.rowDelta, square.col + x * direction.colDelta)
                if (squareContainsAlliedPiece(squareToCheck)) {
                    break
                }
                if (squareContainsEnemyPiece(squareToCheck)) {
                    moves.add(squareToCheck)
                    break
                }
                moves.add(squareToCheck)
                x++
            }
            x = 1
        }
        return moves
    }

    override fun getAttackMoves(): List<BoardSquare> {
        val moves = mutableListOf<BoardSquare>()
        var squareToCheck: BoardSquare
        var x = 1
        for(direction in directions) {
            while (board.isValidPosition(square.row + x * direction.rowDelta, square.col + x * direction.colDelta)) {
                squareToCheck = board.getSquare(square.row + x * direction.rowDelta, square.col + x * direction.colDelta)
                if (squareContainsPiece(squareToCheck)) {
                    moves.add(squareToCheck)
                    break
                }
                moves.add(squareToCheck)
                x++
            }
            x = 1
        }
        return moves
    }
}
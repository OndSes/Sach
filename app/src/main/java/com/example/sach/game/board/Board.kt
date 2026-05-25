package com.example.sach.game.board

import com.example.sach.game.games.Game
import com.example.sach.game.pieces.Piece
import com.example.sach.game.pieces.PieceColor

/**
 * abstraktná trieda, predstavujúca hraciu dosku 8x8, uchováva zoznamy bielych a čiernych figúrok
 */
abstract class Board(val game: Game) {
    abstract val whitePieces: MutableList<Piece>
    abstract val blackPieces: MutableList<Piece>
    val squares: Array<Array<BoardSquare>> =
        Array(8) { row ->
            Array(8) { col ->
                BoardSquare(row, col)
            }
        }

    /**
     * vyhodnotí stav hry
     */
    abstract fun checkState(color: PieceColor): StateOfGame

    fun getSquare(row: Int, col: Int): BoardSquare {
        return squares[row][col]
    }

    /**
     * skontroluje či sa dané súradnice nachádzajú na doske
     */
    fun isValidPosition(row: Int, col: Int): Boolean {
        return row in 0..7 && col in 0..7
    }

    /**
     * odstráni figúrku zo zoznamu
     */
    fun capture(piece: Piece) {
        if (piece.color == PieceColor.WHITE) {
            whitePieces.remove(piece)
        } else {
            blackPieces.remove(piece)
        }
    }
}
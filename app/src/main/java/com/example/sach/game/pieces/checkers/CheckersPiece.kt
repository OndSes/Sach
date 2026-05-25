package com.example.sach.game.pieces.checkers

import com.example.sach.game.board.CheckersBoard
import com.example.sach.game.board.BoardSquare
import com.example.sach.game.pieces.Direction
import com.example.sach.game.pieces.Piece
import com.example.sach.game.pieces.PieceColor
import kotlin.collections.mutableListOf

/**
 * dámová figúrka, ktorá sa pohybuje po diagonálach
 */
abstract class CheckersPiece(override val board: CheckersBoard, color: PieceColor, row: Int, col: Int): Piece(board, color, row, col) {
    abstract val maxMovement: Int
    abstract val directions: List<Direction>

    /**
     * posunie figúrku na dané políčko a skontroluje či nebolo iné preskočené, v tom prípade ho vyhodí
     */
    override fun move(targetSquare: BoardSquare) {
        super.move(targetSquare)

        board.lastMovedPiece = this

        if (board.isCaptureAvailable) {
            val captureRow: Int = if (targetSquare.row - this.row < 0) {
                targetSquare.row + 1
            } else {
                targetSquare.row - 1
            }
            val captureCol: Int = if (targetSquare.col - this.col < 0) {
                targetSquare.col + 1
            } else {
                targetSquare.col - 1
            }

            board.capture(board.getSquare(captureRow, captureCol).piece!!)
            board.getSquare(captureRow, captureCol).piece = null
        }

        square.piece = null
        targetSquare.piece = this
        row = targetSquare.row
        col = targetSquare.col
    }

    /**
     * vráti všetky legálne ťahy figúrky
     */
    override fun getLegalMoves(): List<BoardSquare> {
        if (board.isCaptureAvailable) {
            return getCaptures()
        }

        val moves = mutableListOf<BoardSquare>()
        var newRow: Int
        var newCol: Int
        var squareToCheck: BoardSquare

        for (direction in directions) {
            for (i in 1..maxMovement) {
                newRow = row + i * direction.rowDelta
                newCol = col + i * direction.colDelta
                if (!board.isValidPosition(newRow, newCol)) {
                    break
                }
                squareToCheck = board.getSquare(newRow, newCol)
                if (squareContainsPiece(squareToCheck)) {
                    break
                }
                moves.add(squareToCheck)
            }
        }

        return moves
    }

    /**
     * vráti všetky ťahy figúrky, pri ktorých bude vyhodená iná figúrka
     */
    fun getCaptures(): List<BoardSquare> {
        val captures = mutableListOf<BoardSquare>()

        var newRow: Int
        var newCol: Int
        var squareToCheck: BoardSquare

        for (direction in directions) {
            for (i in 1..maxMovement) {
                newRow = row + i * direction.rowDelta
                newCol = col + i * direction.colDelta
                if (!board.isValidPosition(newRow, newCol)) {
                    break
                }
                squareToCheck = board.getSquare(newRow, newCol)
                if (squareContainsAlliedPiece(squareToCheck)) {
                    break
                }
                if (squareContainsEnemyPiece(squareToCheck)) {
                    if (!board.isValidPosition(newRow + direction.rowDelta, newCol + direction.colDelta)) {
                        break
                    }
                    squareToCheck = board.getSquare(newRow + direction.rowDelta, newCol + direction.colDelta)
                    if(!squareContainsPiece(squareToCheck)) {
                        captures.add(squareToCheck)
                    }
                    break
                }
            }
        }

        return captures
    }
}
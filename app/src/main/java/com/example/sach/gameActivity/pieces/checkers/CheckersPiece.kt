package com.example.sach.gameActivity.pieces.checkers

import com.example.sach.gameActivity.board.CheckersBoard
import com.example.sach.gameActivity.board.Square
import com.example.sach.gameActivity.pieces.Direction
import com.example.sach.gameActivity.pieces.Piece
import com.example.sach.gameActivity.pieces.PieceColor
import kotlin.collections.mutableListOf

abstract class CheckersPiece(override val board: CheckersBoard, color: PieceColor, row: Int, col: Int): Piece(board, color, row, col) {
    abstract val maxMovement: Int
    abstract val directions: List<Direction>

    override fun move(targetSquare: Square) {
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
    }

    override fun getLegalMoves(): Array<Square> {
        if (board.isCaptureAvailable) {
            return getCaptures()
        }

        val moves = mutableListOf<Square>()
        var newRow: Int
        var newCol: Int
        var squareToCheck: Square

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

        return moves.toTypedArray()
    }
    fun getCaptures(): Array<Square> {
        val captures = mutableListOf<Square>()

        var newRow: Int
        var newCol: Int
        var squareToCheck: Square

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

        return captures.toTypedArray()
    }
}
package com.example.sach.game.ui

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import com.example.sach.game.board.BoardSquare
import com.example.sach.game.board.ChessBoard
import com.example.sach.game.board.StateOfGame
import com.example.sach.game.games.Chess
import com.example.sach.game.games.Game
import com.example.sach.game.pieces.PieceColor
import com.example.sach.game.pieces.chess.Knight
import com.example.sach.game.pieces.chess.Pawn
import com.example.sach.game.pieces.chess.sliding.Bishop
import com.example.sach.game.pieces.chess.sliding.Queen
import com.example.sach.game.pieces.chess.sliding.Rook

class BoardRenderer(val boardView: GridLayout, val context: Context, var game: Game,
                    val whiteTimer: TextView?, val blackTimer: TextView?) {
    val timers = whiteTimer != null && blackTimer != null
    val squares: Array<Array<SquareView>> =
        Array(8) { row ->
            Array(8) { col ->
                SquareView(row, col, context)
            }
        }
    var selectedSquare: SquareView? = null
    var onSaveGameRequested: (() -> Unit)? = null
    init {
        boardView.removeAllViews()

        for (row in 0..7) {
            for (col in 0..7) {
                val square = squares[row][col]
                square.onTap = {
                    selectSquare(square)
                }
                boardView.addView(square.container)
            }
        }
        if (timers && !game.isTimerEnabled) {
            whiteTimer!!.visibility = View.GONE
            blackTimer!!.visibility = View.GONE
        }

        refreshBoard()
    }

    fun selectSquare(square: SquareView) {
        val boardSquare = game.board.squares[square.row][square.col]
        selectedSquare = square
        game.selectSquare(boardSquare)
    }

    fun refreshBoard() {
        selectedSquare = null

        if (game.settings.rotateBoard) {
            rotate(game.turnColor)
        } else if (game.settings.rotatePieces) {
            rotatePieces(game.turnColor)
            rotateTimers(game.turnColor)
        }

        resetViews()

        activateSquares(game.turnColor)
    }

    fun resetViews() {
        for (row in 0..7) {
            for (col in 0..7) {
                val square = squares[row][col]
                square.setPieceImage(getPieceDrawable(game.board.getSquare(row, col).piece))
                resetSquareHighlight(square)
                square.hideMoveIndicator()
            }
        }
    }

    fun resetSquareHighlight(square: SquareView) {
        square.pieceView.alpha = 1.0f
    }

    fun highlightSquare() {
        selectedSquare!!.pieceView.alpha = 0.5f
    }

    fun activateSquares(color: PieceColor) {
        deactivateSquares()
        val pieces = if (color == PieceColor.WHITE) {game.board.whitePieces} else {game.board.blackPieces}

        for (piece in pieces) {
            squares[piece.row][piece.col].isActive = true
        }
    }

    fun deactivateSquares() {
        for (row in 0..7) {
            for (col in 0..7) {
                squares[row][col].isActive = false
            }
        }
    }

    fun showLegalMoves(moves: List<BoardSquare>) {
        for (s in moves) {
            val square = squares[s.row][s.col]
            square.isActive = true
            square.showMoveIndicator()
        }
    }

    fun rotate(turnColor: PieceColor) {
        boardView.animate()
            .rotation(
                if (turnColor == PieceColor.WHITE) 0f else 180f
            )
            .setDuration(300)
            .start()

        rotatePieces(turnColor)
    }

    fun rotatePieces(turnColor: PieceColor) {
        val rotation = if (turnColor == PieceColor.WHITE) 0f else 180f
        for (row in squares) {
            for (square in row) {
                square.rotateView(rotation)
            }
        }
    }

    private fun rotateTimers(turnColor: PieceColor) {
        if (!timers) {
            return
        }
        val rotation = if (turnColor == PieceColor.WHITE) 0f else 180f
        whiteTimer!!.rotation = rotation
        blackTimer!!.rotation = rotation
    }

    fun showGameOverMessage(color: PieceColor, state: StateOfGame) {
        val message: String = when (state) {
            StateOfGame.CHECKMATE -> {
                when (color) {
                    PieceColor.WHITE -> "Checkmate! White Wins"
                    PieceColor.BLACK -> "Checkmate! Black Wins"
                }
            }
            StateOfGame.TIMEOUT -> {
                when (color) {
                    PieceColor.WHITE -> "White Wins On Timeout"
                    PieceColor.BLACK -> "Black Wins On Timeout"
                }
            }
            else -> "Draw by Stalemate"
        }


        AlertDialog.Builder(context)
            .setTitle("Game Over")
            .setMessage(message)
            .setCancelable(false)

            .setPositiveButton("Save Game") { _, _ ->
                onSaveGameRequested?.invoke()
            }

            .setNegativeButton("Close", null)
            .show()
    }

    fun requestPromotion(pawn: Pawn) {
        val options = arrayOf(
            "Queen",
            "Rook",
            "Bishop",
            "Knight"
        )

        AlertDialog.Builder(context)
            .setTitle("Promote Pawn")
            .setItems(options) { _, which ->

                val promotedPiece =
                    when (which) {

                        0 -> Queen(
                            game.board as ChessBoard,
                            pawn.color,
                            pawn.row,
                            pawn.col
                        )

                        1 -> Rook(
                            game.board as ChessBoard,
                            pawn.color,
                            pawn.row,
                            pawn.col
                        )

                        2 -> Bishop(
                            game.board as ChessBoard,
                            pawn.color,
                            pawn.row,
                            pawn.col
                        )

                        else -> Knight(
                            game.board as ChessBoard,
                            pawn.color,
                            pawn.row,
                            pawn.col
                        )
                    }

                val chess = game as Chess
                chess.promotePawn(pawn, promotedPiece)

                refreshBoard()
            }
            .show()
    }

    fun formatTime(millis: Long): String {
        val totalSeconds = millis / 1000

        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60

        return String.format("%02d:%02d", minutes, seconds)
    }

    fun updateTimers(whiteTime: Long, blackTime: Long) {
        blackTimer!!.text = formatTime(blackTime)
        whiteTimer!!.text = formatTime(whiteTime)
    }
}
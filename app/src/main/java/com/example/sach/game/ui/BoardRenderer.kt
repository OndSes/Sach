package com.example.sach.game.ui

import android.app.AlertDialog
import android.content.Context
import android.widget.GridLayout
import com.example.sach.game.board.BoardSquare
import com.example.sach.game.board.ChessBoard
import com.example.sach.game.games.Chess
import com.example.sach.game.games.Game
import com.example.sach.game.pieces.PieceColor
import com.example.sach.game.pieces.chess.Knight
import com.example.sach.game.pieces.chess.Pawn
import com.example.sach.game.pieces.chess.sliding.Bishop
import com.example.sach.game.pieces.chess.sliding.Queen
import com.example.sach.game.pieces.chess.sliding.Rook

class BoardRenderer(val boardView: GridLayout, val context: Context, val game: Game) {
    val squares: Array<Array<SquareView>> =
        Array(8) { row ->
            Array(8) { col ->
                SquareView(row, col, context)
            }
        }
    var selectedSquare: SquareView? = null

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

        game.onBoardChanged = { refreshBoard() }
        game.onPieceSelected = { moves ->
            resetViews()
            highlightSquare(selectedSquare!!)
            showLegalMoves(moves)
        }
        game.onPromotionRequested = { pawn -> requestPromotion(pawn) }
        game.onGameMessageRequested = { message -> showGameOverMessage(message) }

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
            rotatePieces()
        }

        resetViews()

        activateSquares(game.turnColor)
    }

    private fun resetViews() {
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

    fun highlightSquare(square: SquareView) {
        square.pieceView.alpha = 0.5f
    }

    fun activateSquares(color: PieceColor) {
        for (row in 0..7) {
            for (col in 0..7) {
                squares[row][col].isActive = false
            }
        }
        val pieces = if (color == PieceColor.WHITE) {game.board.whitePieces} else {game.board.blackPieces}

        for (piece in pieces) {
            squares[piece.row][piece.col].isActive = true
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

        rotatePieces()
    }

    fun rotatePieces() {
        for (row in squares) {
            for (square in row) {
                square.rotateView()
            }
        }
    }

    fun showGameOverMessage(message: String) {

        AlertDialog.Builder(context)
            .setTitle("Game Over")
            .setMessage(message)
            .setCancelable(false)
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
}
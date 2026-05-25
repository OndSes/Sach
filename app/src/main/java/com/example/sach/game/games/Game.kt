package com.example.sach.game.games

import android.os.CountDownTimer
import com.example.sach.game.Settings
import com.example.sach.game.board.Board
import com.example.sach.game.board.BoardSquare
import com.example.sach.game.board.StateOfGame
import com.example.sach.game.pieces.PieceColor
import com.example.sach.game.pieces.chess.Pawn
import java.util.UUID

/**
 * abstraktná trieda, ktorá kontroluje chod hry, časomieru a vyvoláva udalosti pri zmenách
 * tiež uchováva zoznam ťahov na uloženie do databázy
 */
abstract class Game(val settings: Settings) {
    val gameId = UUID.randomUUID().toString()
    lateinit var board: Board
    var turnColor: PieceColor = PieceColor.WHITE
    val moves = mutableListOf<String>()
    var whiteTimeMillis: Long = settings.whiteTimeMinutes * 60_000L
    var blackTimeMillis: Long = settings.blackTimeMinutes * 60_000L
    var isTimerEnabled = settings.timerEnabled
    private var timer: CountDownTimer? = null
    var selectedSquare: BoardSquare? = null
    var onBoardChanged: (() -> Unit)? = null
    var onPieceSelected: ((List<BoardSquare>) -> Unit)? = null
    var onPromotionRequested: ((Pawn) -> Unit)? = null
    var onGameOver: ((PieceColor, StateOfGame) -> Unit)? = null
    var onTimerChanged: ((Long, Long) -> Unit)? = null
    fun movePiece(square: BoardSquare) {
        selectedSquare!!.piece!!.move(square)
        nextMove()
    }

    open fun nextMove() {
        onBoardChanged?.invoke()
    }

    fun selectSquare(square: BoardSquare) {
        if(square.piece != null && square.piece!!.color == turnColor) {
            selectedSquare = square
            onPieceSelected?.invoke(square.piece!!.getLegalMoves())
        } else {
            movePiece(square)
        }
    }

    fun recordMove(move: String) {
        moves.add(move)
    }

    fun startTimer() {
        if (!isTimerEnabled) {
            return
        }

        timer?.cancel()

        timer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
                override fun onTick(
                    millisUntilFinished: Long
                ) {
                    if (turnColor == PieceColor.WHITE) {
                        whiteTimeMillis -= 1000
                    } else {
                        blackTimeMillis -= 1000
                    }

                    onTimerChanged?.invoke(whiteTimeMillis, blackTimeMillis)
                    checkTimeout()
                }
                override fun onFinish() {}
            }
        timer?.start()
    }

    private fun checkTimeout() {
        if (whiteTimeMillis <= 0) {
            onGameOver?.invoke(turnColor.opposite, StateOfGame.TIMEOUT)
            timer?.cancel()
        } else if (blackTimeMillis <= 0) {
            onGameOver?.invoke(turnColor.opposite, StateOfGame.TIMEOUT)
            timer?.cancel()
        }
    }
}
package com.example.sach.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sach.R
import com.example.sach.game.Settings
import com.example.sach.game.games.Checkers
import com.example.sach.game.games.Chess
import com.example.sach.game.games.Game
import com.example.sach.game.ui.BoardRenderer
import com.example.sach.game.viewModel.GameViewModel
import com.example.sach.history.database.MoveEntity
import kotlinx.coroutines.launch
import kotlin.getValue

class ReplayFragment : Fragment() {
    lateinit var game: Game
    var currentMoveIndex = 0
    val viewModel: GameViewModel by viewModels()
    lateinit var gameId: String
    lateinit var moves: List<MoveEntity>

    override fun onSaveInstanceState(
        outState: Bundle
    ) {
        super.onSaveInstanceState(outState)

        outState.putInt(
            "move_index",
            currentMoveIndex
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_replay,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        currentMoveIndex = savedInstanceState?.getInt(
            "move_index",
            0
        ) ?: 0

        gameId = arguments?.getString("gameId")!!
        game = when(arguments?.getString("gameType")!!) {
            "chess" -> Chess(Settings(rotateBoard = false, rotatePieces = false, false, 0, 0))
            else -> Checkers(Settings(rotateBoard = false, rotatePieces = false, false, 0, 0))
        }
        val boardView =
            view.findViewById<GridLayout>(
                R.id.replayBoard
            )

        val renderer =
            BoardRenderer(
                boardView,
                requireContext(),
                game,
                null,
                null
            )
        renderer.deactivateSquares()

        lifecycleScope.launch {
            moves = viewModel.getMoves(gameId)

            repeat(currentMoveIndex) { index ->

                applyMove(moves[index])
            }

            renderer.refreshBoard()
            renderer.deactivateSquares()
        }

        val nextMoveButton =
            view.findViewById<Button>(
                R.id.nextMoveButton
            )

        nextMoveButton.setOnClickListener {

            if (currentMoveIndex >= moves.size) {
                return@setOnClickListener
            }

            val move = moves[currentMoveIndex]

            applyMove(move)

            renderer.refreshBoard()
            renderer.deactivateSquares()

            currentMoveIndex++
        }

        val previousMoveButton =
            view.findViewById<Button>(
                R.id.previousMoveButton
            )

        previousMoveButton.setOnClickListener {

            if (currentMoveIndex <= 0) {
                return@setOnClickListener
            }

            currentMoveIndex--

            rebuildBoard(renderer)
        }
    }

    private fun applyMove(move: MoveEntity) {
        val parts = move.moveData.split("->")

        val from = parts[0].split(",")
        val to = parts[1].split(",")

        val fromRow = from[0].toInt()
        val fromCol = from[1].toInt()

        val toRow = to[0].toInt()
        val toCol = to[1].toInt()

        val fromSquare =
            game.board.getSquare(
                fromRow,
                fromCol
            )

        val toSquare =
            game.board.getSquare(
                toRow,
                toCol
            )
        fromSquare.piece!!.move(toSquare)
    }
    private fun rebuildBoard(renderer: BoardRenderer) {
        game = when(arguments?.getString("gameType")!!) {
            "chess" -> Chess(Settings(rotateBoard = false, rotatePieces = false, false, 0, 0))
            else -> Checkers(Settings(rotateBoard = false, rotatePieces = false, false, 0, 0))
        }

        for (i in 0 until currentMoveIndex) {
            applyMove(moves[i])
        }

        renderer.game = game
        renderer.refreshBoard()
        renderer.deactivateSquares()
    }
}
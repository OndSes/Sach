package com.example.sach.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sach.R
import com.example.sach.game.viewModel.GameViewModel
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {

    private val viewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_history,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        val recyclerView =
            view.findViewById<RecyclerView>(
                R.id.historyRecyclerView
            )

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        lifecycleScope.launch {

            val games = viewModel.getGames()
            lateinit var adapter: HistoryAdapter

            adapter =
                HistoryAdapter(
                    games.toMutableList()
                ) { game, position ->

                    lifecycleScope.launch {

                        viewModel.deleteGame(game.gameId)

                        adapter.removeAt(position)
                    }
                }

            recyclerView.adapter = adapter
        }
    }
}
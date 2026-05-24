package com.example.sach.mainMenu

import com.example.sach.R
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class MainMenuFragment : Fragment(R.layout.activity_main_menu) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val chessButton =
            view.findViewById<Button>(R.id.chessButton)

        val checkersButton =
            view.findViewById<Button>(R.id.checkersButton)

        val historyButton =
            view.findViewById<Button>(R.id.historyButton)

        val settingsButton =
            view.findViewById<Button>(R.id.settingsButton)

        chessButton.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("game_type", "chess")

            findNavController().navigate(
                R.id.gameFragment,
                bundle
            )
        }

        checkersButton.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("game_type", "checkers")

            findNavController().navigate(
                R.id.gameFragment,
                bundle
            )
        }

        historyButton.setOnClickListener {
/*
            findNavController().navigate(
                R.id.historyFragment
            )
*/        }

        settingsButton.setOnClickListener {

            findNavController().navigate(
                R.id.settingsFragment
            )
        }
    }
}
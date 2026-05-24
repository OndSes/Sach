package com.example.sach.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SwitchCompat
import com.example.sach.R

class SettingsFragment : Fragment(R.layout.activity_settings) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val preferences =
            requireContext().getSharedPreferences(
                "settings",
                Context.MODE_PRIVATE
            )

        val rotateBoardSwitch =
            view.findViewById<SwitchCompat>(
                R.id.rotateBoardSwitch
            )

        rotateBoardSwitch.isChecked =
            preferences.getBoolean(
                "rotate_board",
                false
            )

        rotateBoardSwitch.setOnCheckedChangeListener { _, isChecked ->

            preferences.edit {
                putBoolean(
                    "rotate_board",
                    isChecked
                )
            }
        }

        val rotatePiecesSwitch =
            view.findViewById<SwitchCompat>(
                R.id.rotatePiecesSwitch
            )

        rotatePiecesSwitch.isChecked =
            preferences.getBoolean(
                "rotate_pieces",
                false
            )

        rotatePiecesSwitch.setOnCheckedChangeListener { _, isChecked ->

            preferences.edit {
                putBoolean(
                    "rotate_pieces",
                    isChecked
                )
            }
        }
    }
}
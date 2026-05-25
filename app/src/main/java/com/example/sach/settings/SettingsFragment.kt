package com.example.sach.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SwitchCompat
import androidx.core.widget.addTextChangedListener
import com.example.sach.R
import kotlin.concurrent.timer

class SettingsFragment : Fragment(R.layout.fragment_settings) {

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

        val rotateBoardSwitch = view.findViewById<SwitchCompat>(R.id.rotateBoardSwitch)

        rotateBoardSwitch.isChecked = preferences.getBoolean("rotate_board", false)

        rotateBoardSwitch.setOnCheckedChangeListener { _, isChecked ->
            preferences.edit {
                putBoolean("rotate_board", isChecked)
            }
        }

        val rotatePiecesSwitch = view.findViewById<SwitchCompat>(R.id.rotatePiecesSwitch)

        rotatePiecesSwitch.isChecked = preferences.getBoolean("rotate_pieces", false)

        rotatePiecesSwitch.setOnCheckedChangeListener { _, isChecked ->
            preferences.edit {
                putBoolean("rotate_pieces", isChecked)
            }
        }

        val timerEnabledSwitch = view.findViewById<SwitchCompat>(R.id.timerEnabledSwitch)

        timerEnabledSwitch.isChecked = preferences.getBoolean("timer_enabled", false)

        timerEnabledSwitch.setOnCheckedChangeListener { _, isChecked ->
            preferences.edit {
                putBoolean("timer_enabled", isChecked)
            }
        }

        val whiteTimeEditText = view.findViewById<EditText>(R.id.whiteTimeEditText)
        whiteTimeEditText.setText(preferences.getInt("white_time", 10).toString())

        whiteTimeEditText.addTextChangedListener {
            preferences.edit { putInt("white_time", whiteTimeEditText.text.toString().toIntOrNull() ?: 10) }
        }

        val blackTimeEditText = view.findViewById<EditText>(R.id.blackTimeEditText)
        blackTimeEditText.setText(preferences.getInt("black_time", 10).toString())

        blackTimeEditText.addTextChangedListener {
            preferences.edit { putInt("black_time", blackTimeEditText.text.toString().toIntOrNull() ?: 10) }
        }
    }
}
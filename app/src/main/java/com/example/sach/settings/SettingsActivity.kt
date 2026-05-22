package com.example.sach.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.example.sach.R
import androidx.core.content.edit

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        val preferences =
            getSharedPreferences(
                "settings",
                MODE_PRIVATE
            )

        val rotateBoardSwitch =
            findViewById<SwitchCompat>(R.id.rotateBoardSwitch)

        // load saved value
        rotateBoardSwitch.isChecked =
            preferences.getBoolean(
                "rotate_board",
                false
            )

        // save changes
        rotateBoardSwitch.setOnCheckedChangeListener { _, isChecked ->

            preferences.edit {
                putBoolean(
                    "rotate_board",
                    isChecked
                )
            }
        }

        val rotatePiecesSwitch =
            findViewById<SwitchCompat>(R.id.rotatePiecesSwitch)

        // load saved value
        rotatePiecesSwitch.isChecked =
            preferences.getBoolean(
                "rotate_pieces",
                false
            )

        // save changes
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
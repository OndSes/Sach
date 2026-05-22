package com.example.sach.mainMenu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.sach.R
import com.example.sach.gameActivity.GameActivity
import com.example.sach.settings.SettingsActivity

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_menu)

        val chessButton = findViewById<Button>(R.id.chessButton)
        val checkersButton = findViewById<Button>(R.id.checkersButton)
        val historyButton = findViewById<Button>(R.id.historyButton)
        val settingsButton = findViewById<Button>(R.id.settingsButton)

        chessButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("game_type", "chess")
            startActivity(intent)
        }

        checkersButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("game_type", "checkers")
            startActivity(intent)
        }

        historyButton.setOnClickListener {

            // open history screen
        }

        settingsButton.setOnClickListener {

            startActivity(
                Intent(this, SettingsActivity::class.java)
            )
        }
    }
}
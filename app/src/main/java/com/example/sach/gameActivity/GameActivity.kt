package com.example.sach.gameActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sach.gameActivity.Game
import com.example.sach.R

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Game(findViewById(R.id.chessBoard), this)
    }
}
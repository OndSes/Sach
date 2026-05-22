package com.example.sach.gameActivity.pieces

enum class PieceColor {
    WHITE,
    BLACK;

    val opposite
        get() = when (this) {
            WHITE -> BLACK
            BLACK -> WHITE
        }

}
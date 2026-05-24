package com.example.sach.game.pieces

enum class PieceColor {
    WHITE,
    BLACK;

    val opposite
        get() = when (this) {
            WHITE -> BLACK
            BLACK -> WHITE
        }

}
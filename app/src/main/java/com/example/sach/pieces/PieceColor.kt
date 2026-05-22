package com.example.sach.pieces

enum class PieceColor {
    WHITE,
    BLACK;

    val opposite
        get() = when (this) {
            WHITE -> BLACK
            BLACK -> WHITE
        }

}
package com.example.sach.pieces

enum class PieceColor {
    WHITE,
    BLACK;

    val oppostie
        get() = when (this) {
            WHITE -> BLACK
            BLACK -> WHITE
        }

}
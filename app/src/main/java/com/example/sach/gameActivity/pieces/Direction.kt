package com.example.sach.gameActivity.pieces

enum class Direction(val rowDelta: Int, val colDelta: Int) {
    UP(-1, 0),
    DOWN(1, 0),

    LEFT(0, -1),
    RIGHT(0, 1),

    UP_LEFT(-1, -1),
    UP_RIGHT(-1, 1),

    DOWN_LEFT(1, -1),
    DOWN_RIGHT(1, 1)
}
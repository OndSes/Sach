package com.example.sach.gameActivity.board

import com.example.sach.gameActivity.pieces.Piece

data class BoardSquare(
    val row: Int,
    val col: Int,
    var piece: Piece? = null
)

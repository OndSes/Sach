package com.example.sach.game.board

import com.example.sach.game.pieces.Piece

/**
 * Políčko na hracej doske
 */
data class BoardSquare(
    val row: Int,
    val col: Int,
    var piece: Piece? = null
)

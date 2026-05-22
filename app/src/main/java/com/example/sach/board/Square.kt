package com.example.sach.board

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.sach.Game
import com.example.sach.R
import com.example.sach.pieces.Piece

class Square(val row: Int, val col: Int, context: Context, val game: Game) {
    val container = FrameLayout(context)
    val moveIndicator = View(context)
    val view: ImageView = ImageView(context)
    var isActive = false

    var piece: Piece? = null
        set(value) {
            field = value
            if (value != null) {
                view.setImageResource(value.getResourceId())
                value.square = this
            } else {
                view.setImageDrawable(null)
            }
        }

    init {
        val size = context.resources.displayMetrics.widthPixels / 8

        val params = GridLayout.LayoutParams()
        params.width = size
        params.height = size

        container.layoutParams = params

        moveIndicator.background = ContextCompat.getDrawable(context, R.drawable.move_indicator)
        val indicatorParams = FrameLayout.LayoutParams(
            40,
            40
        )
        indicatorParams.gravity = Gravity.CENTER
        moveIndicator.layoutParams = indicatorParams
        moveIndicator.visibility = View.GONE

        view.setBackgroundColor(
            if ((row + col) % 2 == 1) Color.DKGRAY else Color.LTGRAY
        )

        view.scaleType = ImageView.ScaleType.FIT_CENTER
        view.setPadding(8, 8, 8, 8)

        view.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )

        view.setOnClickListener {
            if (!isActive) {
                return@setOnClickListener
            }
            game.selectSquare(this)
        }

        container.addView(view)
        container.addView(moveIndicator)
    }

    fun showMoveIndicator() {
        moveIndicator.visibility = View.VISIBLE
    }

    fun hideMoveIndicator() {
        moveIndicator.visibility = View.GONE
    }

    fun updateView() {

        if (piece == null) {
            view.setImageDrawable(null)
            return
        }

        view.setImageResource(piece!!.getResourceId())
    }
}
package com.example.sach.game.ui

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.sach.R

class SquareView(val row: Int, val col: Int, context: Context) {
    val container = FrameLayout(context)
    val moveIndicator = View(context)
    val pieceView: ImageView = ImageView(context)
    var isActive = false
    var onTap: (() -> Unit)? = null
    val metrics = context.resources.displayMetrics

    init {
        val size = minOf(metrics.widthPixels, metrics.heightPixels) / 8

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

        pieceView.setBackgroundColor(
            if ((row + col) % 2 == 1) Color.DKGRAY else Color.LTGRAY
        )

        pieceView.scaleType = ImageView.ScaleType.FIT_CENTER
        pieceView.setPadding(8, 8, 8, 8)

        pieceView.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )

        pieceView.setOnClickListener {
            if (isActive) {
                onTap?.invoke()
            }
        }

        container.addView(pieceView)
        container.addView(moveIndicator)
    }

    fun showMoveIndicator() {
        moveIndicator.visibility = View.VISIBLE
    }

    fun hideMoveIndicator() {
        moveIndicator.visibility = View.GONE
    }

    fun setPieceImage(
        resourceId: Int?
    ) {

        if (resourceId == null) {
            pieceView.setImageDrawable(null)
        } else {
            pieceView.setImageResource(resourceId)
        }
    }

    fun rotateView(rotation: Float) {
        pieceView.rotation = rotation
    }
}
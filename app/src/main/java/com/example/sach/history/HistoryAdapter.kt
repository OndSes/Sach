package com.example.sach.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sach.R
import com.example.sach.history.database.GameEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryAdapter(
    private val games: MutableList<GameEntity>,
    private val onDeleteClicked: (GameEntity, Int) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(view: View)
        : RecyclerView.ViewHolder(view) {

        val gameTypeText: TextView =
            view.findViewById(R.id.gameTypeText)

        val dateText: TextView =
            view.findViewById(R.id.dateText)

        val deleteButton: Button =
            view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_game,
                    parent,
                    false
                )

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val game = games[position]

        holder.gameTypeText.text = game.gameType
        val formatter =
            SimpleDateFormat(
                "dd.MM.yyyy HH:mm",
                Locale.getDefault()
            )

        holder.dateText.text =
            formatter.format(Date(game.date))

        holder.deleteButton.setOnClickListener {
            onDeleteClicked(game, position)
        }
    }

    fun removeAt(position: Int) {
        games.removeAt(position)
        notifyItemRemoved(position)
    }
}
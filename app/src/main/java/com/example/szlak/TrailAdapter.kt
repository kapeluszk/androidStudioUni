package com.example.szlak

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class TrailAdapter(private var trails: List<LocalData.Trail>) :
    RecyclerView.Adapter<TrailAdapter.TrailViewHolder>() {

    // Interface for item click listener
    interface OnItemClickListener {
        fun onItemClick(trail: LocalData.Trail)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    // ViewHolder pattern to improve performance
    inner class TrailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)

        init {
            // Setting click listener on card view
            cardView.setOnClickListener {
                itemClickListener?.onItemClick(trails[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trail, parent, false)
        return TrailViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TrailViewHolder, position: Int) {
        val currentTrail = trails[position]
        holder.nameTextView.text = currentTrail.name
        // You can bind other views here if needed
    }

    override fun getItemCount(): Int {
        return trails.size
    }

    fun setTrails(newTrails: List<LocalData.Trail>) {
        trails = newTrails
        notifyDataSetChanged()
    }
}
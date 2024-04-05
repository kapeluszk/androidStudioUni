package com.example.szlak

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class TrailAdapter(context: Context, trails: List<LocalData.Trail>) : ArrayAdapter<LocalData.Trail>(context, R.layout.item_trail, trails) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.item_trail, parent, false)
        }

        val currentTrail = getItem(position)

        val nameTextView = listItemView!!.findViewById<TextView>(R.id.nameTextView)
        nameTextView?.text = currentTrail?.name ?: ""

        return listItemView
    }
}
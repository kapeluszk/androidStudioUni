package com.example.szlak

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

class DescriptionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_description, container, false)

        val bottomToolbar = view.findViewById<Toolbar>(R.id.bottom_toolbar)
        bottomToolbar.inflateMenu((R.menu.bottom_toolbar_menu))
        bottomToolbar.setOnMenuItemClickListener{ item ->
            when (item.itemId) {
                R.id.action_trail_list ->{
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, TrailListFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.action_stoper -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, StoperFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }

                else -> {false}
            }
        }

        // Inflate the layout for this fragment
        return view
    }
}
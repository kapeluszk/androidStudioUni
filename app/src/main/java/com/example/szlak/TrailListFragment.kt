package com.example.szlak

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TrailListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var trailAdapter: TrailAdapter
    private lateinit var allTrails: List<LocalData.Trail>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_trail_list, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Load all trails by default
        allTrails = LocalData.readTrailsFromCSV(requireContext(), "szlaki.csv")

        // Initialize TrailAdapter with all trails
//        trailAdapter = TrailAdapter(allTrails)
//        recyclerView.adapter = trailAdapter

        // Set up click listeners for CardViews as difficulty selection buttons
        view.findViewById<View>(R.id.descriptionCardView).setOnClickListener {
            navigateToDifficultyTrails("all")
        }

        view.findViewById<View>(R.id.easyTrailsCardView).setOnClickListener {
            navigateToDifficultyTrails("easy")
        }

        view.findViewById<View>(R.id.hardTrailsCardView).setOnClickListener {
            navigateToDifficultyTrails("hard")
        }

        return view
    }

    private fun navigateToDifficultyTrails(difficulty: String) {
        // Przygotuj informacje o wybranej trudności i wszystkich szlakach
        val bundle = Bundle().apply {
            putString("difficulty", difficulty)

        }

        // Twórz nowy fragment lub aktywność w zależności od potrzeb
        val newFragment = DifficultyTrailsFragment(difficulty).apply {
            arguments = bundle
        }

        // Przełącz się na nowy widok
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.container, newFragment) // Zastąp fragment o ID fragment_container nowym fragmentem
            addToBackStack(null) // Dodaj transakcję do stosu cofania, aby można było wrócić do poprzedniego widoku
            commit()
        }
    }
}
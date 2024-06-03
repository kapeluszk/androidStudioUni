package com.example.szlak

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DifficultyTrailsFragment(private val difficulty: String) : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var trailAdapter: TrailAdapter
    private lateinit var trails: List<LocalData.Trail>
    private lateinit var allTrails: List<LocalData.Trail>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_trail_list, container, false)

        // Load trails by difficulty
        allTrails = LocalData.readTrailsFromCSV(requireContext(), "szlaki.csv")
        trails = LocalData.getTrailsByDiff(allTrails, difficulty)


        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize TrailAdapter with trails for the selected difficulty
        trailAdapter = TrailAdapter(trails)
        recyclerView.adapter = trailAdapter

        return view
    }
}
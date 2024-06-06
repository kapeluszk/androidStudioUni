package com.example.szlak

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TrailListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var trailAdapter: TrailAdapter
    private lateinit var allTrails: List<LocalData.Trail>

    private var selectedDiff: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_trail_list, container, false)


        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

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



//        val toolbar: Toolbar = view.findViewById(/* id = */ R.id.toolbar)
//        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        // Initialize RecyclerView
//        recyclerView = view.findViewById(R.id.recycler_view)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Load all trails by default
        allTrails = LocalData.readTrailsFromCSV(requireContext(), "szlaki.csv")

        // Initialize TrailAdapter with all trails
//        trailAdapter = TrailAdapter(allTrails)
//        recyclerView.adapter = trailAdapter

        // Set up click listeners for CardViews as difficulty selection buttons
        view.findViewById<View>(R.id.descriptionCardView).setOnClickListener {
            selectedDiff = "all"
            saveDiffandNav(selectedDiff)
        }

        view.findViewById<View>(R.id.easyTrailsCardView).setOnClickListener {
            selectedDiff = "easy"
            saveDiffandNav(selectedDiff)
        }

        view.findViewById<View>(R.id.hardTrailsCardView).setOnClickListener {
            selectedDiff = "hard"
            saveDiffandNav(selectedDiff)
        }

        return view
    }

    private fun saveDiffandNav(difficulty: String){
        DifficultyPreference.saveSelectedDifficulty(requireContext(),difficulty)
        navigateToDifficultyTrails()
    }

    private fun navigateToDifficultyTrails() {
        // Przygotuj informacje o wybranej trudności i wszystkich szlakach
//        val selectedDifficulty = DifficultyPreference.getSelectedDifficulty(requireContext())
//        println(selectedDifficulty)
        val newFragment = DifficultyTrailsFragment.newInstance()

        // Przełącz się na nowy widok
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.container, newFragment) // Zastąp fragment o ID fragment_container nowym fragmentem
            addToBackStack(null) // Dodaj transakcję do stosu cofania, aby można było wrócić do poprzedniego widoku
            commit()
        }
    }
}
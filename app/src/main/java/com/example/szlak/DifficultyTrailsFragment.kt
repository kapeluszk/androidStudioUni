package com.example.szlak

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager

class DifficultyTrailsFragment() : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var trailAdapter: TrailAdapter
    private lateinit var trails: List<LocalData.Trail>
    private lateinit var allTrails: List<LocalData.Trail>

    companion object {
        fun newInstance(): DifficultyTrailsFragment {
            val fragment = DifficultyTrailsFragment() // Przekazanie difficulty do konstruktora
            val args = Bundle()
//            args.putString("difficulty", difficulty)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_difficulty_trails, container, false)


        
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


        val difficulty = DifficultyPreference.getSelectedDifficulty(requireContext())
        // Load trails by difficulty
        allTrails = LocalData.readTrailsFromJSON(requireContext(), "trails.json")
        trails = LocalData.getTrailsByDifficulty(allTrails, difficulty ?: "all")


        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view)
        val layoutManager = GridLayoutManager(requireContext(),2)
        recyclerView.layoutManager = layoutManager

        // Initialize TrailAdapter with trails for the selected difficulty
        trailAdapter = TrailAdapter(trails)
        recyclerView.adapter = trailAdapter

        trailAdapter.setOnItemClickListener(object : TrailAdapter.OnItemClickListener {
            override fun onItemClick(trail: LocalData.Trail) {
                // Otwórz TrailDetailsFragment, przekazując szczegóły szlaku
                val fragment = TrailDetailsFragment.newInstance(trail)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        })

        return view
    }

}
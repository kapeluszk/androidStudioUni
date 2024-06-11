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
            val fragment = DifficultyTrailsFragment()
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

        val difficulty = DifficultyPreference.getSelectedDifficulty(requireContext())

        allTrails = LocalData.readTrailsFromJSON(requireContext(), "trails.json")
        trails = LocalData.getTrailsByDifficulty(allTrails, difficulty ?: "all")


        recyclerView = view.findViewById(R.id.recycler_view)
        val layoutManager = GridLayoutManager(requireContext(),2)
        recyclerView.layoutManager = layoutManager

        trailAdapter = TrailAdapter(trails)
        recyclerView.adapter = trailAdapter

        trailAdapter.setOnItemClickListener(object : TrailAdapter.OnItemClickListener {
            override fun onItemClick(trail: LocalData.Trail) {
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
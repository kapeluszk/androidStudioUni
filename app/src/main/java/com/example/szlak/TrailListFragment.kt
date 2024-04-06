package com.example.szlak

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.ListFragment
import com.example.szlak.LocalData.getSampleTrails

class TrailListFragment : ListFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val trails = LocalData.readTrailsFromCSV(requireContext(),"szlaki.csv")
        listAdapter = TrailAdapter(requireContext(), trails)
        print(trails)
        LocalData.printTrails(trails)

        return view
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)

        val selectedTrail = listAdapter?.getItem(position) as LocalData.Trail
        val detailsFragment = TrailDetailsFragment.newInstance(selectedTrail)

        val isTablet = resources.getBoolean(R.bool.isTablet)
        val containerId = if (isTablet) R.id.detail_container else R.id.container

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(containerId, detailsFragment)
            .addToBackStack(null)
            .commit()
    }
}
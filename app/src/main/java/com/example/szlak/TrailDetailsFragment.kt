package com.example.szlak

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.szlak.R.id.descriptionTextView
import com.example.szlak.R.id.nameTextView

class TrailDetailsFragment : Fragment() {

    companion object {
        fun newInstance(trail: LocalData.Trail): TrailDetailsFragment {
            val fragment = TrailDetailsFragment()
            val args = Bundle()
            args.putSerializable("trail", trail)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_trail_details, container, false)

        val trail = arguments?.getSerializable("trail") as? LocalData.Trail

        val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
        val descriptionTextView = view.findViewById<TextView>(R.id.descriptionTextView)

        nameTextView.text = trail?.name
        descriptionTextView.text = trail?.description

        if (savedInstanceState == null) {
            val transaction = childFragmentManager.beginTransaction()
            transaction.add(R.id.stoper_container, StoperFragment())
            transaction.commit()
        }

        return view
    }
}
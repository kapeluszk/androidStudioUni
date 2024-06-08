package com.example.szlak

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TrailPagerAdapter(fragmentActivity: FragmentActivity, private val context: Context) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DescriptionFragment()
            1 -> easyDiff()
            2 -> hardDiff()
            else -> throw IllegalStateException("Invalid position: $position")
        }
    }

    fun hardDiff(): Fragment{
        DifficultyPreference.saveSelectedDifficulty(context, "hard")
        return(DifficultyTrailsFragment())
    }
    fun easyDiff(): Fragment{
        DifficultyPreference.saveSelectedDifficulty(context, "easy")
        return(DifficultyTrailsFragment())
    }
}

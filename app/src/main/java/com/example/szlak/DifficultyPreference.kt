package com.example.szlak

import android.content.Context

object DifficultyPreference {
    private const val PREFERENCE_NAME = "difficulty_pref"
    private const val KEY_DIFFICULTY = "selected_difficulty"

    fun saveSelectedDifficulty(context: Context, difficulty: String) {
        val sharedPref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(KEY_DIFFICULTY, difficulty)
            apply()
        }
    }

    fun getSelectedDifficulty(context: Context): String? {
        val sharedPref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(KEY_DIFFICULTY, null)
    }
}
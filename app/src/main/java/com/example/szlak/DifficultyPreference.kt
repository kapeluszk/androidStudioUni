package com.example.szlak

import android.content.Context
import android.content.SharedPreferences

object DifficultyPreference {

    private const val PREFS_NAME = "difficulty_prefs"
    private const val KEY_SELECTED_DIFFICULTY = "selected_difficulty"

    fun saveSelectedDifficulty(context: Context, difficulty: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(KEY_SELECTED_DIFFICULTY, difficulty)
        editor.apply()
    }

    fun getSelectedDifficulty(context: Context): String? {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_SELECTED_DIFFICULTY, "easy")
    }
}

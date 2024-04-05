package com.example.szlak

import java.io.Serializable

object LocalData {
    data class Trail(val name: String, val description: String) : Serializable

    fun getSampleTrails(): List<Trail> {
        return listOf(
            Trail("Szlak A", "Opis Szlaku A"),
            Trail("Szlak B", "Opis Szlaku B"),
            Trail("Szlak C", "Opis Szlaku C")
        )
    }
}
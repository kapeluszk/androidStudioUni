package com.example.szlak

import android.content.Context
import java.io.File
import java.io.IOException
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

    fun readTrailsFromCSV(context: Context, fileName: String): List<Trail> {
        val trails = mutableListOf<Trail>()
        val assetManager = context.assets

        assetManager.open(fileName).bufferedReader().useLines { lines ->
            val iterator = lines.iterator()
            if (iterator.hasNext()) {
                val header = iterator.next().split(",") // Użyj iteratora, aby przeczytać nagłówek tylko raz
                for (line in iterator) { // Użyj iteratora do przetwarzania reszty linii
                    val trailData = line.split(",")
                    val name = trailData[0]
                    val description = trailData[1]
                    trails.add(Trail(name, description))
                }
            }
        }

        return trails
    }


    fun printTrails(trails: List<Trail>) {
        trails.forEach { trail ->
            println("Name: ${trail.name}, Description: ${trail.description}")
        }
    }
}
package com.example.szlak

import android.content.Context
import android.health.connect.datatypes.units.Length
import java.io.File
import java.io.IOException
import java.io.Serializable

object LocalData {
    data class Trail(val name: String, val description: String, val diff: String, val imgID: String, val expectedLength: String) : Serializable

    fun getSampleTrails(): List<Trail> {
        return listOf(
            Trail("Szlak A", "Opis Szlaku A", "easy", "photo1", "6 hours"),
            Trail("Szlak B", "Opis Szlaku B", "easy", "photo1", "6 hours"),
            Trail("Szlak C", "Opis Szlaku C", "hard", "photo1", "6 hours")
        )
    }

    fun getTrailsByDiff(list: List<Trail>, diff: String): List<Trail> {
        val trails = mutableListOf<Trail>()
        list.forEach {
            if (it.diff == diff ){
                trails.add(it)
            }
        }
        return trails
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
                    val diff = trailData[2]
                    val img = trailData[3]
                    val expectedLength = trailData[4]
                    trails.add(Trail(name, description, diff, img, expectedLength))
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
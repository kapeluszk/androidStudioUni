import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Serializable

object LocalData {
    data class Trail(
        val name: String,
        val description: String,
        val difficulty: String,
        val image: String,
        val expectedTime: String,
        val route: String
    ) : Serializable

    fun getSampleTrails(): List<Trail> {
        return emptyList()
    }

    fun getTrailsByDifficulty(trails: List<Trail>, difficulty: String): List<Trail> {
        return trails.filter { it.difficulty.equals(difficulty, ignoreCase = true) }
    }

    fun readTrailsFromJSON(context: Context, fileName: String): List<Trail> {
        val json = context.assets.open(fileName).bufferedReader().use(BufferedReader::readText)
        val trails = mutableListOf<Trail>()

        try {
            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                val trailObject = jsonArray.getJSONObject(i)
                val name = trailObject.getString("name")
                val description = trailObject.getString("description")
                val difficulty = trailObject.getString("difficulty")
                val image = trailObject.getString("image")
                val expectedTime = trailObject.getString("expectedTime")
                val route = trailObject.getString("route")

                val trail = Trail(name, description, difficulty, image, expectedTime, route)
                trails.add(trail)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return trails
    }

    fun printTrails(trails: List<Trail>) {
        trails.forEach { trail ->
            println("Name: ${trail.name}, Description: ${trail.description}")
        }
    }
}

package ie.dorset.student_24088.project.model

import com.squareup.moshi.JsonClass
import kotlin.random.Random

@JsonClass(generateAdapter = true)
data class Geolocation(
    var lat: String = Random.nextDouble(-90.0, 90.0).toString(),
    var long: String = Random.nextDouble(-180.0, 180.0).toString()
)
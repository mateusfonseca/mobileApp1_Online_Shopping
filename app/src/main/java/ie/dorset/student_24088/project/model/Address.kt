package ie.dorset.student_24088.project.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Address(
    var city: String?,
    var street: String?,
    var number: Int?,
    var zipcode: String?,
    var geolocation: Geolocation?
)
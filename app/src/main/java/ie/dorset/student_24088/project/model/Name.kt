package ie.dorset.student_24088.project.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Name(
    val firstname: String,
    val lastname: String
)
package ie.dorset.student_24088.project.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Category(
    override val id: Int,
    val name: String
) : Data(id = -1, lastModified = null, type = "category")
package ie.dorset.student_24088.project.model

import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
open class Data(
    open val id: Int = 1,
    open val lastModified: LocalDateTime?,
    open val type: String
)
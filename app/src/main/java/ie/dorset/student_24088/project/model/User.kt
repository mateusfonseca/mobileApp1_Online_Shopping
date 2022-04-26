package ie.dorset.student_24088.project.model

import com.squareup.moshi.JsonClass
import ie.dorset.student_24088.project.constant.RANDOM_PROFILE_PICTURE_URL

@JsonClass(generateAdapter = true)
data class User(
    override var id: Int,
    var email: String?,
    var username: String?,
    var password: String?,
    var name: Name?,
    var address: Address?,
    var phone: String?,
    var image: String = RANDOM_PROFILE_PICTURE_URL
) : Data(id = -1, lastModified = null, type = "user")
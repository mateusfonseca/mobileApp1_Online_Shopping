package ie.dorset.student_24088.project.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Cart(
    override var id: Int,
    val userId: Int,
    var date: String,
    var products: List<CartProduct>
) : Data(id = -1, lastModified = null, type = "cart"), Parcelable
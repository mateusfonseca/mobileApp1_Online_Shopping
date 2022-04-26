package ie.dorset.student_24088.project.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CartProduct(
    val productId: Int,
    var quantity: Int
) : Data(id = productId, lastModified = null, type = "cartProduct"),
    Parcelable
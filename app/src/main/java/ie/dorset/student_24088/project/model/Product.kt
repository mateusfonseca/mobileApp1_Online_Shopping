package ie.dorset.student_24088.project.model

import android.icu.text.NumberFormat
import android.icu.text.NumberFormat.CURRENCYSTYLE
import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Product(
    override var id: Int,
    var title: String,
    var category: String,
    var price: Double,
    var description: String,
    var image: String
) : Data(id = -1, lastModified = null, type = "product"), Parcelable {

    @RequiresApi(Build.VERSION_CODES.N)
    fun formatPrice(): String {
        return NumberFormat.getInstance(CURRENCYSTYLE).format(price)
    }
}
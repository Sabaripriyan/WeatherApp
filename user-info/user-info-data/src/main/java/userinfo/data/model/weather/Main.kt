package userinfo.data.model.weather

import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("temp")
    val temp: String? = null
)

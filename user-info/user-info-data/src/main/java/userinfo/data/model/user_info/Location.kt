package userinfo.data.model.user_info

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("city")
    val city: String? = "",
    @SerializedName("state")
    val state: String? = "",
    @SerializedName("country")
    val country: String? = "",
    @SerializedName("coordinates")
    val coordinates: Coordinates? = null
)

package userinfo.data.model

import com.google.gson.annotations.SerializedName

data class Picture(
    @SerializedName("medium")
    val medium: String? = null,
    @SerializedName("large")
    val large: String? = null,
    @SerializedName("thumbnail")
    val thumbnail: String? = null
)

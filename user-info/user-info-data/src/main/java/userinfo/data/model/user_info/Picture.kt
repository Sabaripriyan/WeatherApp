package userinfo.data.model.user_info

import com.google.gson.annotations.SerializedName

data class Picture(
    @SerializedName("medium")
    val medium: String? = null,
    @SerializedName("large")
    val large: String? = null,
    @SerializedName("thumbnail")
    val thumbnail: String? = null
)

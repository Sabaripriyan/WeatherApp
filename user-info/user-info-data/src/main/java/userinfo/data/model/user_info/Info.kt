package userinfo.data.model.user_info

import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("results")
    val results: Int? = null,
    @SerializedName("page")
    val page: Int? = null
)
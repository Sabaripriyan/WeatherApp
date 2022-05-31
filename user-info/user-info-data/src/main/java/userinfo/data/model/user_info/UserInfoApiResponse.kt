package userinfo.data.model.user_info

import com.google.gson.annotations.SerializedName

data class UserInfoApiResponse(
    @SerializedName("results")
    val results: List<UserInfo>? = listOf(),

    @SerializedName("info")
    val info: Info? = null
)

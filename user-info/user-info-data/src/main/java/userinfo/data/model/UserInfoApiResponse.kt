package userinfo.data.model

import com.google.gson.annotations.SerializedName

data class UserInfoApiResponse(
    @SerializedName("results")
    val results: List<UserInfo>? = listOf()
)

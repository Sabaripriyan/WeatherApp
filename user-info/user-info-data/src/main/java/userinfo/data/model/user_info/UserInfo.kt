package userinfo.data.model.user_info

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("name")
    val name: Name? = null,
    @SerializedName("location")
    val location: Location? = null,
    @SerializedName("email")
    val email: String? = "",
    @SerializedName("phone")
    val phone: String? = "",
    @SerializedName("picture")
    val picture: Picture? = null

)

package userinfo.domain.model.user_info

import java.io.Serializable


data class UserInfoData(
    val name: NameData? = null,
    val location: LocationData? = null,
    val email: String? = "",
    val phone: String? = "",
    val picture: PictureData? = null
): Serializable

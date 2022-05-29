package userinfo.domain.model


data class UserInfoData(
    val name: NameData? = null,
    val location: LocationData? = null,
    val email: String? = "",
    val phone: String? = "",
    val picture: PictureData? = null
)

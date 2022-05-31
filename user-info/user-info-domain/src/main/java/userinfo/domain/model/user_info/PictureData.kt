package userinfo.domain.model.user_info

import java.io.Serializable


data class PictureData(
    val medium: String? = null,
    val large: String? = null,
    val thumbnail: String? = null
): Serializable

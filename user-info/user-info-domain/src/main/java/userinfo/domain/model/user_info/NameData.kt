package userinfo.domain.model.user_info

import java.io.Serializable


data class NameData(
    val first: String? = "",
    val last: String? = ""
): Serializable

package userinfo.domain.model.user_info


data class LocationData(
    val city: String? = "",
    val state: String? = "",
    val country: String? = "",
    val coordinates: CoordinatesData? = null
)

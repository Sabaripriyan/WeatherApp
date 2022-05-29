package userinfo.domain.model


data class LocationData(
    val city: String? = "",
    val state: String? = "",
    val country: String? = "",
    val coordinates: CoordinatesData? = null
)

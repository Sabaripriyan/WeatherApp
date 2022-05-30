package userinfo.domain.model.weather


data class CurrentWeatherData(
    val main: MainData? = null,
    val weather: List<WeatherData>? = emptyList(),
    val city: String? = ""
)

package userinfo.data.model.weather

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("main")
    val main: Main? = null,
    @SerializedName("weather")
    val weather: List<Weather>? = emptyList(),
    @SerializedName("name")
    val city: String? = ""
)

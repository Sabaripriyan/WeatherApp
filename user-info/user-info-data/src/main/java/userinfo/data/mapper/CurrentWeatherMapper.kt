package userinfo.data.mapper

import core.kotlin.url.ApiUrls
import userinfo.data.model.weather.CurrentWeather
import userinfo.data.model.weather.Main
import userinfo.data.model.weather.Weather
import userinfo.domain.model.weather.CurrentWeatherData
import userinfo.domain.model.weather.MainData
import userinfo.domain.model.weather.WeatherData


internal fun CurrentWeather.toDomain() = CurrentWeatherData(
    main = main?.toDomain(),
    weather = weather?.map { it.toDomain() },
    city = city
)
internal fun Main.toDomain() = MainData(temp = "$temp \u2103")

internal fun Weather.toDomain() = WeatherData(
    description = description,
    icon = icon?.run{ApiUrls.weatherIconUrl.replace("image_id",icon)}
)
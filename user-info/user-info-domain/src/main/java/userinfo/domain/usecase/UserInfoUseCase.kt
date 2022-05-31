package userinfo.domain.usecase

import core.kotlin.Result
import io.reactivex.Single
import userinfo.domain.model.user_info.UserInfoApiResponseData
import userinfo.domain.model.weather.CurrentWeatherData

interface UserInfoUseCase {

    fun getUserInfo(page: Int): Single<Result<UserInfoApiResponseData>>

    fun getCurrentWeather(lat: String, lon: String): Single<Result<CurrentWeatherData>>
}
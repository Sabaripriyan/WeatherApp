package userinfo.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url
import userinfo.data.model.user_info.UserInfoApiResponse
import userinfo.data.model.weather.CurrentWeather
import userinfo.domain.model.weather.CurrentWeatherData

interface WebService {

    @GET
    fun getUserInfo(
        @Url url: String,
        ): Single<UserInfoApiResponse>

    @GET
    fun getCurrentWeather(
        @Url url: String,
        @QueryMap map: Map<String,String>
    ): Single<CurrentWeather>
}
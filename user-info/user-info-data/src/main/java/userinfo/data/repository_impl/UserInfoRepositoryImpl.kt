package userinfo.data.repository_impl

import core.kotlin.Result
import core.kotlin.UnknownError
import core.kotlin.constants.Constants
import core.kotlin.url.ApiUrls
import io.reactivex.Single
import userinfo.data.api.WebService
import userinfo.data.mapper.toDomain
import userinfo.domain.model.user_info.UserInfoApiResponseData
import userinfo.domain.model.weather.CurrentWeatherData
import userinfo.domain.repository.UserInfoRepository
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    val webService: @JvmSuppressWildcards WebService
): UserInfoRepository {

    override fun getUserInfo(): Single<Result<UserInfoApiResponseData>> {
        return Single.fromCallable {
          callUserInfoApi().blockingGet()
        }
    }

    private fun callUserInfoApi(): Single<Result<UserInfoApiResponseData>>{
        return  webService.getUserInfo(
            url = ApiUrls.userInfoApiUrl,
        ).doOnSuccess {  }
            .map {
                Result.withValue(it.toDomain())
            }
            .onErrorReturn {
                Result.withError(UnknownError(message = "", throwable = it))
            }
    }

    override fun getCurrentWeather(lat: String, lon: String): Single<Result<CurrentWeatherData>> {
        return Single.fromCallable {
            callWeatherApi(lat,lon).blockingGet()
        }
    }

    private fun callWeatherApi(lat: String, lon: String): Single<Result<CurrentWeatherData>> {
        val params = mapOf(
            Pair("lat", lat),
            Pair("lon",lon),
            Pair("appid",Constants.OPEN_WEATHER_API_KEY),
            Pair("units","metric")
        )
        return webService.getCurrentWeather(
            url = ApiUrls.currentWeatherUrl,
            map = params
        ).doOnSuccess {  }
            .map {
                Result.withValue(it.toDomain())
            }
            .onErrorReturn {
                Result.withError(UnknownError(message = "", throwable = it))
            }
    }

}
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

    override fun getUserInfo(page: Int): Single<Result<UserInfoApiResponseData>> {
        return Single.fromCallable {
          callUserInfoApi(page).blockingGet()
        }
    }

    private fun callUserInfoApi(page: Int): Single<Result<UserInfoApiResponseData>>{
        return  webService.getUserInfo(
            ApiUrls.userInfoApiUrl,
            mapOf(
                "results" to "25",
                "page" to "$page"
            ),
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
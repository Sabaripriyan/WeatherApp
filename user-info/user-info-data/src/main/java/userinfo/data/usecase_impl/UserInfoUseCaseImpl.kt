package userinfo.data.usecase_impl

import android.util.Log
import app.database.dao.UserInfoDao
import core.kotlin.Result
import io.reactivex.Single
import userinfo.data.mapper.toDB
import userinfo.data.repository_impl.UserInfoRepositoryImpl
import userinfo.domain.model.user_info.UserInfoApiResponseData
import userinfo.domain.model.weather.CurrentWeatherData
import userinfo.domain.usecase.UserInfoUseCase
import javax.inject.Inject


class UserInfoUseCaseImpl @Inject constructor(
    private val userInfoRepository: UserInfoRepositoryImpl,
    private val userInfoDao: UserInfoDao
): UserInfoUseCase {

    override fun getUserInfo(page: Int): Single<Result<UserInfoApiResponseData>> {
        return userInfoRepository.getUserInfo(page)
            .doOnSuccess {
                when(it) {
                    is Result.OnSuccess -> {
                        if(it.data.results?.isNullOrEmpty() == false){
                            val results = it.data.results
                            userInfoDao.insertUserInfo(results!!.map { it.toDB() })
                        }
                    }

                    is Result.OnError -> {

                    }
                }
            }
    }

    override fun getCurrentWeather(lat: String, lon: String): Single<Result<CurrentWeatherData>> {
        return userInfoRepository.getCurrentWeather(lat,lon)
            .doOnSuccess {
                when(it) {
                    is Result.OnSuccess -> {

                    }

                    is Result.OnError -> {

                    }
                }
            }
    }

}
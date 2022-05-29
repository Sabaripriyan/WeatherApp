package userinfo.data.repository_impl

import core.kotlin.Result
import core.kotlin.UnknownError
import core.kotlin.url.ApiUrls
import io.reactivex.Single
import userinfo.data.api.WebService
import userinfo.data.mapper.toDomain
import userinfo.domain.model.UserInfoApiResponseData
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

    fun callUserInfoApi(): Single<Result<UserInfoApiResponseData>>{
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

}
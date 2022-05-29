package userinfo.domain.repository

import core.kotlin.Result
import io.reactivex.Single
import userinfo.domain.model.UserInfoApiResponseData

interface UserInfoRepository {
    fun getUserInfo(): Single<Result<UserInfoApiResponseData>>
}
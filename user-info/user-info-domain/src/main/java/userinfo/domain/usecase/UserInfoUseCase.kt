package userinfo.domain.usecase

import core.kotlin.Result
import io.reactivex.Single
import userinfo.domain.model.UserInfoApiResponseData

interface UserInfoUseCase {

    fun getUserInfo(): Single<Result<UserInfoApiResponseData>>
}